package com.coral.test.flink.dto;


import com.coral.base.common.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WordInfo {

    /**
     * 第一个单词
     */
    private String first;

    /**
     * 单词
     */
    private String word;

    /**
     * 单词长度
     */
    private Integer length;


    public static Optional<WordInfo> of(String letter) {
        if (StringUtils.isBlank(letter)) {
            return Optional.empty();
        }
        String firstWord = String.valueOf(letter.charAt(0));
        return Optional.of(new WordInfo(firstWord, letter, letter.length()));
    }

}
