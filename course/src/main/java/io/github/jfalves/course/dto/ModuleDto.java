package io.github.jfalves.course.dto;


import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModuleDto {

    @NotBlank
    private String title;
    @NotBlank
    private String description;
}
