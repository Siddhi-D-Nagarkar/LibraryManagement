package com.sid.LibraryManagement.service.impl;

import com.sid.LibraryManagement.entity.User;
import com.sid.LibraryManagement.enums.Operator;
import com.sid.LibraryManagement.enums.UserFilter;
import com.sid.LibraryManagement.enums.UserType;
import com.sid.LibraryManagement.dto.request.UserCreationRequest;
import com.sid.LibraryManagement.dto.response.UserCreationResponse;
import com.sid.LibraryManagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public UserCreationResponse addStudent(UserCreationRequest request) {
        User user = request.toUser();
        user.setUserType(UserType.STUDENT);
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
}
