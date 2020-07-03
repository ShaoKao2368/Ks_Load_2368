package com.blog.pblog;

import com.blog.model.entity.Role;
import com.blog.model.entity.User;
import com.blog.service.IRoleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;

/*
角色测试
2020-7-3
lwy
 */
@SpringBootTest
public class RoleTest {
    @Autowired
    private IRoleService iRoleService;
    @Test
    void testFindRole(){
        User user=new User();
        user.setId(1);
        //原代码之所以报错是因为返回的类型不对导致的。
        List<Role> list = iRoleService.findRoleByLoginUser(user);
        for (Role r:list){
            System.out.println(r.toString());
        }
    }
}
