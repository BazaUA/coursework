package com.bazalytskyi.coursework.dao;

import com.bazalytskyi.coursework.entities.PrivilegeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPrivilegeDAO extends JpaRepository<PrivilegeEntity,Long> {
    PrivilegeEntity findByName(String username);
}
