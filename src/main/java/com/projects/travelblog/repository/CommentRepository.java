package com.projects.travelblog.repository;

import com.projects.travelblog.entity.Blog;
import com.projects.travelblog.entity.Comment;
import com.projects.travelblog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findByBlog(Blog blog);
}
