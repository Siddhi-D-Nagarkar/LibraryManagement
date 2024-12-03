package com.sid.LibraryManagement.controller;

import com.sid.LibraryManagement.entity.User;
import com.sid.LibraryManagement.dto.request.UserCreationRequest;
import com.sid.LibraryManagement.dto.response.UserCreationResponse;
import com.sid.LibraryManagement.enums.Operator;
import com.sid.LibraryManagement.enums.UserFilter;
import com.sid.LibraryManagement.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/addStudent")
    public UserCreationResponse addStudent(@RequestBody @Validated UserCreationRequest request){
        return userService.addStudent(request);
    }

    @GetMapping("/filter")
    public List<User> filterUser(@RequestParam("filterBy") String filterBy,
                                    @RequestParam("operator") Operator operator,
                                    @RequestParam("value") String value
    ){
        return userService.filter(UserFilter.valueOf(filterBy),operator,value);

    }

    @PostMapping("/addAdmin")
    public UserCreationResponse addAdmin(@RequestBody @Validated UserCreationRequest request){
        return userService.addAdmin(request);
    }


}

//user can be created (CREATE)
// updated (UPDATE)
// search (READ)
// delete (DELETE)
