package e_shop.e_shop.service;

import e_shop.e_shop.dto.LoginDto;
import e_shop.e_shop.dto.UserDto;
import e_shop.e_shop.response.LoginMessage;

public interface UserService {

    String addUser(UserDto userDto);

    LoginMessage loginUser(LoginDto loginDto);

    UserDto getUserById(Long id);
}
