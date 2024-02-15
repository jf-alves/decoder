package io.github.jfalves.course.controller;

import io.github.jfalves.course.clients.AuthUserClient;
import io.github.jfalves.course.dto.SubscriptionDto;
import io.github.jfalves.course.dto.UserDto;
import io.github.jfalves.course.enums.UserStatus;
import io.github.jfalves.course.model.CourseModel;
import io.github.jfalves.course.model.CourseUserModel;
import io.github.jfalves.course.service.CourseService;
import io.github.jfalves.course.service.CourseUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class CourseUserController {

    private final AuthUserClient client;
    private final CourseService service;
    private final CourseUserService courseUserService;

    @GetMapping("/courses/{courseId}/users")
    public ResponseEntity<Page<UserDto>> getAllUsersByCourse(@PageableDefault(page = 0, size = 10, sort = "userId", direction = Sort.Direction.ASC) Pageable pageable,
                                                             @PathVariable(value = "courseId") UUID courseId) {
        return ResponseEntity.status(HttpStatus.OK).body(client.getAllUsersByCourse(courseId, pageable));
    }

    @PostMapping("/courses/{courseId}/users/subscription")
    public ResponseEntity<Object> saveSubscriptionUserInCourse(@PathVariable(value = "courseId") UUID courseId,
                                                               @RequestBody @Valid SubscriptionDto dto) {
        ResponseEntity<UserDto> responseUser;
        Optional<CourseModel> courseModelOptional = service.findById(courseId);

        if (!courseModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found. ");
        }
        if (courseUserService.existsByCourseAndUserId(courseModelOptional.get(), dto.getUserId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: subscription already exists!");
        }
        //verificaçao para user
        try {
            responseUser = client.getOneUseById(dto.getUserId());
            if (responseUser.getBody().getUserStatus().equals(UserStatus.BLOCKED)){
                return ResponseEntity.status(HttpStatus.CONFLICT).body("User is blocked. ");
            }
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode().equals(HttpStatus.NOT_FOUND)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Use not found");
            }
        }
        CourseUserModel courseUserModel =  courseUserService.save(courseModelOptional.get().convertToCourseUserModel(dto.getUserId()));

        return ResponseEntity.status(HttpStatus.CREATED).body(courseUserModel);
    }
}
