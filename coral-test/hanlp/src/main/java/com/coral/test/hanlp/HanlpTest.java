package com.coral.test.hanlp;

import com.coral.base.common.CollectionUtil;
import com.coral.base.common.StringPool;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.corpus.tag.Nature;
import com.hankcs.hanlp.dictionary.stopword.CoreStopWordDictionary;
import com.hankcs.hanlp.mining.word2vec.DocVectorModel;
import com.hankcs.hanlp.mining.word2vec.Vector;
import com.hankcs.hanlp.mining.word2vec.WordVectorModel;
import com.hankcs.hanlp.seg.common.Term;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author huss
 * @version 1.0
 * @className HanlpTest
 * @description hanlp测试
 * @date 2022/7/15 10:10
 */
public class HanlpTest {
    private static String BASE_PATH;
    private static final String DIA_MSR_DIC = "hosp_diagnosis_msr.txt";
    private static final String DISEASE_TXT = "disease.txt";
    private static final String DISEASE_SYNO_TEXT = "disease_syno.txt";

    private static final String HOSPITAL_DIA_TEXT = "hosp_diagnosis.txt";

    private static final float BASELINE = 0.4f;

    private static final Set<String> DISEASE_CACHE = new HashSet<>(50000);
    private static final Set<String> DISEASE_SYNO_CACHE = new HashSet<>(50000);

    /**
     * 医院数据
     **/
    private static final Map<String, List<VectorDocument>> HOSPITAL_DATA = new ConcurrentHashMap<>(1000000);


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class VectorDocument {

        private Vector vector;

        private String content;
    }


    public static void main(String[] args) throws IOException {
        init();

//        deduceDiseases("诊断: 发热查因。腹泻2天，全身发热，咳嗽频繁; ");

//        deduceDiseases("诊断:鼻窦炎,鼻塞待查。反复鼻塞清嗓2周");

//        deduceDiseases("诊断:脑震荡。头部摔伤后1天伴呕吐2次。");

//        deduceDiseases("诊断:（64 74 75）中龋；53继发龋，深龋。( 左侧)后牙发黑数日。");

//        deduceDiseases("诊断:上感 咽炎上感 咽炎 。咽痛2天");

        deduceDiseases("诊断:咽炎,鼻窦炎。鼻塞，流涕，咳嗽，咽痛2天");
    }

    /**
     * 初始化数据
     *
     * @throws IOException
     */
    private static void init() throws IOException {
        System.out.println("》》》》》初始化数据开始.");
        Files.readAllLines(Paths.get(BASE_PATH + DISEASE_TXT)).forEach(e -> DISEASE_CACHE.add(e));
        Files.readAllLines(Paths.get(BASE_PATH + DISEASE_SYNO_TEXT)).forEach(e -> DISEASE_SYNO_CACHE.add(e));


        Files.readAllLines(Paths.get(BASE_PATH + HOSPITAL_DIA_TEXT))
                .parallelStream()
                .forEach(e -> {
                    try {
                        List<Term> terms = segment(e);

                        List<String> diseases = wordMatch(terms);

                        if (CollectionUtil.isNotBlank(diseases)) {

                            Vector vector = query(terms);
                            diseases.parallelStream().forEach(dia -> {

                                List<VectorDocument> docs = HOSPITAL_DATA.get(dia);

                                docs = CollectionUtil.isBlank(docs) ? new ArrayList<>() : docs;

                                docs.add(new VectorDocument(vector, e));

                                HOSPITAL_DATA.put(dia, docs);
                            });
                        }

                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }


                });
        System.out.println("》》》》》初始化数据完成.");
    }

    /**
     * 推断疾病
     *
     * @param doc
     * @throws IOException
     */
    private static void deduceDiseases(String doc) throws IOException {
        System.out.println("\n######################################");
        System.out.println("【入参数据】：" + doc);
        List<Term> terms = segment(doc);

        List<String> diseases = null;// wordMatch(terms);
        if (CollectionUtil.isBlank(diseases)) {
            System.out.println("###根据单次匹配疾病失败，开始进行文档相似度匹配...");
            diseases = docNearest(terms);
        }
        System.out.println("【推断出的疾病为】:" + diseases);
        System.out.println("######################################\n");
    }

    private static List<Term> segment(String content) throws IOException {
        List<Term> terms = HanLP.segment(content);
        System.out.println("分词后：" + terms);
        terms = termFilter(terms);
        System.out.println("分词过滤后：" + terms);
        return terms;
    }

    private static List<String> wordMatch(List<Term> terms) {
        List<String> diseases = new ArrayList<>();

        for (Term term : terms) {
            System.out.println(">>>>当前单词：" + term.word);
            if (DISEASE_CACHE.contains(term.word) || DISEASE_SYNO_CACHE.contains(term.word)) {
                diseases.add(term.word);
            }
        }
        return diseases;
    }

    /**
     * 文档相似度
     *
     * @param terms
     */
    private static List<String> docNearest(List<Term> terms) throws IOException {
        Vector currentVector = query(terms);
        return HOSPITAL_DATA.entrySet().parallelStream()
                .filter(e -> e.getValue().stream()
                        .anyMatch(ev -> ev.getVector().cosineForUnitVector(currentVector) >= BASELINE)
                ).map(e -> {
                    e.getValue().parallelStream()
                            .filter(ev -> ev.getVector().cosineForUnitVector(currentVector) >= BASELINE)
                            .forEach(ev -> {
                                float sim = ev.getVector().cosineForUnitVector(currentVector);
                                System.out.println(">>>>>相似度大于基准线的医院数据为:" + sim + " >>> " + ev.getContent());
                            });

                    return e.getKey();
                })
                .collect(Collectors.toList());

    }

    private static Vector query(List<Term> terms) throws IOException {
        WordVectorModel wordVectorModel = new WordVectorModel(BASE_PATH + DIA_MSR_DIC);
        DocVectorModel docVectorModel = new DocVectorModel(wordVectorModel);
        Vector result = new Vector(docVectorModel.dimension());
        int n = 0;
        for (Term term : terms) {
            Vector vector = wordVectorModel.vector(term.word);
            if (vector == null) {
                continue;
            }
            ++n;
            result.addToSelf(vector);
        }
        if (n == 0) {
            return null;
        }
        result.normalize();
        return result;
    }

    /**
     * 分词过滤
     *
     * @param terms
     * @return
     */
    private static List<Term> termFilter(List<Term> terms) {
        if (CollectionUtil.isBlank(terms)) {
            return terms;
        }
        return terms.stream().filter(TERM_FILTER).collect(Collectors.toList());
    }

    private static final Predicate<Term> TERM_FILTER = e -> {
        //过滤 标点符号、量词、介词、数词、时间词、语气词、名语素词、人名词、停用词
        List<Nature> natures = Arrays.asList(
                Nature.w,
                Nature.q,
                Nature.p,
                Nature.m,
                Nature.t,
                Nature.y,
                Nature.ng,
                Nature.nr
        );

        boolean isStopWord = CoreStopWordDictionary.contains(e.word);

        boolean isNx = e.nature.equals(Nature.nx) && e.word.startsWith(StringPool.AMPERSAND);

        return !(natures.contains(e.nature) || isStopWord || isNx);
    };


    static {

        String os = System.getProperty("os.name");
        //Windows操作系统
        if (os != null && os.toLowerCase().startsWith("windows")) {
            BASE_PATH = "E:\\projects\\mine\\java\\coral-cloud\\coral-test\\hanlp\\src\\main\\resources\\";
        } else {
            BASE_PATH = "/data/hanlp/files/";
        }

    }

}
