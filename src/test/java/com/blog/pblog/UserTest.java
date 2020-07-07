package com.blog.pblog;

import com.blog.model.entity.User;
import com.blog.service.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

/**
 * User测试类
 * zzk
 */
@SpringBootTest
public class UserTest {
    @Autowired
    private IUserService iUserService;
    //测试添加用户
    @Test
    void testAddUser(){     //Test OK
        User user = new User();
        user.setEmail("2368407289@qq.com");
        user.setLoginName("admin4");
        user.setLoginPass("2368407289");
        user.setName("烧烤");
        user.setRegisterTime(new Date());
        user.setValid(true);
        user.setImg("qwe");
        String msg = iUserService.addUser(user);
        System.out.println(msg);
    }

    //测试按登录名查找用户
    @Test
    void testFindByLoginName(){ //Test Ok
        User user = iUserService.findByLoginName("admin");
        if (user != null) {
            System.out.println(user.toString());
        }
    }

    //测试查找所有用户  BUG解决，在全局配置文件`  测试OK
    @Test
    void testFindAllUser(){
        List<User> list = iUserService.findAllUser();
        for (User u : list) {
            System.out.println(u.toString());
        }
    }

    //测试修改用户
    @Test
    void testModifyUser(){      //TestOk
        User user = iUserService.findByLoginName("admin2");
        if (user != null) {
            System.out.println(user.toString());
        }
        user.setName("用户admin2");
        iUserService.modifyUser(user);

        User user1 = iUserService.findByLoginName("admin2");
        if (user1 != null) {
            System.out.println(user1.toString());
        }
    }

    //测试删除用户
    @Test   //Test Ok
    void testDelUser(){
        User user = iUserService.findByLoginName("admin4");
        if (user != null) {
            System.out.println(user.toString());
        }
        iUserService.delUser(user);
        User user1 = iUserService.findByLoginName("admin2");
        if (user1 != null) {
            System.out.println(user1.toString());
        }
    }
}
