package com.xwz.service;

import com.xwz.model.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;

@SpringBootTest
class UserServiceTest {
    @Resource
    private UserService userService;

    @Test
    public void testSave(){
        User user = new User();
        user.setUsername("duxingxi");
        user.setUserAccount("666");
        user.setAvatarUrl("C:\\Users\\sky\\Desktop\\Snipaste_2023-02-20_12-32-09.png");
        user.setGender((byte)0);
        user.setUserPassword("123");
        user.setPhoto("456");
        user.setEmail("789");
        boolean save = userService.save(user);
        System.out.println((save ? "成功" : "失败"));
        System.out.println(user.getId());
        Assertions.assertTrue(save);

    }

    /**
     * 该方法用于测试加密算法
     */
    @Test
    public void testEncrypt(){
        final String SALT = "yupi";
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT+"123456").getBytes());
        System.out.println(encryptPassword);
    }

    /**
     * 用于测试注册的业务逻辑
     */
    @Test
    void userRegister() {
        long l = userService.userRegister("p33333", "qweasd123", "qweasd123", "9877");
        System.out.println(l);

    }

    
}