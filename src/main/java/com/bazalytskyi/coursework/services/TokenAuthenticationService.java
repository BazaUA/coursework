package com.bazalytskyi.coursework.services;

import com.bazalytskyi.coursework.dao.IRefreshTokenDao;
import com.bazalytskyi.coursework.entities.SessionUser;
import com.bazalytskyi.coursework.jwt.TokenHandler;
import com.bazalytskyi.coursework.jwt.UserAuthentication;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class TokenAuthenticationService implements ITokenAuthenticationService {
    @Autowired
    private TokenHandler tokenHandler;
    @Autowired
    private IRefreshTokenDao refreshTokenDao;
    private String ACCESS_TOKEN = "access_token";
//    @Autowired
//    Request request;

    public void addAuthentication(HttpServletResponse response, SessionUser userPrincipal) {
        Cookie cookie = new Cookie(ACCESS_TOKEN, tokenHandler.createAccessToken(userPrincipal));
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        //cookie.setSecure(request.isSecure());
        response.addCookie(cookie);
    }

    public Authentication getAuthentication(HttpServletRequest request, HttpServletResponse response) {
        Cookie tokenCookie = WebUtils.getCookie(request, ACCESS_TOKEN);
        String accessToken;
        if (tokenCookie != null && !(accessToken = tokenCookie.getValue()).isEmpty()) {
            try {
                //Пробуем получить данные пользователя из токена
                SessionUser user = tokenHandler.parseSessionUser(accessToken);//Выбрасывает ExpiredJwtException если токен просрочен
                //Возвращаем аутентификацию с этим пользователем
                return new UserAuthentication(user);
            } catch (ExpiredJwtException ex) {
                try {
                    //Если токен просрочен пробуем обновить данные пользователя
                    SessionUser user = tokenHandler.updateSessionUser(ex.getClaims());//Выбрасывает JwtException если refresh токен просрочен или не найден
                    //И добавляем в ответ новый access токен с обновленными данными и тем же id (refresh токеном) внутри
                    Cookie access = new Cookie(ACCESS_TOKEN, tokenHandler.createAccessToken(user, ex.getClaims().getId()));
                    access.setPath("/");
                    access.setHttpOnly(true);
                    //access.setSecure(isRequestSecured(request));
                    response.addCookie(access);
                    //Возвращаем аутентификацию с обновленным пользователем
                    return new UserAuthentication(user);
                } catch (JwtException e) {
                    Claims claims = ex.getClaims();
                    //Обнуляем токен в куки и удаляем из базы данных
                    resetToken(response, claims.getSubject(), claims.getId());
                    return null;
                }
            } catch (JwtException ex) {
                return null;
            }
        }
        return null;
    }

    private void resetToken(HttpServletResponse response, String username, String refreshToken) {
        Cookie refresh = new Cookie(ACCESS_TOKEN, null);
        refresh.setPath("/");
        refresh.setMaxAge(0);
        response.addCookie(refresh);
        refreshTokenDao.remove(username, refreshToken);
    }
}
