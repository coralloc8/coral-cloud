package com.example.spring.common.jpa.entity;

import javax.persistence.Convert;
import javax.persistence.MappedSuperclass;

import com.example.spring.common.jpa.enums.GlobalDeletedEnum;
import com.example.spring.common.jpa.enums.GlobalEnabledEnum;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author huss
 */
@Getter
@Setter
@ToString(callSuper = true)
@MappedSuperclass
public class IdAndStatusKey extends IdentityIdKey {

    @Convert(converter = GlobalEnabledEnum.Convert.class)
    protected GlobalEnabledEnum enabled = GlobalEnabledEnum.ENABLE;

    @Convert(converter = GlobalDeletedEnum.Convert.class)
    protected GlobalDeletedEnum deleted = GlobalDeletedEnum.NO;

}
