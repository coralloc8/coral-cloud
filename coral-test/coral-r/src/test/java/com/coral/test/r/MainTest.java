package com.coral.test.r;

import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPDouble;
import org.rosuda.REngine.REXPFactor;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

import java.util.Arrays;

/**
 * @author huss
 * @version 1.0
 * @className MainTest
 * @description todo
 * @date 2021/6/2 15:37
 */
public class MainTest {

    public static void main(String[] args) throws REXPMismatchException, RserveException {
        callRserve();
    }

    static void callRserve() throws RserveException, REXPMismatchException {
        RConnection rConnection = new RConnection("192.168.29.190");


        String fileName = "/home/zhyx/cdss_java.r";
//        rConnection.assign("fileName", fileName);
        //执行test.R脚本，执行这一步才能调用里面的自定义函数myFunc，如果不行，就在R工具上也执行一下test.R脚本
        rConnection.eval("source('" + fileName + "')");
        //调用myFunc函数
        String dept_name = "妇科";
        String doctor_name = "赖紫玲";
        String message = "查看检验报告";
        String data_name = "（警告）多正常值检验项目异常结果警示";
        String time_select = "最近一个月";

        String func = String.format("CDSS_stat('%s','%s','%s','%s','%s')", dept_name, doctor_name, message, data_name, time_select);

//        String func = String.format("CDSS_stat()");

        System.out.println("func:" + func);

        rConnection.setStringEncoding("utf8");
        REXP rexp = rConnection.eval(func);

        rConnection.close();

        System.out.println("list: " + rexp.asList());
        System.out.println(rexp.asList().keySet());

        Arrays.stream(rexp.asList().at("dept_name_now").asStrings()).forEach(System.out::println);
        System.out.println("=================================");
        Arrays.stream(rexp.asList().at("doctor_name_now").asStrings()).forEach(System.out::println);
        System.out.println("=================================");
        Arrays.stream(rexp.asList().at("tips_type").asStrings()).forEach(System.out::println);
        System.out.println("=================================");
        Arrays.stream(rexp.asList().at("title_one").asStrings()).forEach(System.out::println);
        System.out.println("=================================");
        Arrays.stream(rexp.asList().at("message").asStrings()).forEach(System.out::println);
        System.out.println("=================================");
        Arrays.stream(rexp.asList().at("req_time").asStrings()).forEach(System.out::println);
        System.out.println("=================================");
        Arrays.stream(rexp.asList().at("data_name").asStrings()).forEach(System.out::println);
        System.out.println("=================================");
        Arrays.stream(rexp.asList().at("patient_name").asStrings()).forEach(System.out::println);
        System.out.println("=================================");
        Arrays.stream(rexp.asList().at("id").asStrings()).forEach(System.out::println);
        System.out.println("=================================");
        Arrays.stream(rexp.asList().at("req_year").asStrings()).forEach(System.out::println);
        System.out.println("=================================");
        Arrays.stream(rexp.asList().at("req_month").asStrings()).forEach(System.out::println);
        System.out.println("=================================");
        Arrays.stream(rexp.asList().at("req_day").asStrings()).forEach(System.out::println);
    }
}
