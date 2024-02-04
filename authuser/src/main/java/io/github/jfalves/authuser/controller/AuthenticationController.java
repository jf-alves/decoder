package io.github.jfalves.authuser.controller;


import com.fasterxml.jackson.annotation.JsonView;
import io.github.jfalves.authuser.dto.UserDto;
import io.github.jfalves.authuser.enums.UserStatus;
import io.github.jfalves.authuser.enums.UserType;
import io.github.jfalves.authuser.model.UserModel;
import io.github.jfalves.authuser.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;


@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthenticationController {

    private final UserService service;

    @PostMapping("/signup")
    public ResponseEntity<Object> registerUser(@RequestBody @Validated(UserDto.UserView.RegistrationPost.class)
                                               @JsonView(UserDto.UserView.RegistrationPost.class) UserDto dto) {

        log.debug("Post registerUser UserDto received {} ", dto.toString());

        if (service.existsByUsername(dto.getUsername())) {
            log.warn("Username {} is already .", dto.getUsername());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Username is already taken");
        }
        if (service.existsByEmail(dto.getEmail())) {
            log.warn("Email {} is already .", dto.getEmail());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Email is already taken");
        }
        var userModel = new UserModel();
        BeanUtils.copyProperties(dto, userModel);
        userModel.setUserStatus(UserStatus.ACTIVE);
        userModel.setUserType(UserType.STUDENT);
        userModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        userModel.setLastUpdate(LocalDateTime.now(ZoneId.of("UTC")));
        service.save(userModel);

        log.info("Post registerUser userId saved {} .", userModel.getUserId());
        log.info("User saved successfully userId {} .", userModel.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(userModel);
    }

//    @PostMapping("/login")
//    public ResponseEntity<Object> login(@RequestBody UserDto dto) {
//        Optional<UserModel> userModelOptional = service.findById(userId)
//    }

    @GetMapping("/")
    public String index() {
        log.trace("TRACE"); //Marior numero de detalhes;
        log.debug("DEBUG"); //Utilizado em ambiente de Dev ou STG, nunca utilizado em PRD
        log.info("INFO"); //Utilizado norrmalmente em PRD;
        log.warn("WARN"); // Utilizado para alertas;
        log.error("ERROR"); //Utilizado em erros de processo, comum em try catch;

        //Apenas para forçar cair no log de error
//        try{
//            throw new Exception("Exception message");
//        }catch (Exception e){
//            log.error(" ------------- ERROR ---------------- ", e);
//        }
        return "Logging Spring Boot";
    }
}
