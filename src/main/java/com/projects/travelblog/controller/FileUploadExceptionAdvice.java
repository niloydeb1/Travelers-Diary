package com.projects.travelblog.controller;

import com.projects.travelblog.util.AttributeNames;
import com.projects.travelblog.util.Mappings;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class FileUploadExceptionAdvice {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ModelAndView handleMaxSizeException(
            MaxUploadSizeExceededException exc,
            HttpServletRequest request,
            HttpServletResponse response) {

        ModelAndView modelAndView = new ModelAndView(Mappings.REDIRECT_CREATE);
        modelAndView.getModel().put(AttributeNames.CREATE_MESSAGE, "Image size must be less than "
                + AttributeNames.MAX_IMG_SIZE + "!");
        return modelAndView;
    }
}
