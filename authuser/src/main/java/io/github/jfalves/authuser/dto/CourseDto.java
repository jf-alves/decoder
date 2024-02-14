package io.github.jfalves.authuser.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.jfalves.authuser.enums.CourseLevel;
import io.github.jfalves.authuser.enums.CourseStatus;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseDto {

    private UUID courseID;
    private String name;
    private String description;;
    private String imageUrl;
    private CourseStatus courseStatus;
    private UUID userInstructor;
    private CourseLevel courseLeve;

}
