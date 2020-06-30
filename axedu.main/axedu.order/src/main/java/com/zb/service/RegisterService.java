package com.zb.service;

import com.zb.pojo.Register;

import java.util.List;
import java.util.Map;

public interface RegisterService {

    public Register getRegisterByToken(String token);
}
