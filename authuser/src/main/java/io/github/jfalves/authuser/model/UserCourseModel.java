package io.github.jfalves.authuser.model;


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
@Table(name = "TB_USERS_COURSES")
public class UserCourseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false)
    private UUID courseId;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private UserModel user;
}
