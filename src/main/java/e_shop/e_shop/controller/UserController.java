package e_shop.e_shop.controller;

import e_shop.e_shop.dto.LoginDto;

import e_shop.e_shop.dto.UserDto;
import e_shop.e_shop.response.LoginMessage;
import e_shop.e_shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("api/user")

public class UserController {
 @Autowired
 private UserService userService;

    @PostMapping("/save")
    public String saveUser(@RequestBody UserDto userDto){
        String id = userService.addUser(userDto);
        return id;
    }

    @PostMapping("/login")
    public ResponseEntity<?>loginUser(@RequestBody LoginDto loginDto){
        LoginMessage loginMessage = userService.loginUser(loginDto);
        return ResponseEntity.ok(loginMessage);
    }

}
