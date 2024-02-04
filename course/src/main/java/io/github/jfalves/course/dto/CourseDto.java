package io.github.jfalves.course.dto;

import io.github.jfalves.course.enums.CourseLevel;
import io.github.jfalves.course.enums.CourseStatus;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseDto {

    @NotBlank
    private String name;
    @NotBlank
    private String description;
    private String imageUrl;
    @NotNull
    private CourseStatus courseStatus;
    @NotNull
    private UUID userInstructor;
    @NotNull
    private CourseLevel courseLevel;
}
