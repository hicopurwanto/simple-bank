package nobicode.restful.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import nobicode.restful.dto.UserResponse;
import nobicode.restful.entity.User;
import nobicode.restful.dto.RegisterUserRequest;
import nobicode.restful.repository.UserRepository;
import nobicode.restful.security.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ValidationService validationService;

    @Transactional
    public void register(RegisterUserRequest request) {
        validationService.validate(request);

        if (userRepository.existsById(request.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "username sudah terdaftar");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPasswords(BCrypt.hashpw(request.getPasswords(), BCrypt.gensalt()));
        user.setName(request.getName());

        userRepository.save(user);
    }

    public UserResponse get(User user) {
        return UserResponse.builder()
                .username(user.getUsername())
                .name(user.getName())
                .build();
    }
}
