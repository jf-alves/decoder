package io.github.jfalves.course.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LessonDto {

    @NotBlank
    private String title;
    private String description;
    @NotBlank
    private String videoUrl;
}
