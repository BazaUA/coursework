package com.bazalytskyi.coursework.services;

import com.bazalytskyi.coursework.dao.IRoleDAO;
import com.bazalytskyi.coursework.dao.IUserDAO;
import com.bazalytskyi.coursework.entities.CustomUserDetails;
import com.bazalytskyi.coursework.dto.UserDto;
import com.bazalytskyi.coursework.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {
    @Autowired
    IUserDAO userDao;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    IUserDAO userDAO;

    @Autowired
    IRoleDAO roleDAO;


    @Override
    public UserEntity registerNewUserAccount(UserDto accountDto){
        UserEntity user = new UserEntity();

        user.setUsername(accountDto.getUsername());

        user.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        user.setEmail(accountDto.getEmail());
        user.setRole(roleDAO.findByName(accountDto.getRole()));
        return userDAO.save(user);
    }

    @Override
    public CustomUserDetails loadUserByUsername(String username) {
        UserEntity userEntity = userDAO.findByUsername(username);
        CustomUserDetails user = new CustomUserDetails(userEntity);
        return user;
    }

    @Override
    public UserEntity getUserByUsername(String username) {
        UserEntity userEntity = userDAO.findByUsername(username);

        return userEntity;
    }

    @Override
    public UserEntity getUserById(long id){
        return userDAO.getOne(id);
    }

    @Override
    public UserEntity getUserByLoginAndPassword(String username, String existingPassword) {
       UserEntity userEntity= userDAO.findByUsername(username);
       if(userEntity!=null&&passwordEncoder.matches(existingPassword,userEntity.getPassword()))
           return userEntity;
       else
           return null;
    }
}
