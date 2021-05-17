package com.projects.travelblog.service;

import com.projects.travelblog.entity.Blog;
import com.projects.travelblog.entity.Comment;

import java.util.List;

public interface CommentService {

    public Comment saveComment(Comment comment);

    public Comment getCommentById(int id);

    public List<Comment> getCommentsByBlog(Blog blog);
}
