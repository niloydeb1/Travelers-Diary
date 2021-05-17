package com.projects.travelblog.service;


import com.projects.travelblog.entity.Blog;
import com.projects.travelblog.repository.BlogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogServiceImpl implements BlogService{

    private final BlogRepository repository;

    public BlogServiceImpl(BlogRepository repository) {
        this.repository = repository;
    }

    @Override
    public Blog saveBlog(Blog blog) {
        return repository.save(blog);
    }

    @Override
    public List<Blog> getBlogs() {
        return repository.findAll();
    }

    @Override
    public Blog getBlogById(int id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Blog> getBlogsByKeyword(String keyword) {
        return repository.findByKeyword(keyword);
    }
}
