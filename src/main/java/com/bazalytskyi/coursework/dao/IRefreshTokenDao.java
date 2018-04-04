package com.bazalytskyi.coursework.dao;

public interface IRefreshTokenDao {
    void insert(String username, String refreshToken, long l);
}
