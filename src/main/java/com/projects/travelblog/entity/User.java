package com.projects.travelblog.entity;

import com.projects.travelblog.util.TableNames;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = TableNames.USER)
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int uid;

    private String name;

    private String email;

    private String password;

    private String contact;

    @OneToMany(mappedBy = "user")
    private List<Blog> blog = new ArrayList<Blog>();

    @OneToMany(mappedBy = "user")
    private List<Comment> comment = new ArrayList<Comment>();

    public User(String name, String email, String password, String contact) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.contact = contact;
    }
}