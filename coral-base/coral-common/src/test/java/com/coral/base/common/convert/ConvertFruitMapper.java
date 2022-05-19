package com.coral.base.common.convert;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author huss
 * @version 1.0
 * @className ConvertFruitMapper
 * @description 水果转换
 * @date 2022/5/19 9:16
 */
@Mapper
public interface ConvertFruitMapper {

    ConvertFruitMapper INSTANCE = Mappers.getMapper(ConvertFruitMapper.class);


    @Mapping(source = "fruitName", target = "name")
    ConvertFruitModelVO convert(ConvertFruitModel convertFruitModel);
}
