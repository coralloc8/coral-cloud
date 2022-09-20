package com.coral.base.common.aviator;

import com.coral.base.common.ScoreUtil;
import com.coral.base.common.aviator.function.InFunction;
import com.coral.base.common.aviator.function.PowFunction;
import com.coral.base.common.aviator.function.QuestionAnswerFunction;
import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author huss
 * @version 1.0
 * @className AviatorTest
 * @description 测试
 * @date 2022/5/25 17:40
 */
public class AviatorTest {

    /**
     *
     */

    @Test
    @DisplayName("test1")
    public void test1() {
        Assertions.assertEquals(7, (Long) AviatorEvaluator.execute("1+2*3"));
    }

    @Test
    @DisplayName("test2")
    public void test2() {
        Map<String, Object> env = new HashMap<>();
        env.put("name", "小三");
        String expression = "'年纪轻轻，奈何做贼啊！' + name + '同学'";
        Assertions.assertEquals("年纪轻轻，奈何做贼啊！小三同学", AviatorEvaluator.execute(expression, env));
    }

    @Test
    @DisplayName("test3")
    public void test3() {
        String name = "小三";
        String str = "'年纪轻轻，奈何做贼啊！' + name + '同学'";
        Map<String, Object> env = new HashMap<>();
        env.put("name", name);
        String expression = "string.length(" + str + ")";
        String result = "年纪轻轻，奈何做贼啊！" + name + "同学";
        Assertions.assertEquals(result.length(), (Long) AviatorEvaluator.execute(expression, env));
    }

    @Test
    @DisplayName("test4")
    public void test4() {
        String expression = "a-(b-c)>100";
        Expression compileExp = AviatorEvaluator.compile(expression, true);
        Map<String, Object> env = new HashMap<>();
        env.put("a", 12);
        env.put("b", 23);
        env.put("c", 30);
        Boolean result = (Boolean) compileExp.execute(env);
        System.out.println("result:" + result);
    }


    @Test
    @DisplayName("test5")
    public void test5() {
        String expression = "min(a,b)";
        Expression compileExp = AviatorEvaluator.compile(expression, true);
        Map<String, Object> env = new HashMap<>();
        env.put("a", 12);
        env.put("b", 23);
        Integer result = (Integer) compileExp.execute(env);
        System.out.println("最小值为:" + result);
    }

    @Test
    @DisplayName("test6")
    public void test6() {
        AviatorEvaluator.addFunction(new QuestionAnswerFunction());
        String expression = "questionAnswer(4412) - questionAnswer(4413) - questionAnswer(4414)";
        Expression compileExp = AviatorEvaluator.compile(expression, true);
        Map<String, Object> env = new HashMap<>();
        env.put("4412", 5);
        env.put("4413", 1);
        env.put("4414", 2);
        Long result = (Long) compileExp.execute(env);
        System.out.println("值为:" + result);
    }

    @Test
    @DisplayName("test7")
    public void test7() {
        PersonModel personModel = new PersonModel(UUID.randomUUID().toString(), "person1", Arrays.asList("腹泻", "咳嗽", "手术"));
        Map<String, Object> env = new HashMap<>();
        env.put("person", personModel);
        String expression = "person.name";
        Expression compileExp = AviatorEvaluator.compile(expression, true);
        String result = (String) compileExp.execute(env);
        System.out.println(result);

        expression = "person.list[1]";
        compileExp = AviatorEvaluator.compile(expression, true);
        result = (String) compileExp.execute(env);
        System.out.println(result);

    }

    @Test
    @DisplayName("test8")
    public void test8() {
        AviatorEvaluator.addFunction(new InFunction());

        PersonModel personModel = new PersonModel(UUID.randomUUID().toString(), "person1", Arrays.asList("腹泻", "耳痛", "下肢麻木"));
        Map<String, Object> env = new HashMap<>();
        env.put("person", personModel);
        env.put("collection", Arrays.asList("腹泻1", "咳嗽", "手术", "发热", "咽痛"));

        String expression = "inSeq(person.list[1],collection)";
        Expression compileExp = AviatorEvaluator.compile(expression, true);
        Boolean result = (Boolean) compileExp.execute(env);
        System.out.println(result);

        expression = "include(collection,person.list[0])";
        compileExp = AviatorEvaluator.compile(expression, true);
        result = (Boolean) compileExp.execute(env);
        System.out.println(result);
    }

    @Test
    @DisplayName("test9")
    public void test9() {
        AviatorEvaluator.addFunction(new PowFunction());
        /**
         * 3、输入框内设置的字母为自然数（如：π，e等）
         * 4、定义计算符号（+、（）、-、/、*、^、%、√）
         */

        String str = "{2 * [ 2 *2 + (2+5)]}";

        System.out.println("替换前:" + str);

        str = ScoreUtil.parse(str);
        System.out.println("替换后:" + str);

        Long result = (Long) AviatorEvaluator.execute(str);
        System.out.println("结果：" + result);

        Double result2 = (Double) AviatorEvaluator.execute("pow(2,(3+5))");
        System.out.println(result2);

        String str2 = " (1 + e^(6.8272 + (0.0391×年龄) + (0.7917×吸烟史) + (1.3388×恶性肿瘤史) + (0.1274×直径) + (1.040×肺结节影像学特征) + (0.7838×位置) )";


        str2 = ScoreUtil.parse(str2);
        System.out.println("替换后:" + str2);

        String str4 = " e^(6.8272 + (0.0391×年龄) + (0.7917×吸烟史) + (1.3388×恶性肿瘤史) + (0.1274×直径) + (1.040×肺结节影像学特征) + (0.7838×位置) )" +
                " * (1 + e ^ (6.8272 + (0.0391×年龄) + (0.7917×吸烟史) + (1.3388×恶性肿瘤史) + (0.1274×直径) + (1.040×肺结节影像学特征) + (0.7838×位置) ) )" +
                " * (1 + ((2+3 )* 5) ^ (6.8272 + (0.0391×年龄) + (0.7917×吸烟史) + (1.3388×恶性肿瘤史) + (0.1274×直径) + (1.040×肺结节影像学特征) + (0.7838×位置) ))" +
                " * (2^34)" +
                "* ((2+3)*5^2*(3)+(Q1+Q23) )" +
                "* ((Q11+23) * 23√(1+2*(2+3)/23+(Q34)) )";
        System.out.println("result: " + ScoreUtil.parse(str4));


        Double result23 = (Double) AviatorEvaluator.execute("math.pow(8,1.0/3)");
        System.out.println(result23);
    }


}
