package com.coral.base.common.convert;

import com.coral.base.common.DatePattern;
import com.coral.base.common.EnumUtil;
import com.coral.base.common.json.JsonUtil;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author huss
 * @version 1.0
 * @className ConvertMapper
 * @description 转换类
 * @date 2022/5/18 16:42
 */
@Mapper(uses = ConvertFruitMapper.class)
public interface ConvertMapper {
    ConvertMapper INSTANCE = Mappers.getMapper(ConvertMapper.class);

    /**
     * ConvertModel转换vo
     *
     * @param convertModel
     * @return
     */
    @Mapping(source = "sex", target = "gender")
    @Mapping(source = "startDate", target = "startDateText", dateFormat = DatePattern.PATTERN_DATE)
    @Mapping(source = "status", target = "statusText")
    @Mapping(source = "age", target = "age")
    @Mapping(source = "firstSon", target = "myFirstSon")
    @Mapping(source = "firstSon.name", target = "myFirstSonName")
    @Mapping(source = "children", target = "myChildren")
    @Mapping(source = "firstFruit", target = "myFirstFruit")
    @Mapping(source = "fruits", target = "myFruits")
    @Mapping(target = "myFullNameText", expression = "java(convertModel.getFirstName() + convertModel.getLastName())")
    ConvertModelVO convert(ConvertModel convertModel);


    @AfterMapping
    default void setFullName(@MappingTarget ConvertModelVO vo, ConvertModel convertModel) {
        vo.setMyFullName(convertModel.getFirstName() + convertModel.getLastName());
    }

    /**
     * ConvertSonModel转换vo
     *
     * @param convertSonModel
     * @return
     */
    @Mapping(source = "name", target = "nameText")
    ConvertModelVO.ConvertSonModelVO convert(ConvertModel.ConvertSonModel convertSonModel);

    /**
     * config转换
     *
     * @param config
     * @return
     */
    default List<ConvertModelVO.ConfigVO> voConfigConvert(String config) {
        return JsonUtil.parseArray(config, ConvertModelVO.ConfigVO.class);
    }

    /**
     * 状态转换
     *
     * @param status
     * @return
     */
    default StatusEnum voStatusConvert(String status) {
        return EnumUtil.codeOf(StatusEnum.class, status);
    }
}
