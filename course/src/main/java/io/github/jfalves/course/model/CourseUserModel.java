package io.github.jfalves.course.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_COURSES_USERS")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseUserModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false)
    private UUID userId;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private CourseModel course;
}
