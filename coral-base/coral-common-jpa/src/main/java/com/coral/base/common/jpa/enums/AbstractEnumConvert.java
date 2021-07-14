package com.coral.base.common.jpa.enums;

import com.coral.base.common.enums.IEnum;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.AttributeConverter;
import java.io.Serializable;
import java.util.Arrays;

/**
 * @author huss
 */
@Slf4j
public abstract class AbstractEnumConvert<E extends Enum<E> & IEnum<E, R>, R extends Serializable>
        implements AttributeConverter<E, R> {

    private Class<E> clazz;


    public AbstractEnumConvert(Class<E> clazz) {
        log.debug("#####clazz:{}", clazz);
        this.clazz = clazz;
    }

    @Override
    public R convertToDatabaseColumn(E attribute) {
        log.debug(">>>>>convertToDatabaseColumn attribute:{}", attribute);
        return attribute == null ? null : attribute.getCode();
    }

    @Override
    public E convertToEntityAttribute(R dbData) {
        log.debug(">>>>>convertToEntityAttribute data:{}", dbData);
        return dbData == null ? null : this.getByCode(clazz, dbData);
    }

    private E getByCode(Class<E> cls, R code) {
        return Arrays.stream(cls.getEnumConstants()).filter(e -> e.getCode().equals(code)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown code '" + code + "' for enum " + cls.getName()));
    }
}
