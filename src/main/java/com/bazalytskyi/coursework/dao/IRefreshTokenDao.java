package com.bazalytskyi.coursework.dao;

public interface IRefreshTokenDao {
    void insert(String username, String refreshToken, long l);
    int updateIfNotExpired(String username, String token, long expiration);
    int remove(String username, String token);

    int remove(String username);
}
