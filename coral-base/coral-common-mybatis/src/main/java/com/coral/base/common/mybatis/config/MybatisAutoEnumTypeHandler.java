package com.coral.base.common.mybatis.config;

import com.baomidou.mybatisplus.core.handlers.MybatisEnumTypeHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.EnumOrdinalTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author huss
 * @version 1.0
 * @className MyBatisEnumTypeHandler
 * @description 自动枚举
 * @date 2023/2/6 8:26
 */
@Slf4j
public class MybatisAutoEnumTypeHandler<E extends Enum<E>> extends BaseTypeHandler<E> {

    private final BaseTypeHandler<E> typeHandler;


    public MybatisAutoEnumTypeHandler(Class<E> enumClassType) {
        log.info(">>>>>[MybatisAutoEnumTypeHandler] enumClassType:{}", enumClassType);
        if (enumClassType == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }

        if (enumClassType.isEnum() && MybatisEnumTypeHandler.isMpEnums(enumClassType)) {
//            log.info(">>>>>[MybatisAutoEnumTypeHandler] enumClassType:{} MybatisEnumTypeHandler init.", enumClassType);
            this.typeHandler = new MybatisEnumTypeHandler(enumClassType);
        } else {
//            log.info(">>>>>[MybatisAutoEnumTypeHandler] enumClassType:{} EnumOrdinalTypeHandler init.", enumClassType);
            this.typeHandler = new EnumOrdinalTypeHandler<>(enumClassType);
        }

    }

    @SuppressWarnings("Duplicates")
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType)
            throws SQLException {
        typeHandler.setNonNullParameter(ps, i, parameter, jdbcType);
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return typeHandler.getNullableResult(rs, columnName);
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return typeHandler.getNullableResult(rs, columnIndex);
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return typeHandler.getNullableResult(cs, columnIndex);
    }

}