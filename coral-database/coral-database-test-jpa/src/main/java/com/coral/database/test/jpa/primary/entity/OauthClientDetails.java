package com.coral.database.test.jpa.primary.entity;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;


import com.coral.base.common.jpa.entity.IdentityIdKey;
import com.coral.base.common.jpa.enums.GlobalEnabledEnum;
import lombok.Data;
import lombok.ToString;

/**
 * @author huss
 */
@Entity
@Table
@Data
@ToString(callSuper = true)
public class OauthClientDetails extends IdentityIdKey {

    private String clientId;

    private String resourceIds;

    private String clientSecret;

    private String scope;

    private String authorizedGrantTypes;

    private String webServerRedirectUri;

    private String authorities;

    private Integer accessTokenValidity;

    private Integer refreshTokenValidity;

    private String additionalInformation;

    private String autoapprove;

    @Convert(converter = GlobalEnabledEnum.Convert.class)
    private GlobalEnabledEnum enabled = GlobalEnabledEnum.ENABLE;

}
