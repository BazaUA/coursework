package com.bazalytskyi.coursework.dao;

import com.bazalytskyi.coursework.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IRoleDAO extends JpaRepository<RoleEntity,Long> {
    RoleEntity findByName(String name);
}
