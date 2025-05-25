package org.akhil.bg.controller;

import lombok.RequiredArgsConstructor;
import org.akhil.bg.payload.UserDto;
import org.akhil.bg.response.RemoveBgResponse;
import org.akhil.bg.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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

    @GetMapping("/credits")
    public ResponseEntity<RemoveBgResponse> getUserCredits(Authentication authentication) {
        try{
            if(authentication.getName().isEmpty() || authentication.getName()==null){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                        RemoveBgResponse.builder()
                                .statusCode(HttpStatus.FORBIDDEN)
                                .success(false)
                                .data("User does not have permission to access this resource")
                                .build()
                );
            }
            String clerkId=authentication.getName();
            UserDto existingUserDto=userService.getUserByClerkId(clerkId);
            Map<String,Integer> map=new HashMap<>();
            map.put("credits",existingUserDto.getCredits());
            return ResponseEntity.ok(RemoveBgResponse.builder()
                            .success(true)
                            .data(map)
                            .statusCode(HttpStatus.OK)
                    .build());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    RemoveBgResponse.builder()
                            .statusCode(HttpStatus.INTERNAL_SERVER_ERROR)
                            .success(false)
                            .data("Something went wrong")
                            .build()
            );
        }
    }
}
