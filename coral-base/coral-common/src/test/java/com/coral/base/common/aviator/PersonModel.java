package com.coral.base.common.aviator;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author huss
 * @version 1.0
 * @className PersonModel
 * @description PersonModel
 * @date 2022/5/27 10:00
 */
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class PersonModel {

    private String id;

    private String name;

    private List<String> list;

}
