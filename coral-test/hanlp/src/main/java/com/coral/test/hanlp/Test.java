package com.coral.test.hanlp;

import com.coral.base.common.StringEscapeUtils;
import com.hankcs.hanlp.corpus.io.IOUtil;
import lombok.SneakyThrows;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author huss
 * @version 1.0
 * @className Test
 * @description todo
 * @date 2022/7/13 11:10
 */
public class Test {

    private static final String PATTERN = "[^a-zA-Z0-9\\u4e00-\\u9fa5]";

    private static String BASE_PATH;

    private static final String DIA_MSR_DIC = "hosp_diagnosis_msr.txt.071402";

    static {

        String os = System.getProperty("os.name");
        //Windows操作系统
        if (os != null && os.toLowerCase().startsWith("windows")) {
            BASE_PATH = "E:\\projects\\mine\\java\\coral-cloud\\coral-test\\hanlp\\src\\main\\resources\\";
        } else {
            BASE_PATH = "/data/hanlp/files/";
        }


    }

    @SneakyThrows
    public static void main(String[] args) {


//        parseLine();
//        parseFile();

//        readVectorFile();
        parse();
    }

    private static void parse() {
        String json = "跛行原因待查&middot;。步态异常半天";
        System.out.println(StringEscapeUtils.escapeHtml4(json));
    }

    public static void readVectorFile() throws IOException {


        InputStream is = null;
        Reader r = null;
        BufferedReader br = null;
        try {
            is = IOUtil.newInputStream(BASE_PATH + DIA_MSR_DIC);
            r = new InputStreamReader(is, "utf-8");
            br = new BufferedReader(r);

            String line = br.readLine();
            int words = Integer.parseInt(line.split("\\s+")[0].trim());
            for (int i = 0; i < words; i++) {
                line = br.readLine();
                System.out.println("line:" + line);
                line = line.trim();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private static void parseFile() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(BASE_PATH + DIA_MSR_DIC));
        for (int i = 0, size = lines.size(); i < size; i++) {
            System.out.println(lines.get(i).trim());
        }
    }


    private static void parseLine() {
        String line = "附件 0.793144 0.055890 -0.200861 -0.221833 -0.001861 -0.489511 0.030345 -0.077911 0.014806 -0.320510 -0.362488 0.127447 -0.661922 -0.153258 -0.583662 -0.880954 -0.354496 0.527460 -0.259201 0.545730 0.081048 -0.222506 -0.053725 0.368653 -0.612851 0.464310 -0.397929 -0.440225 0.379804 -0.501494 0.540833 -0.523602 -0.067806 0.166059 0.005455 -0.060968 0.699055 -0.069672 -0.181233 -0.274548 0.434001 0.388217 -0.090628 0.034641 0.808564 0.426318 -0.383805 0.445655 -0.927245 -0.367252 -0.514565 0.545192 0.758364 -0.554097 0.046451 0.313407 -0.416906 -0.275449 -0.505592 -0.068792 1.209366 0.190853 0.381137 0.169508 -0.555496 0.327380 -0.292359 -0.030920 0.303128 -0.536980 -0.024538 -0.111180 0.064440 -0.075799 -0.192754 0.101261 -0.379817 0.480659 -0.803023 -0.371441 0.108727 -0.085802 -0.329015 0.508219 -0.380160 -0.253830 -0.326508 0.401240 0.255582 0.315289 -0.272329 0.613402 0.416164 0.436118 -0.172851 0.147604 -0.704450 -0.625210 0.326187 -0.477842";
        String[] params = line.split("\\s+");

        for (int j = 0; j < 100; j++) {
            System.out.println(Float.parseFloat(params[j + 1]));
        }
    }
}
