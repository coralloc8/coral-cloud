package com.example.spring.database.test.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.spring.common.jpa.repository.JpaBaseRepository;
import com.example.spring.database.test.entity.OauthClientDetails;

@Repository
public interface OauthClientDetailsRepository extends JpaBaseRepository<OauthClientDetails, Long> {

    List<OauthClientDetails> findByClientId(String clientId);
}
