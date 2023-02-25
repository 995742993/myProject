package com.xwz.service;

import com.xwz.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 *
 */
public interface UserService extends IService<User> {

    /**
     * 注册验证
     * @param userAccount 用户名
     * @param userPassword 密码
     * @param checkPassword 校验密码
     * @param planetCode
     * @return 成功：用户id 失败-1
     */
    long userRegister(String userAccount, String userPassword, String checkPassword, String planetCode);

    /**
     * 登录验证
     * @param userAccount 用户名
     * @param userPassword 密码
     * @param request
     * @return 用户信息
     */
    User doLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 用户数据脱敏
     * @param originUser 未脱敏
     * @return 已脱敏
     */
    User getSafetyUser(User originUser);

    /**
     * 用户注销
     * @param request 请求
     * @return 返回一个1代表成功
     */
    Integer userLogout(HttpServletRequest request);
}
