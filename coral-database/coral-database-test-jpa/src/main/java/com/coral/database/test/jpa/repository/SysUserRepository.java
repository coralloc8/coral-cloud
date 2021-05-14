package com.coral.database.test.jpa.repository;

import java.util.List;

import com.coral.base.common.jpa.enums.GlobalDeletedEnum;
import org.springframework.stereotype.Repository;

import com.coral.base.common.jpa.repository.JpaBaseRepository;
import com.coral.database.test.jpa.entity.SysUser;

/**
 * @author huss
 */
@Repository
public interface SysUserRepository extends JpaBaseRepository<SysUser, Long> {

    List<SysUser> findByAccountAndDeleted(String username, GlobalDeletedEnum deletedEnum);
}