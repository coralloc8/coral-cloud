package com.example.spring.database.test.repository;

import java.util.List;

import com.example.spring.common.jpa.enums.GlobalDeletedEnum;
import org.springframework.stereotype.Repository;

import com.example.spring.common.jpa.repository.JpaBaseRepository;
import com.example.spring.database.test.entity.SysUser;

/**
 * @author huss
 */
@Repository
public interface SysUserRepository extends JpaBaseRepository<SysUser, Long> {

    List<SysUser> findByAccountAndDeleted(String username, GlobalDeletedEnum deletedEnum);
}