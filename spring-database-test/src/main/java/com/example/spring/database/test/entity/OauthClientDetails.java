package com.example.spring.database.test.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.example.spring.common.jpa.entity.IdentityIdKey;

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

}
