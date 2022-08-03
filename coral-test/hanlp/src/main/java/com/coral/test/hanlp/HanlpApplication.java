package com.coral.test.hanlp;

import com.coral.base.common.CollectionUtil;
import com.coral.base.common.StringPool;
import com.coral.base.common.StringUtils;
import com.coral.base.common.json.JsonUtil;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.corpus.synonym.Synonym;
import com.hankcs.hanlp.corpus.tag.Nature;
import com.hankcs.hanlp.dictionary.CoreSynonymDictionary;
import com.hankcs.hanlp.dictionary.common.CommonSynonymDictionary;
import com.hankcs.hanlp.dictionary.stopword.CoreStopWordDictionary;
import com.hankcs.hanlp.mining.word2vec.DocVectorModel;
import com.hankcs.hanlp.mining.word2vec.Word2VecTrainer;
import com.hankcs.hanlp.mining.word2vec.WordVectorModel;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.StandardTokenizer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author huss
 * @version 1.0
 * @className HanlpApplication
 * @description 主程序
 * @date 2022/7/6 13:46
 */
public class HanlpApplication {

    private static String BASE_PATH;

    static {

        String os = System.getProperty("os.name");
        //Windows操作系统
        if (os != null && os.toLowerCase().startsWith("windows")) {
            BASE_PATH = "E:\\projects\\mine\\java\\coral-cloud\\coral-test\\hanlp\\src\\main\\resources\\";
        } else {
            BASE_PATH = "/data/hanlp/files/";
        }


    }

    /**
     * skip-gram （训练速度慢，对罕见字有效），CBOW（训练速度快）。一般选择Skip-gram模型；
     * <p>
     * 训练方法：Hierarchical Softmax（对罕见字有利），Negative Sampling（对常见字和低维向量有利）；
     * <p>
     * sample 降采样越低，对高频词越不利，对低频词有利。可以这么理解，本来高频词 词被迭代50次，低频词迭代10次，如果采样频率降低一半，高频词失去了25次迭代，而低频词只失去了5次。一般设置成（1e-3~1e-5）。降采样会降低高频词对上下文影响的权重。
     * <p>
     * window 窗口大小影响 词 和前后多少个词的关系，和语料中语句长度有关，建议可以统计一下语料中，句子长度的分布，再来设置window大小。一般设置成8。Skip-gram通常选择10左右，CBOW通常选择5左右。
     * <p>
     * iter 影响训练次数，语料不够的情况下，可以调大迭代次数。spark 版本有bug，迭代次数超过1，训练得到的词向量维度值超大。
     * <p>
     * workers workers 用于设定训练的并行处理，以加快训练速度。workers设为1可复现训练好的词向量，但速度稍慢。worker 这个参数起作用的前提是安装了 Cython，否则只能用单核。
     */


    //同义词路径
    private static final String DISEASE_SYNONYM_TEXT = "disease_synonym.txt";
    private static final String DISEASE_SYNONYM_PATH = HanLP.Config.CoreSynonymDictionaryDictionaryPath;

    private static final String HOSP_DIA_JSON = "hospital_record.json";

    private static final String HOSP_REPORT_JSON = "hospital_report.json";

    private static final String HOSP_DIA_DIC = "hosp_diagnosis.txt";
    private static final String HOSP_DIA_SEGMENT_DIC = "hosp_diagnosis_segment.txt";
    private static final String DIA_MSR_DIC = "hosp_diagnosis_msr.txt";


    private static final Class<HanlpApplication> APP_CLASS = HanlpApplication.class;

    //只保留数字、字母、中文
    private static final String PATTERN = "[^a-zA-Z\\u4e00-\\u9fa5]";


    public static void main(String[] args) throws IOException {

        StandardTokenizer.SEGMENT.enableCustomDictionaryForcing(true);


//        System.out.println(StandardTokenizer.segment("诊断：高血压病2级低危吗？"));
//        System.out.println(StandardTokenizer.segment("诊断：右眼泪囊炎/泪道阻塞 于2021.10.10发热5天"));
//        System.out.println(HanLP.segment("子宫附件肿物"));
//        System.out.println(HanLP.segment("暴发型伤寒"));
//        System.out.println(HanLP.segment("伤寒杆菌性脓毒血症"));
//        System.out.println(HanLP.segment("0139群霍乱"));
//        System.out.println(HanLP.segment("子宫息肉"));
//        System.out.println(NLPTokenizer.segment("急性上呼吸道感染 抽搐查因：热性惊厥？ 伤寒杆菌性脓毒血症"));
//        System.out.println(NLPTokenizer.analyze("医生诊断出的结果是过敏性支气管炎"));
        System.out.println("===============================");


        //将json数据解析到hosp_diagnosis.txt
//        parseDisease();
        //将hosp_diagnosis.txt这个文件进行分词
//        loadDisease();

        //训练数据
//        train();

//        nearest("诊断:上腹痛，胃肠功能紊乱。脐周疼痛1天？");
//        System.out.println("=========================\n");
//
//        nearest("诊断:胃食管反流；疱疹性咽峡炎。");
//        System.out.println("=========================\n");
//
//        nearest("诊断：右眼泪囊炎/泪道阻塞");
//        System.out.println("=========================\n");
//
//        nearest("诊断：特应性皮炎");
//        System.out.println("=========================\n");

        nearest("诊断:湿疹？尿布疹？。包皮阴囊皮肤发红");
        System.out.println("=========================\n");

//        nearest("腹胀");
//        System.out.println("=========================\n");
//
//        nearest("恶性肿瘤靶向治疗,乳房恶性肿瘤,恶性肿瘤靶向治疗,乳房恶性肿瘤。左乳腺癌术后10月余，返院行靶向治疗");
//        System.out.println("=========================\n");

//
//        similarity("诊断: 上感。发热 1余　天。", "诊断:右拇指化脓性指头炎门诊。右拇指化脓性指头炎，复查。");

//        addSynonym();

//        synonym("门诊诊断：复发性鼻息肉？待查");

    }

    /**
     * 同义词测试 转换
     */
    private static String synonym(String str) {
        List<Term> terms = HanLP.segment(str);
//        System.out.println("分词:" + terms);

        List<CommonSynonymDictionary.SynonymItem> items = CoreSynonymDictionary.createSynonymList(terms, true);
        String res = str;
        if (CollectionUtil.isNotBlank(items)) {
            res = items.stream().map(e -> {
                if (e.entry.getIdString().length() > 8
                        && e.entry.getIdString().startsWith("X")
                        && CollectionUtil.isNotBlank(e.synonymList)) {
                    return e.synonymList.get(0).getRealWord();
                }
                return e.entry.getRealWord();
            }).collect(Collectors.joining());
        }
//        System.out.println("转换后:" + res);
        return res;
    }


    private static void similarity(String what, String with) throws IOException {
        WordVectorModel wordVectorModel = new WordVectorModel(BASE_PATH + DIA_MSR_DIC);

        DocVectorModel docVectorModel = new DocVectorModel(wordVectorModel);

        List<Map.Entry<Integer, Float>> list = docVectorModel.nearest(what);
        System.out.println(list);

        float similarity = docVectorModel.similarity(what, with);
        System.out.println("相似度为：" + similarity);

    }

    private static void nearest(String str) throws IOException {
        WordVectorModel wordVectorModel = new WordVectorModel(BASE_PATH + DIA_MSR_DIC);

        List<Map.Entry<String, Float>> list = wordVectorModel.nearest(str);

        System.out.println("不进行分词的结果为：" + list);

        System.out.println("分词前：" + str);
        List<Term> terms = HanLP.segment(str);
        System.out.println("分词后：" + terms);
        terms = termFilter(terms);
        System.out.println("分词过滤后：" + terms);

        for (Term term : terms) {
            System.out.println(">>>>当前单词：" + term.word);
            list = wordVectorModel.nearest(term.word);

            System.out.println("结果为：" + list);

        }

    }


    private static void train() {
        Word2VecTrainer trainer = new Word2VecTrainer();
        String trainFileName = BASE_PATH + HOSP_DIA_SEGMENT_DIC;
        String modelFileName = BASE_PATH + DIA_MSR_DIC;
        trainer.train(trainFileName, modelFileName);
    }

    /**
     * 添加同义词
     */
    private static void addSynonym() throws IOException {
        String idPrefix = "Z";

        AtomicLong atomicLong = new AtomicLong(0);
        Files.readAllLines(Paths.get(BASE_PATH + DISEASE_SYNONYM_TEXT)).forEach(line -> {
            String id = idPrefix + String.format("%06d", atomicLong.incrementAndGet());


            List<String> list = new ArrayList<>();
            list.add(id + "=");
            list.addAll(Arrays.asList(line.split(" ")));

            List<Synonym> synonyms = Synonym.create(list.toArray(new String[]{}));
            id = synonyms.get(0).getIdString();
            String word = synonyms.stream().map(e -> e.getRealWord()).collect(Collectors.joining(" "));


            String newLine = id + "= " + word + "\n";
            try {
                Files.write(Paths.get(DISEASE_SYNONYM_PATH), newLine.getBytes(), StandardOpenOption.APPEND);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });

    }


    private static void parseDisease() throws IOException {
        System.out.println(">>>>解析开始");
//        String json = IOUtil.readStream(APP_CLASS.getClassLoader().getResourceAsStream(HOSP_DIA_JSON));

        List<String> files = Arrays.asList(
                BASE_PATH + HOSP_DIA_JSON,
                BASE_PATH + HOSP_REPORT_JSON
        );

        Path path = Paths.get(BASE_PATH, HOSP_DIA_DIC);

        if (!Files.exists(path)) {
            Files.createFile(path);
        }


        files.forEach(file -> {

            String json = "";
            try {
                json = Files.readAllLines(Paths.get(file)).stream().collect(Collectors.joining());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Map map = JsonUtil.parse(json, Map.class);


            List<Map> list = (List) map.get("RECORDS");

            list.forEach(e -> {
                String value = (String) e.get("mi");
                if (StringUtils.isNotBlank(value)) {
                    value = value.replaceAll("[\r\n]", "");
                }
                value += "\n";
                try {
                    Files.write(Paths.get(BASE_PATH, HOSP_DIA_DIC), value.getBytes(), StandardOpenOption.APPEND);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });

        });

        System.out.println(">>>>解析完成");
    }


    /**
     * 加载诊断信息 并分词
     */
    private static void loadDisease() {
        try {
//            List<String> diseases = IOUtil.readStreamAsArray(APP_CLASS.getClassLoader().getResourceAsStream(HOSP_DIA_DIC));
            List<String> diseases = Files.readAllLines(Paths.get(BASE_PATH + HOSP_DIA_DIC));

            String segmentStr = diseases.stream()
                    .filter(StringUtils::isNotBlank)
                    .map(e -> {
                        List<Term> terms = HanLP.segment(e);

                        System.out.println("分词后：" + terms);

                        String letter = terms.stream()
                                .filter(TERM_FILTER)
                                .map(t -> t.word.toUpperCase())
                                .collect(Collectors.joining("  "));

                        System.out.println("去除杂质后：" + letter);
                        return letter;
                    })
                    .collect(Collectors.joining("\n"));

            Files.write(Paths.get(BASE_PATH, HOSP_DIA_SEGMENT_DIC), segmentStr.getBytes(), StandardOpenOption.CREATE_NEW);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

    private static List<Term> termFilter(List<Term> terms) {
        if (CollectionUtil.isBlank(terms)) {
            return terms;
        }
        return terms.stream().filter(TERM_FILTER).collect(Collectors.toList());
    }

}
