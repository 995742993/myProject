package com.xwz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xwz.common.ErrorCode;
import com.xwz.exception.BusinessException;
import com.xwz.model.domain.User;
import com.xwz.service.UserService;
import com.xwz.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.xwz.constant.UserConstant.USER_LOGIN_STATE;

/**
 * 该类用于做业务逻辑的处理，例如注册，登录
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    /**
     * 盐值，用于混淆密码
     */
    public static final String SALT = "yupi";

    public long userRegister(String userAccount, String userPassword, String checkPassword, String planetCode){

        //一 校验数据
        //1.传入的对象有一个是空那就返回真
        if (StringUtils.isAnyBlank(userAccount,userPassword,checkPassword,planetCode)){
            throw new BusinessException(ErrorCode.PARAMS_ERR0R,"参数为空");
        }
        if (userAccount.length()<4){
            throw new BusinessException(ErrorCode.PARAMS_ERR0R,"用户名至少4位");
        }
        if (userPassword.length()<8||checkPassword.length()<8){
            throw new BusinessException(ErrorCode.PARAMS_ERR0R,"密码至少8位");
        }
        if (planetCode.length()>5){
            throw new BusinessException(ErrorCode.PARAMS_ERR0R,"星球编号至多5位");
        }

        //2.账户规则：字母开头，5-16字符，允许字母数字下划线，不可包含特殊字符
        String validPattern = "^[a-zA-Z][a-zA-Z0-9_]{4,15}$";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (!matcher.find()){
            throw new BusinessException(ErrorCode.PARAMS_ERR0R,"用户名不符合要求");
        }
        //3.密码和校验密码相同
        if (!userPassword.equals(checkPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERR0R,"输入密码不一致");
        }
        //4.查询数据库，进行用户名重复校验,一般把需要调用查询数据库的方法放在最下面，有利于执行效率
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("userAccount",userAccount);
        long count = this.count(qw);
        if (count>0){
            throw new BusinessException(ErrorCode.PARAMS_ERR0R,"用户名已存在");
        }

        //5.星球编号不可重复
        qw = new QueryWrapper<>();
        qw.eq("planetCode",planetCode);
        count = this.count(qw);
        if (count>0){
            throw new BusinessException(ErrorCode.PARAMS_ERR0R,"星球编号已存在");
        }

        //二 密码加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT+userPassword).getBytes());
        System.out.println(encryptPassword);

        //三 插入数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        user.setPlanetCode(planetCode);
        boolean save = this.save(user);
        if (!save){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"数据库保存数据时出错");
        }
        return user.getId();
    }

    @Override
    public User doLogin(String userAccount, String userPassword, HttpServletRequest request) {
        //一 校验数据
        //1.传入的对象有一个是空那就返回真
        if (StringUtils.isAnyBlank(userAccount,userPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERR0R,"用户名或密码不能为空");
        }
        if (userAccount.length()<4){
            throw new BusinessException(ErrorCode.PARAMS_ERR0R,"用户名至少4位");
        }
        if (userPassword.length()<8){
            throw new BusinessException(ErrorCode.PARAMS_ERR0R,"密码至少8位");
        }
        //2.账户规则：字母开头，5-16字符，允许字母数字下划线，不可包含特殊字符
        String validPattern = "^[a-zA-Z][a-zA-Z0-9_]{4,15}$";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (!matcher.find()){
            throw new BusinessException(ErrorCode.PARAMS_ERR0R,"用户名不符合要求");
        }

        // 3.密码加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT+userPassword).getBytes());

        //4.查询数据库，条件是用户名和密码相等
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.eq(User::getUserAccount, userAccount).eq(User::getUserPassword, encryptPassword);
        User user = this.getOne(lqw);
        //用户不存在
        if (user==null){
            //进行日志记录
            log.info("user login failed,userAccount cannot match userPassword");
            throw new BusinessException(ErrorCode.PARAMS_ERR0R,"用户名或密码错误");
        }
        //进行用户信息脱敏
        User safetyUser = getSafetyUser(user);
        //记录用户的登录态，通过Tomcat服务器的session会话
        request.getSession().setAttribute(USER_LOGIN_STATE,safetyUser);
        return safetyUser;
    }

    /**
     * 用户数据脱敏处理
     * @param originUser 未脱敏
     * @return 已脱敏
     */
    @Override
    public User getSafetyUser(User originUser){
        if (originUser==null){
            return null;
        }
        //如何进行用户信息脱敏？
        //创建一个新的对象来进行敏感数据的剔除
        User safetyUser = new User();
        safetyUser.setId(originUser.getId());
        safetyUser.setUsername(originUser.getUsername());
        safetyUser.setUserAccount(originUser.getUserAccount());
        safetyUser.setAvatarUrl(originUser.getAvatarUrl());
        safetyUser.setGender(originUser.getGender());
        safetyUser.setPhoto(originUser.getPhoto());
        safetyUser.setEmail(originUser.getEmail());
        safetyUser.setRole(originUser.getRole());
        safetyUser.setUserStatus(originUser.getUserStatus());
        safetyUser.setCreateTime(originUser.getCreateTime());
        safetyUser.setPlanetCode(originUser.getPlanetCode());
        return safetyUser;
    }

    @Override
    public Integer userLogout(HttpServletRequest request){
        log.info("用户的cookie为："+request.getSession().getAttribute(USER_LOGIN_STATE)+" 即将被移除");
        //移除登录态
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return 1;
    }

}




