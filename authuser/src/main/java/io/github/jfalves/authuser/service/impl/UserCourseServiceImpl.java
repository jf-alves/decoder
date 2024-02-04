package io.github.jfalves.authuser.service.impl;

import io.github.jfalves.authuser.repository.UserCourseRepository;
import io.github.jfalves.authuser.service.UserCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCourseServiceImpl implements UserCourseService {

    private final UserCourseRepository repository;
}
