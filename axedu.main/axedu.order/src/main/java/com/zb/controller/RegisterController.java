package com.zb.controller;

import com.zb.pojo.Register;
import com.zb.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {
    @Autowired
    private RegisterService registerService;
    @GetMapping("/getRegisterByToken/{token}")
    public Register getRegisterByToken(@PathVariable("token") String token){
        return registerService.getRegisterByToken(token);
    }
}
