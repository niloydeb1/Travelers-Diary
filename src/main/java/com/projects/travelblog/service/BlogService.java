package com.projects.travelblog.service;

import com.projects.travelblog.entity.Blog;

import java.util.List;

public interface BlogService {
    public Blog saveBlog(Blog blog);

    public List<Blog> getBlogs();

    public Blog getBlogById(int id);

    public List<Blog> getBlogsByKeyword(String keyword);
}
