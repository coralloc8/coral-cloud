package com.example.spring.web.auth.web;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring.web.core.response.Result;
import com.example.spring.web.core.response.Results;

/**
 * @author huss
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @GetMapping
    public Result getCurrentUser(Principal principal) {

        String username = principal.getName();
        return new Results().success(principal);
    }

}
