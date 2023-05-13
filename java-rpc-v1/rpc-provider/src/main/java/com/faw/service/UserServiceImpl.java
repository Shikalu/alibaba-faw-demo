package com.faw.service;

import com.faw.annotation.RpcService;
import com.faw.api.UserService;
import com.faw.api.dto.UserDTO;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 鹿胜宝
 * @date 2023/05/06
 */
@Service
@RpcService
public class UserServiceImpl implements UserService {
    Map<Object, UserDTO> userDTOMap = new HashMap<>();

    @Override
    public UserDTO getById(int id) {
        if (userDTOMap.size() == 0) {
            UserDTO user1 = UserDTO.builder().id(1).name("张三").build();
            UserDTO user2 = UserDTO.builder().id(2).name("李四").build();
            userDTOMap.put(user1.getId(),user1);
            userDTOMap.put(user2.getId(),user2);
        }
        return userDTOMap.get(id);
    }
}
