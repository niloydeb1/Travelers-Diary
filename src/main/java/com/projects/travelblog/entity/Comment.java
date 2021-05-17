package com.projects.travelblog.entity;

import com.projects.travelblog.util.TableNames;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = TableNames.COMMENT)
public class Comment {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int cid;

    private String content;

    private String date;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "uid", referencedColumnName = "uid")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bid", referencedColumnName = "bid")
    private Blog blog;

    public Comment(String content) {
        this.content = content;
    }

    public String getCurrentDateTime() {

        DateFormat df = new SimpleDateFormat("HH:mm dd.MM.yyyy");
        Date dateobj = new Date();
        return df.format(dateobj);
    }
}
