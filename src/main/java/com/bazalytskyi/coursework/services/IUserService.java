package com.bazalytskyi.coursework.services;

import com.bazalytskyi.coursework.entities.CustomUserDetails;
import com.bazalytskyi.coursework.dto.UserDto;
import com.bazalytskyi.coursework.entities.UserEntity;

public interface IUserService {

    UserEntity registerNewUserAccount(UserDto accountDto) ;

    CustomUserDetails loadUserByUsername(String username);

    UserEntity getUserByUsername(String username);

    UserEntity getUserById(long id);


    UserEntity getUserByLoginAndPassword(String username, String existingPassword);
}
