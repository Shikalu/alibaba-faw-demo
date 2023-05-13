package com.faw.api;

import com.faw.api.dto.UserDTO;

/**
 * @author 鹿胜宝
 * @date 2023/05/06
 */
public interface UserService {
    UserDTO getById(int id);
}
