package com.bazalytskyi.coursework.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public class RefreshTokenDao implements IRefreshTokenDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insert(String username, String token, long expires) {
        String sql = "INSERT INTO refresh_token "
                + "(username, token, expires) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, username, token, new Timestamp(expires));
    }

    public int updateIfNotExpired(String username, String token, long expiration) {
        String sql = "UPDATE refresh_token "
                + "SET expires = ? "
                + "WHERE username = ? "
                + "AND token = ? "
                + "AND expires > ?";
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Timestamp expires = new Timestamp(now.getTime() + expiration);
        return jdbcTemplate.update(sql, expires, username, token, now);
    }

    public int remove(String username, String token){
        String sql = "DELETE FROM refresh_token "
                + "WHERE username = ? "
                + "AND token = ?";
        return jdbcTemplate.update(sql, username, token);
    }

    @Override
    public int remove(String username) {
        String sql = "DELETE FROM refresh_token "
                + "WHERE username = ? ";
        return jdbcTemplate.update(sql, username);
    }
}