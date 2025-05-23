package org.akhil.bg.service.impl;

import lombok.RequiredArgsConstructor;
import org.akhil.bg.model.UserEntity;
import org.akhil.bg.payload.UserDto;
import org.akhil.bg.repo.UserRepo;
import org.akhil.bg.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    @Override
    public UserDto saveUser(UserDto userDto) {
        Optional<UserEntity> userEntity=userRepo.findByClerkId(userDto.getClerkId());
        if(userEntity.isPresent()){
               UserEntity user=userEntity.get();
               user.setEmail(userDto.getEmail());
               user.setFirstName(userDto.getFirstName());
               user.setLastName(userDto.getLastName());
               user.setPhotoUrl(userDto.getPhotoUrl());
               if(userDto.getCredits()!=null){
                   user.setCredits(userDto.getCredits());
               }
             user= userRepo.save(user);
               return mapToDto(user);
        }
        UserEntity newUser=mapToEntity(userDto);
        userRepo.save(newUser);
        return mapToDto(newUser);
    }

    private UserDto mapToDto(UserEntity newUser) {
        return UserDto.builder()
                .clerkId(newUser.getClerkId())
                .firstName(newUser.getFirstName())
                .lastName(newUser.getLastName())
                .email(newUser.getEmail())
                .credits(newUser.getCredits())
                .photoUrl(newUser.getPhotoUrl())
                .build();
    }

    private UserEntity mapToEntity(UserDto userDto) {
        return UserEntity.builder()
                .clerkId(userDto.getClerkId())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .photoUrl(userDto.getPhotoUrl())
                .build();
    }
}
