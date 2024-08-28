package e_shop.e_shop.service.impl;

import e_shop.e_shop.dto.LoginDto;
import e_shop.e_shop.dto.UserDto;
import e_shop.e_shop.entity.User;
import e_shop.e_shop.mapper.UserMapper;
import e_shop.e_shop.repository.UserRepository;
import e_shop.e_shop.response.LoginMessage;
import e_shop.e_shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String addUser(UserDto userDto) {
        User user = new User(
                userDto.getId(),
                userDto.getUsername(),
                this.passwordEncoder.encode(userDto.getPassword()),
                userDto.getRole(),
                userDto.getFirstName(),
                userDto.getLastName(),
                userDto.getEmail(),
                userDto.getNotes()
        );
        userRepository.save(user);
        return user.getUsername();

    }

    @Override
    public LoginMessage loginUser(LoginDto loginDto) {
        // Retrieve the user by username
        User user = userRepository.findByUsername(loginDto.getUsername());

        // Check if user exists
        if (user != null) {
            // Check if the provided password matches the stored (encoded) password
            boolean isPwdRight = passwordEncoder.matches(loginDto.getPassword(), user.getPassword());

            if (isPwdRight) {
                // Password is correct
                return new LoginMessage("Login success", true, user.getRole(), user.getId());
            } else {
                // Password is incorrect
                return new LoginMessage("Login Failed: Incorrect password", false, null, null);
            }
        } else {
            // Username does not exist
            return new LoginMessage("Login Failed: Username does not exist", false, null, null);
        }
    }

    @Override
    public UserDto getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(UserMapper::toDto)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }
}
