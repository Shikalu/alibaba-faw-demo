package com.faw;

import com.faw.api.UserService;
import com.faw.api.dto.UserDTO;
import com.faw.proxy.RpcClientProxy;


public class ClientTest{
    public static void main(String[] args) {
        UserService userService = (UserService)RpcClientProxy.create(UserService.class);
        UserDTO userDTO = userService.getById(1);
        System.out.println("测试RPC调用：" + userDTO);
    }
}
