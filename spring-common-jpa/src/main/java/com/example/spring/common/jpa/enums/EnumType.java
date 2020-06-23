package com.example.spring.common.jpa.enums;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Objects;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.DynamicParameterizedType;
import org.hibernate.usertype.UserType;

import com.example.spring.common.enums.IEnum;

/**
 * 和hibernate紧耦合了
 * 
 * @author huss
 */
@Deprecated
public class EnumType implements UserType, DynamicParameterizedType {

    private Class enumClass;
    private static final int[] SQL_TYPES = new int[] {Types.INTEGER};

    @Override
    public void setParameterValues(Properties parameters) {
        final ParameterType reader = (ParameterType)parameters.get(PARAMETER_TYPE);
        if (reader != null) {
            enumClass = reader.getReturnedClass().asSubclass(Enum.class);
        }
    }

    /**
     * 枚举存储int值
     * 
     * @return
     */
    @Override
    public int[] sqlTypes() {
        return SQL_TYPES;
    }

    @Override
    public Class returnedClass() {
        return enumClass;
    }

    /**
     * 是否相等，不相等会触发JPA update操作
     * 
     * @param x
     * @param y
     * @return
     * @throws HibernateException
     */
    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        if (x == null && y == null) {
            return true;
        }
        boolean notEqual = (x == null && y != null) || (x != null && y == null);
        if (notEqual) {
            return false;
        }
        return x.equals(y);
    }

    @Override
    public int hashCode(Object x) throws HibernateException {
        return x == null ? 0 : x.hashCode();
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner)
        throws HibernateException, SQLException {
        Object value = rs.getObject(names[0]);
        if (value == null) {
            return null;
        }
        for (Object object : enumClass.getEnumConstants()) {
            if (Objects.equals(value, ((IEnum)object).getCode())) {
                return object;
            }
        }
        throw new RuntimeException(
            String.format("Unknown name value [%s] for enum class [%s]", value, enumClass.getName()));
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session)
        throws HibernateException, SQLException {
        if (value == null) {
            st.setNull(index, SQL_TYPES[0]);
        } else if (value instanceof Integer) {
            st.setInt(index, (Integer)value);
        } else {
            st.setInt(index, (Integer)((IEnum)value).getCode());
        }
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        return value;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        return (Serializable)value;
    }

    @Override
    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return cached;
    }

    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return original;
    }
}
