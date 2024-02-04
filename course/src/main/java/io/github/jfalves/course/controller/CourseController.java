package io.github.jfalves.course.controller;

import io.github.jfalves.course.dto.CourseDto;
import io.github.jfalves.course.model.CourseModel;
import io.github.jfalves.course.service.CourseService;
import io.github.jfalves.course.specifications.SpecificationTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/courses")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CourseController {

    private final CourseService service;

    @PostMapping
    public ResponseEntity<Object> saveCourse(@RequestBody @Valid CourseDto dto) {
        var courseModel = new CourseModel();

        BeanUtils.copyProperties(dto, courseModel);
        courseModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        courseModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        service.save(courseModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(courseModel);
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<Object> deleteCourse(@PathVariable(value = "courseId") UUID courseId) {

        Optional<CourseModel> courseModelOptional = service.findById(courseId);
        if (!courseModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");
        }
        service.delete(courseModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Course deleted success");
    }

    @PutMapping("/{courseId}/update")
    public ResponseEntity<Object> updateCourse(@PathVariable(value = "courseId") UUID courseId,
                                               @RequestBody @Valid CourseDto dto) {
        Optional<CourseModel> courseModelOptional = service.findById(courseId);
        if (!courseModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found.");
        }

        var courseModel = new CourseModel();
        BeanUtils.copyProperties(dto, courseModel);
        courseModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));

        return ResponseEntity.status(HttpStatus.OK).body(service.save(courseModel));
    }

    @GetMapping
    public ResponseEntity<Page<CourseModel>> getAllCourse(SpecificationTemplate.CourseSpec spec,
                                                          @PageableDefault(page = 0,
                                                                  size = 10,
                                                                  sort = "courseId",
                                                                  direction = Sort.Direction.ASC) Pageable pageable,
                                                          @RequestParam(required = false) UUID userId) {
        if (userId != null) {
            return ResponseEntity.status(HttpStatus.OK).body(service.findAll(SpecificationTemplate.courseUserId(userId).and(spec), pageable));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(service.findAll(spec, pageable));
        }
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<Object> getOneCourse(@PathVariable(value = "courseId") UUID courseId) {
        Optional<CourseModel> courseModelOptional = service.findById(courseId);
        if (!courseModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(courseModelOptional.get());
    }

}
