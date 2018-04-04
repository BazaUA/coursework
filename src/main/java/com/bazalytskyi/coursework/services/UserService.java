package com.bazalytskyi.coursework.services;

import com.bazalytskyi.coursework.dao.IRoleDAO;
import com.bazalytskyi.coursework.dao.IUserDAO;
import com.bazalytskyi.coursework.entities.UserDto;
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
//        if (emailExist(accountDto.getEmail())) {
//            throw new EmailExistsException
//                    ("There is an account with that email adress: " + accountDto.getEmail());
//        }
        UserEntity user = new UserEntity();

        user.setUsername(accountDto.getUsername());

        user.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        user.setEmail(accountDto.getEmail());
        user.setRole(roleDAO.findByName(accountDto.getRole()));
        return userDAO.save(user);
    }
}
