package com.cdcn.apartmentonlinemarket.users.controller;

import com.cdcn.apartmentonlinemarket.users.domain.dto.UserDto;
import com.cdcn.apartmentonlinemarket.users.domain.entity.Users;
import com.cdcn.apartmentonlinemarket.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findById(id));
    }

    @PutMapping("{userid}")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<?> editUser(@PathVariable("userid") String id, @Valid @RequestBody UserDto user) {
        return ResponseEntity.ok(userService.updateUser(id, user));
    }

    @DeleteMapping("{userid}")
    public ResponseEntity<?> delete(@PathVariable("userid") String id) {
        userService.deleteById(id);
        return ResponseEntity.status(200).body("Delete user id " + id + " successfully." );
    }
}
