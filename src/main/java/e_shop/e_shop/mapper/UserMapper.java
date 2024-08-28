// src/main/java/e_shop/e_shop/mapper/UserMapper.java
package e_shop.e_shop.mapper;

import e_shop.e_shop.dto.UserDto;
import e_shop.e_shop.entity.User;

public class UserMapper {

    // Convert User entity to UserDto
    public static UserDto toDto(User user) {
        if (user == null) {
            return null;
        }
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword()); // Note: Storing passwords in DTOs should be handled securely
        userDto.setRole(user.getRole());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setNotes(user.getNotes());
        return userDto;
    }

    // Convert UserDto to User entity
    public static User toEntity(UserDto userDto) {
        if (userDto == null) {
            return null;
        }
        User user = new User();
        user.setId(userDto.getId());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword()); // Note: Ensure passwords are securely handled
        user.setRole(userDto.getRole());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setNotes(userDto.getNotes());
        return user;
    }
}
