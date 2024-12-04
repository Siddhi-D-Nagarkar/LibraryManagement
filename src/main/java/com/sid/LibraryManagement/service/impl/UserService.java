package com.sid.LibraryManagement.service.impl;

import com.sid.LibraryManagement.entity.User;
import com.sid.LibraryManagement.enums.Operator;
import com.sid.LibraryManagement.enums.UserFilter;
import com.sid.LibraryManagement.enums.UserType;
import com.sid.LibraryManagement.dto.request.UserCreationRequest;
import com.sid.LibraryManagement.dto.response.UserCreationResponse;
import com.sid.LibraryManagement.repository.UserCacheRepository;
import com.sid.LibraryManagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Value("${student.authority}")
    private String studentAuthority;

    @Value("${admin.authority}")
    private String adminAuthority;

    @Autowired
    private UserCacheRepository userCacheRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User cacheUser = userCacheRepository.getUser(email);
        if(cacheUser != null) {
            return cacheUser;
        }

        User dbUser =  userRepository.findByEmail(email);
        if (dbUser == null) {
            throw new UsernameNotFoundException("No user found with username: " + email);
        }
        userCacheRepository.setUser(email, dbUser);
        return dbUser;
    }

    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserCreationResponse addStudent(UserCreationRequest request) {
        User user = request.toUser();
        user.setUserType(UserType.STUDENT);
        user.setPassword(passwordEncoder.encode(request.getUserPassword()));
        user.setAuthorities(studentAuthority);

        User userFromDb = userRepository.save(user);
        return UserCreationResponse.builder()
                .userName(userFromDb.getName())
                .userAddress(userFromDb.getAddress())
                .userPhone(userFromDb.getPhoneNo())
                .userEmail(userFromDb.getEmail())
                .build();
    }

    public List<User> filter(UserFilter filterBy, Operator operator, String value) {
        switch (filterBy){
            case NAME : {
                switch (operator){
                    case EQUALS :{
                        // TODO convert the User to UserFilterResponseType
                        return userRepository.findByName(value);
                    }
                    case LIKE:{
                        return userRepository.findByNameLike("%"+value+"%");
                    }
                }
            }
        }
        return new ArrayList<>();
    }

    public User checkIfUserIsValid( String userEmail) {
        return userRepository.findByEmail(userEmail);
    }

    public UserCreationResponse addAdmin(UserCreationRequest request) {
        User user = request.toUser();
        user.setUserType(UserType.ADMIN);
        user.setPassword(passwordEncoder.encode(request.getUserPassword()));
        user.setAuthorities(adminAuthority);
        User userFromDb = userRepository.save(user);
        return UserCreationResponse.builder()
                .userName(userFromDb.getName())
                .userAddress(userFromDb.getAddress())
                .userPhone(userFromDb.getPhoneNo())
                .userEmail(userFromDb.getEmail())
                .build();
    }
}
