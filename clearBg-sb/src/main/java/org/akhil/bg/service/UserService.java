package org.akhil.bg.service;

import org.akhil.bg.payload.UserDto;

public interface UserService {
    UserDto saveUser(UserDto userDto);

    UserDto getUserByClerkId(String clerkId);

    void deleteUserByClerkId(String clerkId);
}
