package com.bazalytskyi.coursework.services;

import com.bazalytskyi.coursework.entities.UserDto;
import com.bazalytskyi.coursework.entities.UserEntity;

public interface IUserService {

    UserEntity registerNewUserAccount(UserDto accountDto) ;
}
