package com.bazalytskyi.coursework.services;

import com.bazalytskyi.coursework.entities.SessionUser;
import com.bazalytskyi.coursework.entities.UserDto;
import com.bazalytskyi.coursework.entities.UserEntity;

public interface IUserService {

    UserEntity registerNewUserAccount(UserDto accountDto) ;

    SessionUser loadUserByUsername(String username);

    UserEntity getUserByUsername(String username);

    UserEntity getUserById(long id);

    UserEntity getUser(String email, String pass);
}
