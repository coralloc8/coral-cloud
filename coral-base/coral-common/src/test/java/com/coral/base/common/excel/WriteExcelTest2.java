package com.coral.base.common.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.write.handler.AbstractRowWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.coral.base.common.StringUtils;
import com.coral.base.common.excel.model.ExcelWrite;
import com.coral.base.common.json.JsonUtil;
import lombok.*;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author huss
 * @version 1.0
 * @className ChatRecorderSyncServiceTesyt
 * @description todo
 * @date 2021/9/27 16:56
 */
public class WriteExcelTest2 {


    @Test
    @Disabled
    @DisplayName("聊天消息测试")
    public void save() throws IOException {

        String file = "C:\\Users\\Administrator\\Desktop\\fanqie.js";

        List<String> lines = Files.readAllLines(Paths.get(file));

        String source = String.join("", lines);

        Map map = JsonUtil.parse(source, Map.class);

        Map data = (Map) map.get("data");

        List<Map> items = (List<Map>) data.get("items");
        items.remove(0);

        String sheetName = (String) data.get("formName");

        AtomicInteger atomicInteger = new AtomicInteger(0);
        List<Question> questions = items.stream().map(e -> {
            String title = (String) e.get("label");
            int index = 0;
            String no = "";
            while (StringUtils.isNumeric(title.charAt(index) + "")) {
                no += title.charAt(index);
                index++;
            }

            title = title.substring(no.length());
            no = atomicInteger.incrementAndGet() + "";
            List<String> options;
            if (!e.containsKey("items")) {
                options = new ArrayList<>();
            } else {
                List<Map> optionItems = (List<Map>) e.get("items");

                options = optionItems.stream().map(i -> i.get("text").toString()).collect(Collectors.toList());
            }


            return Question.builder()
                    .options(options)
                    .title(title)
                    .no(no)
                    .build();
        }).collect(Collectors.toList());


//        System.out.println(JsonUtil.toJson(questions));

        this.createExcel(questions, sheetName);

    }

    /**
     * 创建excel
     */
    private void createExcel(List<Question> questions, String sheetName) {


        List<QuestionWrite> writes = questions.stream().flatMap(e -> {
                    if (e.getOptions().isEmpty()) {
                        return Stream.of(QuestionWrite.builder()
                                .no(e.getNo())
                                .title(e.getTitle())
                                .option("")
                                .build());
                    } else {
                        return e.getOptions().stream().map(c -> QuestionWrite.builder()
                                .no(e.getNo())
                                .title(e.getTitle())
                                .option(c)
                                .build());
                    }
                })
                .collect(Collectors.toList());

        String fileName = "C:\\Users\\Administrator\\Desktop\\番茄word.xlsx";

        ExcelWrite.SheetWrite sheetWrite = new ExcelWrite.SheetWrite();
        sheetWrite.setSheet(0);
        sheetWrite.setHeadVoClass(QuestionWrite.class);
        sheetWrite.setSheetName(sheetName);
        sheetWrite.setData(writes);

        ExcelWrite excelWrite = new ExcelWrite();
        excelWrite.setFilePath(fileName);
        excelWrite.setSheetWrites(Arrays.asList(sheetWrite));


        ExcelWriteHandler excelWriteHandler = new ExcelWriteHandler(Arrays.asList(new MyLoopMergeStrategy(questions)));
        excelWriteHandler.write(excelWrite);

    }

    public static class MyLoopMergeStrategy extends AbstractRowWriteHandler {

        @Getter
        private List<Question> questions;

        @Getter
        private Map<String, Question> questionMap;

        public MyLoopMergeStrategy(List<Question> questions) {
            this.questions = questions;
            this.questionMap = getQuestions().stream().collect(Collectors.toMap(e -> e.getNo(), v -> v));
        }

        @Override
        public void afterRowDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row,
                                    Integer relativeRowIndex, Boolean isHead) {
            if (isHead) {
                return;
            }
            String no = row.getCell(0).getStringCellValue();
            Question question = questionMap.get(no);
            if (Objects.nonNull(question) && !question.getOptions().isEmpty()) {

                int size = question.getOptions().size();

                CellRangeAddress cellRangeAddress = new CellRangeAddress(row.getRowNum(), row.getRowNum() + size - 1,
                        0, 0);
                writeSheetHolder.getSheet().addMergedRegionUnsafe(cellRangeAddress);
                cellRangeAddress = new CellRangeAddress(row.getRowNum(), row.getRowNum() + size - 1,
                        1, 1);
                writeSheetHolder.getSheet().addMergedRegionUnsafe(cellRangeAddress);
                questionMap.remove(no);
            }
        }
    }


    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Question {
        private String no;
        private String title;
        private List<String> options;
    }


    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class QuestionWrite {
        @ExcelProperty("序号")
        private String no;

        @ExcelProperty("题目")
        private String title;

        @ExcelProperty("选项")
        private String option;
    }


}
