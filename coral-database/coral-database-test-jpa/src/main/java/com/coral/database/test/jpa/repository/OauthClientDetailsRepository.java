package com.coral.database.test.jpa.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.coral.base.common.jpa.repository.JpaBaseRepository;
import com.coral.database.test.jpa.entity.OauthClientDetails;

@Repository
public interface OauthClientDetailsRepository extends JpaBaseRepository<OauthClientDetails, Long> {

    List<OauthClientDetails> findByClientId(String clientId);
}
