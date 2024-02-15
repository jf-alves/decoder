package io.github.jfalves.course.service;

import io.github.jfalves.course.model.CourseModel;
import io.github.jfalves.course.model.CourseUserModel;

import java.util.UUID;

public interface CourseUserService {
    boolean existsByCourseAndUserId(CourseModel courseModel, UUID userId);

    CourseUserModel save(CourseUserModel courseUserModel);
}
