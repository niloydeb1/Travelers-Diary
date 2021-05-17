package com.projects.travelblog.entity;

import com.projects.travelblog.util.TableNames;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import static java.util.Objects.isNull;

@Slf4j
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = TableNames.BLOG)
public class Blog {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int bid;

    private String title;

    private String keyword;

    private String content;

    private String img_name;

    private String date;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "uid", referencedColumnName = "uid")
    private User user;

    @OneToMany(mappedBy = "blog")
    private List<Comment> comment = new ArrayList<Comment>();

    public Blog(String title, String keyword, String content, String img_name) {
        this.title = title;
        this.keyword = keyword;
        this.content = content;
        this.img_name = img_name;
    }

    public String getCurrentDateTime() {

        DateFormat df = new SimpleDateFormat("HH:mm dd.MM.yyyy");
        Date dateobj = new Date();
        return df.format(dateobj);
    }

    public String getShortContent(String content) {

        if (content.length() <= 200) return (content + "...");
        return (content.substring(0, 200) + "...");

    }

    public List<Comment> getSortedComments(List <Comment> comments) {
        comments.sort(Comparator.comparing(Comment::getCid));
        return comments;
    }

    @Transient
    public String getImgPath() {

        if (isNull(img_name) || isNull(bid)) return null;

        return "/src/main/resources/blog-images/" + bid + "/" + img_name;
    }
}
