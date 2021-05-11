package com.coral.base.common.excel;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

import com.coral.base.common.DatePattern;
import com.coral.base.common.DateTimeUtil;
import com.coral.base.common.excel.model.ExcelFill;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description: excel 填充
 * @author: huss
 * @time: 2020/11/3 11:00
 */
public class FillExcelTest {

    public static void main(String[] args) {

        ExcelFillHandler excelFillHandler = new ExcelFillHandler();

        String filePath = "C:\\Users\\huss\\Desktop\\test.xlsx";
        String templteFilePath =
            "D:\\idea-workspace\\coral-cloud\\spring-base\\spring-common\\src\\test\\java\\com\\example\\spring\\common\\excel\\excel_fill.xlsx";

        List<ExcelFill.SheetFill> sheetFills = new ArrayList<>();

        // sheet1
        ExcelFill.SheetFill sheetFill = new ExcelFill.SheetFill();
        sheetFill.setSheet(0);

        // 时间
        Map<String, Object> map = new HashMap<>();
        map.put("time", DateTimeUtil.format(LocalDateTime.now(), DatePattern.PATTERN_DATETIME));
        map.put("totalPerson", 3);

        sheetFill.addFillData(map);

        List<Person> persons = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            Person person = new Person((i + 1) + "", "名称" + (i + 1), (int)(Math.random() * 100));
            persons.add(person);
        }

        // 统计
        long nonageCount = persons.parallelStream().filter(e -> e.getAge() < 18).count();
        long youthCount = persons.parallelStream().filter(e -> e.getAge() >= 18 && e.getAge() < 35).count();
        long middleAgedCount = persons.parallelStream().filter(e -> e.getAge() >= 35 && e.getAge() < 60).count();
        long elderlyCount = persons.parallelStream().filter(e -> e.getAge() >= 60).count();

        Statistics statistics = new Statistics(nonageCount, youthCount, middleAgedCount, elderlyCount);
        sheetFill.addFillData("statistics", ExcelFill.DirectionEnum.HORIZONTAL, Arrays.asList(statistics));

        sheetFill.addFillData("persons", persons);

        sheetFills.add(sheetFill);

        // sheet2
        sheetFill = new ExcelFill.SheetFill();
        sheetFill.setSheet(1);

        // 时间
        map = new HashMap<>();
        map.put("time", DateTimeUtil.format(LocalDateTime.now(), DatePattern.PATTERN_DATETIME));
        map.put("totalPerson", 3);

        sheetFill.addFillData(map);

        persons = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            Person person = new Person((i + 1) + "", "名称" + (i + 1), (int)(Math.random() * 100));
            persons.add(person);
        }

        // 统计
        nonageCount = persons.parallelStream().filter(e -> e.getAge() < 18).count();
        youthCount = persons.parallelStream().filter(e -> e.getAge() >= 18 && e.getAge() < 35).count();
        middleAgedCount = persons.parallelStream().filter(e -> e.getAge() >= 35 && e.getAge() < 60).count();
        elderlyCount = persons.parallelStream().filter(e -> e.getAge() >= 60).count();

        statistics = new Statistics(nonageCount, youthCount, middleAgedCount, elderlyCount);
        sheetFill.addFillData("statistics", ExcelFill.DirectionEnum.HORIZONTAL, Arrays.asList(statistics));

        sheetFill.addFillData("persons", persons);

        sheetFills.add(sheetFill);

        // 开始填充
        ExcelFill excelFill = new ExcelFill(templteFilePath, filePath, sheetFills);

        excelFillHandler.fill(excelFill);
    }

    @Getter
    @AllArgsConstructor
    static class Statistics implements Serializable {

        private long nonageCount;
        private long youthCount;

        private long middleAgedCount;

        private long elderlyCount;

    }

    @Getter
    static class Person implements Serializable {
        private String no;

        private String name;

        private int age;

        Person(String no, String name, int age) {
            this.no = no;
            this.name = name;
            this.age = age;
        }
    }

}
