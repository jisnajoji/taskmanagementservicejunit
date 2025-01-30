package com.example.tms.service.user;

import com.example.tms.dao.UserRequest;
import com.example.tms.models.User;
import com.example.tms.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl {
    @Autowired
    private UserRepository userRepository;

    public User createUser(UserRequest request) throws BadRequestException {
//        System.out.println(findUserByUsername(request.getUsername()));
//        if (findUserByUsername(request.getUsername()) != null) {
//            throw new BadRequestException("username already exists" );
//        }
        System.out.println(request);
        if (request.getUsername() == null) {
            System.out.println("entered");
            throw new BadRequestException("Username cannot be null or empty");
        } if (request.getPassword() == null) {
            throw new BadRequestException("Password cannot be null or empty");
        } if (request.getEmail() == null) {
            throw new BadRequestException("Email cannot be null or empty");
        }
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setUsername(request.getUsername());
        return userRepository.save(user);
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

}
