package org.akhil.bg.controller;

import lombok.RequiredArgsConstructor;
import org.akhil.bg.payload.UserDto;
import org.akhil.bg.response.RemoveBgResponse;
import org.akhil.bg.service.RemoveBackGroundService;
import org.akhil.bg.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController {
    private final RemoveBackGroundService  removeBackGroundService;
    private final UserService userService;

    @PostMapping("/remove-background")
    public ResponseEntity<?> removeBackground(@RequestParam("file") MultipartFile file, Authentication authentication) {
        Map<String,Object> responseMap=new HashMap<>();
        try{
            if(authentication.getName().isEmpty() || authentication.getPrincipal() == null){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                        RemoveBgResponse.builder()
                                .success(false)
                                .statusCode(HttpStatus.FORBIDDEN)
                                .data("User does not have permission to access this resource")
                                .build()
                );
            }
            UserDto userDto=userService.getUserByClerkId(authentication.getName());
            if(userDto.getCredits()==0){
                responseMap.put("message","No credit balance");
                responseMap.put("creditBalance",userDto.getCredits());
                return ResponseEntity.ok(
                        RemoveBgResponse.builder()
                                .success(false)
                                .statusCode(HttpStatus.OK)
                                .data(responseMap)
                                .build()
                );
            }

           byte[] imageBytes= removeBackGroundService.removeBackground(file);
            String base64Image= Base64.getEncoder().encodeToString(imageBytes);
            userDto.setCredits(userDto.getCredits()-1);
            userService.saveUser(userDto);

            return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body(base64Image);

        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    RemoveBgResponse.builder()
                            .success(false)
                            .statusCode(HttpStatus.INTERNAL_SERVER_ERROR)
                            .data("Something went wrong")
                            .build()
            );

        }

    }
}
