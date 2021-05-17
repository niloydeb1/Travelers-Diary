package com.projects.travelblog.controller;

import com.projects.travelblog.entity.Blog;
import com.projects.travelblog.entity.Comment;
import com.projects.travelblog.entity.User;
import com.projects.travelblog.service.BlogService;
import com.projects.travelblog.service.CommentService;
import com.projects.travelblog.service.UserService;
import com.projects.travelblog.util.AttributeNames;
import com.projects.travelblog.util.Mappings;
import com.projects.travelblog.util.ViewNames;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.isNull;

@Slf4j
@Controller
@SessionAttributes(AttributeNames.UID)
public class WebController {

    private final UserService userService;
    private final BlogService blogService;
    private final CommentService commentService;

    public WebController(UserService userService, BlogService blogService, CommentService commentService) {
        this.userService = userService;
        this.blogService = blogService;
        this.commentService = commentService;
    }


    @GetMapping(Mappings.LOGIN)
    public String login(ModelMap model) {
        log.info("UID: " + model.getAttribute(AttributeNames.UID));

        if (!isNull(model.getAttribute(AttributeNames.UID))) {
            return Mappings.REDIRECT_HOME;
        } else {
            return ViewNames.LOGIN;
        }
    }


    @GetMapping(Mappings.SIGNUP)
    public String signup(ModelMap model) {
        if (!isNull(model.getAttribute(AttributeNames.UID))) {
            return Mappings.REDIRECT_HOME;
        } else {
            return ViewNames.SIGNUP;
        }
    }

    @GetMapping(Mappings.LOGOUT)
    public String logout(SessionStatus status) {
        status.setComplete();
        return Mappings.REDIRECT_LOGIN;
    }


    @GetMapping(Mappings.HOME)
    public String home(ModelMap model) {

        if (!isNull(model.getAttribute(AttributeNames.UID))) {

            List<Blog> blogs = blogService.getBlogs();

            // Resizing list of blogs because only 5 blogs will be shown in homepage
            if (blogs.size() > 5)   blogs.subList(5, blogs.size()).clear();

            model.addAttribute(AttributeNames.BLOG_LIST, blogs);

            return ViewNames.HOME;
        } else {
            return Mappings.REDIRECT_LOGIN;
        }
    }


    @GetMapping(Mappings.POST)
    public String post(ModelMap model) {
        if (!isNull(model.getAttribute(AttributeNames.UID))) {
            return Mappings.HOME;
        } else {
            return Mappings.REDIRECT_LOGIN;
        }
    }


    @GetMapping(value = Mappings.BLOG)
    public String exploreBlogs(ModelMap model) {

        if (!isNull(model.getAttribute(AttributeNames.UID))) {
            List<Blog> blogs = blogService.getBlogs();

            int total_page = (int) Math.ceil(blogs.size() / 4.0);
            int start = 0;
            int end = start + 4;
            if(end > blogs.size()) end = blogs.size();
            blogs = blogs.subList(start, end);

            model.addAttribute(AttributeNames.BLOGS, blogs);
            model.addAttribute(AttributeNames.KEYWORD, "");
            model.addAttribute(AttributeNames.TOTAL_PAGES, total_page);
            model.addAttribute(AttributeNames.CURRENT_PAGE, 1);

            return ViewNames.BLOG;
        } else {
            return Mappings.REDIRECT_LOGIN;
        }
    }


    @GetMapping(value = Mappings.BLOG, params = {AttributeNames.KEYWORD, AttributeNames.CURRENT_PAGE})
    public String exploreBlogsbyPage(ModelMap model,
                               @RequestParam String KEYWORD,
                               @RequestParam int CURRENT_PAGE) {

        if (!isNull(model.getAttribute(AttributeNames.UID))) {
            List<Blog> blogs = null;

            if (KEYWORD.equals("")) {
                blogs = blogService.getBlogs();
            }else {
                blogs = blogService.getBlogsByKeyword(KEYWORD);
            }
            int total_page = (int) Math.ceil(blogs.size() / 4.0);
            int start = (CURRENT_PAGE - 1) * 4;
            int end = start + 4;
            if(end > blogs.size()) end = blogs.size();
            blogs = blogs.subList(start, end);

            model.addAttribute(AttributeNames.BLOGS, blogs);
            model.addAttribute(AttributeNames.KEYWORD, KEYWORD);
            model.addAttribute(AttributeNames.TOTAL_PAGES, total_page);
            model.addAttribute(AttributeNames.CURRENT_PAGE, CURRENT_PAGE);

            return ViewNames.BLOG;
        } else {
            return Mappings.REDIRECT_LOGIN;
        }
    }


    @GetMapping(value = Mappings.POST, params = {AttributeNames.BID})
    public String post(ModelMap model, @RequestParam int BID) {

        if (!isNull(model.getAttribute(AttributeNames.UID))) {
            Blog blog = blogService.getBlogById(BID);
            model.addAttribute(AttributeNames.BLOG, blog);

            return ViewNames.POST;
        } else {
            return Mappings.REDIRECT_LOGIN;
        }
    }


    @GetMapping(Mappings.CREATE)
    public String create(ModelMap model) {
        if (!isNull(model.getAttribute(AttributeNames.UID))) {
            return ViewNames.CREATE;
        } else {
            return Mappings.REDIRECT_LOGIN;
        }
    }


    @PostMapping(value = Mappings.HOME, params = {"email", "password"})
    public ModelAndView logToHome(ModelMap model,
                                  @RequestParam String email,
                                  @RequestParam String password) {

        User user = userService.getUserByEmail(email);

        log.info("Given: " + email + " " + password);

        if (!isNull(user) && user.getPassword().equals(password)) {
            model.addAttribute(AttributeNames.UID, user.getUid());

            log.info("Login successful!");

            return new ModelAndView(Mappings.REDIRECT_HOME);
        } else {
            model.addAttribute(AttributeNames.LOGIN_ERROR, userService.getLoginError());

            log.info(model.toString());

            return new ModelAndView(Mappings.REDIRECT_LOGIN, model);
        }
    }


    @PostMapping(value = Mappings.HOME, params = {"name", "email", "password", "confirm_password", "contact"})
    public ModelAndView signToHome(ModelMap model,
                                   @RequestParam String name,
                                   @RequestParam String email,
                                   @RequestParam String password,
                                   @RequestParam String confirm_password,
                                   @RequestParam String contact) {

        if (password.equals(confirm_password)) {
            User user = userService.getUserByEmail(email);
            if (isNull(user)) {
                user = new User(name, email, password, contact);
                userService.saveUser(user);

                model.addAttribute(AttributeNames.UID, user.getUid());

                log.info("given {} and pass {}", email, password);
                log.info("Sign-up successful!");

                return new ModelAndView(Mappings.REDIRECT_HOME);
            } else {
                model.addAttribute(AttributeNames.SIGNUP_ERROR, userService.getSignUpError());

                log.info(model.toString());

                return new ModelAndView(Mappings.REDIRECT_SIGNUP, model);
            }
        } else {
            model.addAttribute(AttributeNames.SIGNUP_ERROR, "Password does not match!");

            log.info(model.toString());

            return new ModelAndView(Mappings.REDIRECT_SIGNUP, model);
        }
    }


    @PostMapping(value = Mappings.CREATE, params = {"title", "keyword", "content"})
    public ModelAndView createBlog(ModelMap model,
                                   @RequestParam String title,
                                   @RequestParam String keyword,
                                   @RequestParam String content,
                                   @RequestParam("img")MultipartFile multipartFile) throws IOException {

        int uid = (int) model.getAttribute(AttributeNames.UID);

        String img_name = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));

        Blog blog = new Blog(title, keyword, content, img_name);
        blog.setDate(blog.getCurrentDateTime());

        User user = userService.getUserById(uid);
        user.getBlog().add(blog);

        blog.setUser(user);

        Blog savedBlog = blogService.saveBlog(blog);

        String uploadDir = "./src/main/resources/blog-images/" + savedBlog.getBid();

        Path uploadPath = Paths.get(uploadDir);

        if(!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = multipartFile.getInputStream()){
            Path filePath = uploadPath.resolve(img_name);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IOException("Could not save file: "+ img_name);
        }

        model.addAttribute(AttributeNames.CREATE_MESSAGE, "Blog Uploaded!");

        log.info(model.toString());

        return new ModelAndView(Mappings.REDIRECT_CREATE, model);
    }


    @PostMapping (value = Mappings.BLOG, params = {AttributeNames.KEYWORD})
    public String exploreBlogsByKeyword(ModelMap model,
                                        @RequestParam String KEYWORD) {

        if (!isNull(model.getAttribute(AttributeNames.UID))) {
            List<Blog> blogs = blogService.getBlogsByKeyword(KEYWORD);

            model.addAttribute(AttributeNames.BLOGS, blogs);
            model.addAttribute(AttributeNames.KEYWORD, KEYWORD);
            model.addAttribute(AttributeNames.CURRENT_PAGE, 1);

            return (Mappings.REDIRECT_BLOG + "?KEYWORD=" + KEYWORD + "&CURRENT_PAGE= "+ 1);
        } else {
            return Mappings.REDIRECT_LOGIN;
        }
    }


    @PostMapping(value = Mappings.COMMENT, params = {"BID", "userComment"})
    public String comment(ModelMap model,
                                    @RequestParam int BID,
                                    @RequestParam String userComment) {
        log.info("Comment was: " + userComment);
        int uid = (int) model.getAttribute(AttributeNames.UID);

        Comment comment = new Comment(userComment);
        comment.setDate(comment.getCurrentDateTime());

        User user = userService.getUserById(uid);
        user.getComment().add(comment);

        Blog blog = blogService.getBlogById(BID);
        blog.getComment().add(comment);

        comment.setBlog(blog);
        comment.setUser(user);

        commentService.saveComment(comment);

        return (Mappings.REDIRECT_POST + "?BID="+BID);
    }
}
