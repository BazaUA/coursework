package com.bazalytskyi.coursework.dao;

import org.springframework.stereotype.Repository;

@Repository
public class RefreshTokenDao implements IRefreshTokenDao{

    @Override
    public void insert(String username, String refreshToken, long l) {

    }
}
