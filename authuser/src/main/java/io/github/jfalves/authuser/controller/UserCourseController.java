package io.github.jfalves.authuser.controller;

import io.github.jfalves.authuser.clients.UserClient;
import io.github.jfalves.authuser.dto.CourseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/usercourses")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserCourseController {

    private final UserClient client;

    @GetMapping("/users/{userId}/courses")
    public ResponseEntity<Page<CourseDto>> getAllCoursesByUser(@PageableDefault(page = 0, size = 10, sort = "courseId",
                                                                                direction = Sort.Direction.ASC)Pageable pageable,
                                                               @PathVariable(value = "userId")UUID userId){

        return ResponseEntity.status(HttpStatus.OK).body(client.getAllCoursesByUser(userId, pageable));
    }


}
