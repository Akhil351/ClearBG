package org.akhil.bg.controller;

import lombok.RequiredArgsConstructor;
import org.akhil.bg.payload.UserDto;
import org.akhil.bg.response.RemoveBgResponse;
import org.akhil.bg.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<RemoveBgResponse> createOrUpdateUser(@RequestBody UserDto userDto, Authentication authentication) {
        try{
            if(!authentication.getName().equals(userDto.getClerkId())){
               RemoveBgResponse removeBgResponse = RemoveBgResponse.builder()
                       .success(false)
                       .data("User does not have permission to access this resource")
                       .statusCode(HttpStatus.FORBIDDEN)
                       .build();
               return ResponseEntity.status(HttpStatus.FORBIDDEN).body(removeBgResponse);
            }
            userDto=userService.saveUser(userDto);
            RemoveBgResponse removeBgResponse=RemoveBgResponse.builder()
                    .success(true)
                    .data(userDto)
                    .statusCode(HttpStatus.OK)
                    .build();
            return ResponseEntity.ok(removeBgResponse);
        }
        catch(Exception e){
            RemoveBgResponse removeBgResponse=RemoveBgResponse.builder()
                    .success(false)
                    .data(e.getMessage())
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(removeBgResponse);
        }
    }
}
