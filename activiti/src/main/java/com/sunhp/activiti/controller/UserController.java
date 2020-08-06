package com.sunhp.activiti.controller;

import com.sunhp.activiti.entity.User;
import com.sunhp.activiti.enums.ResponseCodeEnum;
import com.sunhp.activiti.exception.MyException;
import com.sunhp.activiti.service.UserService;
import com.sunhp.activiti.vo.ResultVO;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * (User)表控制层
 *
 * @author sunhp
 * @since 2020-04-13
 */
@RestController
@RequestMapping("user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    /**
     * 服务对象
     */
    @Autowired
    private UserService userService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @ApiOperation(value = "获取单个用户信息")
    @GetMapping("selectOne")
    public User selectOne(Integer id) {
        return this.userService.queryById(id);
    }
    @PostMapping("te")
    public ResultVO<User> selectOnee(@RequestParam("id") Integer id) {
        logger.info("id,{}", id);
        return new ResultVO<User>(ResponseCodeEnum.SUCCESS, userService.queryById(id));
    }

    @GetMapping("select")
    public ResultVO<User> select(Integer id) {
        try {
            int i = 1 / 0;
        } catch (Exception e) {
            throw new MyException(ResponseCodeEnum.UNEXPECTED_EXCEPTION);
        }
        return new ResultVO<User>(ResponseCodeEnum.SUCCESS, userService.queryById(id));
    }
    @PostMapping("insert")
    public ResultVO insert(@RequestBody User user){
        userService.insert(user);
        return new ResultVO("200","添加用户成功",user);
    }

    /**
     * 批量插入测试
     *
     * @return
     */
    @GetMapping("insertMult")
    public String insertMult() {
        List<User> userList = new ArrayList<>();
        //测试批量插入
        for (int i = 0; i < 5; i++) {
            User user = new User();
            user.setUsername("a"+i);
            user.setPassword("password"+i);
            //user.setRealname("test");
            userList.add(user);
        }
        System.out.println(userList);
        userService.insetMult(userList);
        return "success";
    }
    @GetMapping("updateMult")
    public String updateMult() {
        List<User> userList = new ArrayList<>();
        //测试批量更新
        User user1 = new User();
        user1.setId(3);
        user1.setUsername("zhang");

        User user2 = new User();
        user2.setId(4);
        user2.setUsername("li");
        userList.add(user1);
        userList.add(user2);

        userService.updateMult(userList);
        return "success";
    }
}