package com.projects.travelblog.service;

import com.projects.travelblog.entity.Blog;
import com.projects.travelblog.entity.Comment;
import com.projects.travelblog.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService{

    private final CommentRepository repository;

    public CommentServiceImpl(CommentRepository repository) {
        this.repository = repository;
    }

    public Comment saveComment(Comment comment) {
        return repository.save(comment);
    }

    public Comment getCommentById(int id) {
        return repository.findById(id).orElse(null);
    }

    public List<Comment> getCommentsByBlog(Blog blog) {
        return repository.findByBlog(blog);
    }
}
