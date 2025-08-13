package com.z.common.utils;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.z.common.core.redis.RedisCache;
import com.z.common.exception.ServiceException;
import com.z.common.exception.UnloginException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * 维护用户token
 */

@Component
public class UserTokenManager {
    @Resource
    private RedisCache redisService;


    public static final String PREFIX = "wx_teacher_info:";
    public static final String ID = "wx_teacher_info_id:";
    public static final String PATRIARCH_PREFIX = "wx_patriarch_info:";
    public static final String PATRIARCH_ID = "wx_patriarch_info_id:";
    public static final Long EXPIRETIME = 1L;


    public UserToken auth(HttpServletRequest request) {
        String cacheObject = redisService.getCacheObject(PREFIX + request.getHeader("Authorization"));
        UserToken userToken = JSON.parseObject(cacheObject, UserToken.class);

        if (userToken == null) {
            return null;
        }
        Long userId = userToken.getUserId();
        if (userToken.getExpireTime().isBefore(LocalDateTime.now())) {
            redisService.deleteObject(PREFIX + userToken.getToken());
            redisService.deleteObject(ID + userId);
            return null;
        }
        if (userId == null) {
            throw new UnloginException();
        }
        userToken = new UserToken();
        // String token = redisService.getCacheObject(RedisKeys.ACCESS_TOKEN.getPrefix());
        String cacheUserToken = redisService.getCacheObject(ID + userId);
        userToken.setToken(cacheUserToken);
        userToken.setUserId(userId);
        return userToken;
    }

    public UserToken patriarchAuth(HttpServletRequest request) {
        String cacheObject = redisService.getCacheObject(PATRIARCH_PREFIX + request.getHeader("Authorization"));
        UserToken userToken = JSON.parseObject(cacheObject, UserToken.class);

        if (userToken == null) {
            return null;
        }
        Long userId = userToken.getUserId();
        if (userToken.getExpireTime().isBefore(LocalDateTime.now())) {
            redisService.deleteObject(PATRIARCH_PREFIX + userToken.getToken());
            redisService.deleteObject(PATRIARCH_ID + userId);
            return null;
        }
        if (userId == null) {
            throw new UnloginException();
        }
        userToken = new UserToken();
        // String token = redisService.getCacheObject(RedisKeys.ACCESS_TOKEN.getPrefix());
        String cacheUserToken = redisService.getCacheObject(PATRIARCH_ID + userId);
        userToken.setToken(cacheUserToken);
        userToken.setUserId(userId);
        return userToken;
    }

    public UserToken generateToken(Long id, String PREFIX, String ID) {
        if (id == null) {
            throw new ServiceException();
        }
        UserToken userToken = null;
        Boolean flag = redisService.hasKey(ID + id);
        if (flag == null || !flag) {
            String token = CharUtil.getRandomString(32);

            LocalDateTime update = LocalDateTime.now();
            LocalDateTime expire = update.plusDays(1);

            userToken = new UserToken();
            userToken.setToken(token);
            userToken.setUpdateTime(update);
            userToken.setUserId(id);
            userToken.setExpireTime(expire);
            redisService.setCacheObject(PREFIX + token, JSONObject.toJSONString(userToken), Math.toIntExact(EXPIRETIME), TimeUnit.DAYS);
            redisService.setCacheObject(ID + id, JSONObject.toJSONString(userToken), Math.toIntExact(EXPIRETIME), TimeUnit.DAYS);
        } else {
            String cacheObject = redisService.getCacheObject(ID + id);
            userToken = JSON.parseObject(cacheObject, UserToken.class);

        }


        return userToken;
    }


}
