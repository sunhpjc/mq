package com.sunhp.activiti.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;

/**
 * (User)实体类
 *
 * @author sunhp
 * @since 2020-04-13
 */
@Data
public class User implements Serializable {
    private static final long serialVersionUID = 229548154917595464L;
    
    private Integer id;
    
    private String username;

    private String password;
    
    private String realname;

    public User() {
    }

    public User(Integer id, String username, String password, String realname) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.realname = realname;
    }
}