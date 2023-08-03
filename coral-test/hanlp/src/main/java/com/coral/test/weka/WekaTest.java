package com.coral.test.weka;

import weka.attributeSelection.CfsSubsetEval;
import weka.attributeSelection.GreedyStepwise;
import weka.classifiers.Classifier;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;
import weka.filters.Filter;
import weka.filters.supervised.attribute.AttributeSelection;

import java.util.Arrays;

public class WekaTest {

    public static void main(String[] args) throws Exception {
        Instances instances = ConverterUtils.DataSource.read("C://Users/huss/Desktop/test5.csv");
        instances.setClassIndex(0);
        System.out.println("instances:" + instances);
        System.out.println("------------");

        // 特征选择
//        AttributeSelection filter = new AttributeSelection();
//        CfsSubsetEval eval = new CfsSubsetEval();
//        GreedyStepwise search = new GreedyStepwise();
//        search.setSearchBackwards(true);
//        filter.setEvaluator(eval);
//        filter.setSearch(search);
//        filter.setInputFormat(instances);
//
//        Instances newInstances = Filter.useFilter(instances, filter);//使用特征选择方法，生成新的训练数据
//
//        System.out.println("newInstances:" + newInstances);

        Classifier forest = new RandomForest();
        forest.buildClassifier(instances);

        Instances instancesTest = ConverterUtils.DataSource.read("C://Users/huss/Desktop/test2.csv");
        instancesTest.setClassIndex(0); //设置分类属性所在行号（第一行为0号），

        System.out.println(">>>>>>instancesTest:" + instancesTest.instance(0));

//        Evaluation classifierEval = new Evaluation(instancesTest);
//        classifierEval.evaluateModel(forest, instancesTest);
//        System.out.println(classifierEval.toSummaryString("\nResults\n\n", false));
//        System.out.println(classifierEval.areaUnderROC(0));

//        double res = forest.classifyInstance(instancesTest.instance(0));
//        System.out.println(res);

        double[] res = forest.distributionForInstance(instancesTest.firstInstance());
        Arrays.stream(res).forEach(System.out::println);

    }
}
