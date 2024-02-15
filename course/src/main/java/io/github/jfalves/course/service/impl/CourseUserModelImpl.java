package io.github.jfalves.course.service.impl;

import io.github.jfalves.course.model.CourseModel;
import io.github.jfalves.course.model.CourseUserModel;
import io.github.jfalves.course.repository.CourseUserRepository;
import io.github.jfalves.course.service.CourseUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseUserModelImpl implements CourseUserService {

    private final CourseUserRepository repository;

    @Override
    public boolean existsByCourseAndUserId(CourseModel courseModel, UUID userId) {
        return repository.existsByCourseAndUserId(courseModel, userId);
    }

    @Override
    public CourseUserModel save(CourseUserModel courseUserModel) {
        return repository.save(courseUserModel);
    }
}
