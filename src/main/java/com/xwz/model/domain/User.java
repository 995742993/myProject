package com.xwz.model.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 用户
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 昵称
     */
    private String username;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 用户头像
     */
    private String avatarUrl;

    /**
     * 性别
     */
    private Byte gender;

    /**
     * 密码

     */
    private String userPassword;

    /**
     * 
     */
    private String photo;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 用户状态
     */
    private Integer userStatus;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 逻辑删除，要使得它生效需要设置配置文件并且加上注解，
     * 这样默认在查询的时候就会在条件最后加上isDelete = 0，意为仅查询未被逻辑删除的部分
     */
    @TableLogic
    private Byte isDelete;

    /**
     * 用户角色；0-普通用户； 1-管理员
     */
    private Integer role;

    /**
     * 星球编号
     */
    private String planetCode;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}