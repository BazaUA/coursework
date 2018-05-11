package com.bazalytskyi.coursework.listners;

import com.bazalytskyi.coursework.dao.IPrivilegeDAO;
import com.bazalytskyi.coursework.dao.IRoleDAO;
import com.bazalytskyi.coursework.dao.IUserDAO;
import com.bazalytskyi.coursework.entities.PrivilegeEntity;
import com.bazalytskyi.coursework.entities.RoleEntity;
import com.bazalytskyi.coursework.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {
    boolean alreadySetup = true;

    @Autowired
    private IUserDAO userRepository;

    @Autowired
    private IRoleDAO roleRepository;

    @Autowired
    private IPrivilegeDAO privilegeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup)
            return;
        PrivilegeEntity readPrivilege
                = createPrivilegeIfNotFound("READ_PRIVILEGE");
        PrivilegeEntity writePrivilege
                = createPrivilegeIfNotFound("WRITE_PRIVILEGE");

        List<PrivilegeEntity> adminPrivileges = Arrays.asList(
                readPrivilege, writePrivilege);
        createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        createRoleIfNotFound("ROLE_USER", Arrays.asList(readPrivilege));

        RoleEntity adminRole = roleRepository.findByName("ROLE_ADMIN");
        UserEntity user = new UserEntity();
        user.setUsername("Test");
        user.setEmail("test");
        user.setPassword(passwordEncoder.encode("test"));
        user.setRole(adminRole);
        user.setEnabled(true);
        userRepository.save(user);

        alreadySetup = true;
    }

    @Transactional
    PrivilegeEntity createPrivilegeIfNotFound(String name) {

        PrivilegeEntity privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new PrivilegeEntity(name);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }

    @Transactional
    RoleEntity createRoleIfNotFound(
            String name, Collection<PrivilegeEntity> privileges) {

        RoleEntity role = roleRepository.findByName(name);
        if (role == null) {
            role = new RoleEntity(name);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
        return role;
    }
}
