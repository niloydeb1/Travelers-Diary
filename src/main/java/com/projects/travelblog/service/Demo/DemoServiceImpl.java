package com.projects.travelblog.service.Demo;

import org.springframework.stereotype.Service;

@Service
public class DemoServiceImpl implements DemoService {
    @Override
    public String getMessage() {
        return "Hello From The Core Module";
    }

    @Override
    public String getERROR() {
        return "Wrong email or password!";
    }
}
