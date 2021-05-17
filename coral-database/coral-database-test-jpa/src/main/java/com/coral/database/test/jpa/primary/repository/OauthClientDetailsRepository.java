package com.coral.database.test.jpa.primary.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.coral.base.common.jpa.repository.JpaBaseRepository;
import com.coral.database.test.jpa.primary.entity.OauthClientDetails;

@Repository
public interface OauthClientDetailsRepository extends JpaBaseRepository<OauthClientDetails, Long> {

    List<OauthClientDetails> findByClientId(String clientId);
}
