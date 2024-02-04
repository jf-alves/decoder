package io.github.jfalves.authuser.repository;

import io.github.jfalves.authuser.model.UserCourseModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserCourseRepository extends JpaRepository<UserCourseModel, UUID> {
}
