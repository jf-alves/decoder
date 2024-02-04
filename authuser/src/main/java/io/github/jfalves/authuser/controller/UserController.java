package io.github.jfalves.authuser.controller;

import com.fasterxml.jackson.annotation.JsonView;
import io.github.jfalves.authuser.dto.UserDto;
import io.github.jfalves.authuser.model.UserModel;
import io.github.jfalves.authuser.service.UserService;
import io.github.jfalves.authuser.specifications.SpecificationTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    private final UserService service;

//    @GetMapping
//    public ResponseEntity<Page<UserModel>> getAllUsers(SpecificationTemplate.UserSpec spec,
//                                                       @PageableDefault(page = 0, size = 10, sort = "userId", direction = Sort.Direction.ASC) Pageable pageable){
//        Page<UserModel> userModelPage = service.findAll(spec, pageable);
//        if(!userModelPage.isEmpty()){
//            for(UserModel user : userModelPage.toList()){
//                user.add(linkTo(methodOn(UserController.class).getOneUser(user.getUserId())).withSelfRel());
//            }
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(userModelPage);
//    }

    @GetMapping
    public ResponseEntity<Page<UserModel>> getAllUsers(SpecificationTemplate.UserSpec spec,
                                                       @PageableDefault(page = 0, size = 10, sort = "userId",
                                                               direction = Sort.Direction.ASC)
                                                       Pageable pageable,
                                                       @RequestParam(required = false)UUID courseId) {
        log.debug("GetAll UserId and courseId received {} ", courseId);
        Page<UserModel> userModelPage = null;
        if (courseId != null){
            userModelPage = service.findAll(SpecificationTemplate.userCourseId(courseId).and(spec), pageable);
        }else {
            userModelPage = service.findAll(spec, pageable);
        }
        if (!userModelPage.isEmpty()) {
            for (UserModel user : userModelPage.toList()) {
                user.add(linkTo(methodOn(UserController.class).getOneUser(user.getUserId())).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(userModelPage);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> getOneUser(@PathVariable(value = "userId") UUID userId) {
        Optional<UserModel> userModelOptional = service.findById(userId);
        if (userModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(userModelOptional.get());
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable(value = "userId") UUID userId) {

        log.debug("DELETE deleteUser UserId received {} ", userId);

        Optional<UserModel> userModelOptional = service.findById(userId);
        if (userModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        } else {
            service.delete(userModelOptional.get());
            log.debug("DELETE deleteUSer userId {} .", userId);
            log.info("User deleted successfully userId {} .", userId);
            return ResponseEntity.status(HttpStatus.OK).body("User deleted success");
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Object> updateUser(@PathVariable(value = "userId") UUID userId,
                                             @RequestBody @Validated(UserDto.UserView.UserPut.class)
                                             @JsonView(UserDto.UserView.UserPut.class) UserDto dto) {
        log.debug("Put updateUser UserDto received {} ", dto.toString());
        Optional<UserModel> userModelOptional = service.findById(userId);

        if (userModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found. ");
        } else {
            var userModel = userModelOptional.get();
            userModel.setFullName(dto.getFullName());
            userModel.setPhoneNumber(dto.getFullName());
            userModel.setCpf(dto.getCpf());
            userModel.setLastUpdate(LocalDateTime.now(ZoneId.of("UTC")));
            service.save(userModel);

            log.info("Put updateUser userId saved {} .", userModel.getUserId());
            log.info("User updated successfully userId {} .", userModel.getUserId());
            return ResponseEntity.status(HttpStatus.OK).body(userModel);
        }
    }

    @PutMapping("/{userId}/password")
    public ResponseEntity<Object> updatePassword(@PathVariable(value = "userId") UUID userId,
                                                 @RequestBody @Validated(UserDto.UserView.PasswordPut.class)
                                                 @JsonView(UserDto.UserView.PasswordPut.class) UserDto dto) {

        log.debug("Put updatePassword UserDto received {} ", dto.toString());
        Optional<UserModel> userModelOptional = service.findById(userId);

        if (userModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        if (!userModelOptional.get().getPassword().equals(dto.getOldPassword())) {
            log.warn("Error: Mismatched old password {}.", dto.getUserId());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Mismatched old password");
        } else {
            var userModel = userModelOptional.get();

            userModel.setPassword(dto.getPassword());
            userModel.setLastUpdate(LocalDateTime.now(ZoneId.of("UTC")));
            service.save(userModel);

            log.info("Put updatePassword userId saved {} .", userModel.getUserId());
            log.info("User updated successfully userId {} .", userModel.getUserId());
            return ResponseEntity.status(HttpStatus.OK).body("Password updated successfully");
        }
    }

    @PutMapping("/{userId}/image")
    public ResponseEntity<Object> updateImage(@PathVariable(value = "userId") UUID userId,
                                              @RequestBody @Validated(UserDto.UserView.ImagePut.class)
                                              @JsonView(UserDto.UserView.ImagePut.class) UserDto dto) {
        log.debug("Put updateImage UserDto received {} ", dto.toString());

        Optional<UserModel> userModelOptional = service.findById(userId);
        if (userModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found. ");
        } else {
            var userModel = userModelOptional.get();

            userModel.setImageUrl(dto.getImageUrl());
            userModel.setLastUpdate(LocalDateTime.now(ZoneId.of("UTC")));
            service.save(userModel);

            log.info("Put updateImage userId saved {} .", userModel.getUserId());
            log.info("User updated successfully userId {} .", userModel.getUserId());
            return ResponseEntity.status(HttpStatus.OK).body(userModel);
        }
    }
}
