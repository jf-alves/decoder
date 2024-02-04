package io.github.jfalves.course.controller;

import io.github.jfalves.course.dto.ModuleDto;
import io.github.jfalves.course.model.CourseModel;
import io.github.jfalves.course.model.ModuleModel;
import io.github.jfalves.course.service.CourseService;
import io.github.jfalves.course.service.ModuleService;
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
@RequestMapping("/modules")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ModuleController {

    private final ModuleService moduleService;
    private final CourseService courseService;


    @PostMapping("/courses/{courseId}/modules/save")
    public ResponseEntity<Object> saveModule(@PathVariable(value = "courseId") UUID courseId,
                                             @RequestBody @Valid ModuleDto dto) {

        Optional<CourseModel> courseModelOptional = courseService.findById(courseId);
        if (!courseModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");
        }

        var moduleModel = new ModuleModel();
        BeanUtils.copyProperties(dto, moduleModel);
        moduleModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        moduleModel.setCourse(courseModelOptional.get());
        return ResponseEntity.status(HttpStatus.CREATED).body(moduleService.save(moduleModel));
    }

    @DeleteMapping("/courses/{courseId}/modules/{moduleId}/delete")
    public ResponseEntity<Object> deleteCourse(@PathVariable(value= "courseId") UUID courseId,
                                               @PathVariable(value = "moduleId")UUID moduleId){

        Optional<ModuleModel> moduleModelOptional = moduleService.findModuleIntoCourse(courseId, moduleId);
        if(!moduleModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("<Module not found for this course.");
        }
        moduleService.delete(moduleModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Module deleted success");
    }

    @PutMapping("/courses/{courseId}/modules/{moduleId}/update")
    public ResponseEntity<Object> updateModule(@PathVariable(value = "courseId")UUID courseId,
                                               @PathVariable(value = "moduleId")UUID moduleId,
                                               @RequestBody @Valid ModuleDto dto){
        Optional<ModuleModel> moduleModelOptional = moduleService.findModuleIntoCourse(courseId, moduleId);
        if(!moduleModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("<Module not found for this course.");
        }

        var moduleModel = new ModuleModel();
        BeanUtils.copyProperties(dto, moduleModel);

        return ResponseEntity.status(HttpStatus.OK).body(moduleService.save(moduleModel));
    }

    @GetMapping("/courses/{courseId}/modules/all")
    public ResponseEntity<Page<ModuleModel>> getAllModule(@PathVariable(value = "courseId")UUID courseId,
                                                          SpecificationTemplate.ModuleSpec spec,
                                                          @PageableDefault(page = 0,
                                                                  size = 10,
                                                                  sort = "moduleId",
                                                                  direction = Sort.Direction.ASC)
                                                          Pageable pageable
                                                          ){
        return ResponseEntity.status(HttpStatus.OK).body(moduleService.findAllByCourse(SpecificationTemplate.moduleCourseId(courseId).and(spec), pageable));
    }

    @GetMapping("/courses/{courseId}/modules/one")
    public ResponseEntity<Object> getOneModule(@PathVariable(value = "courseId")UUID courseId,
                                               @PathVariable(value = "moduleId")UUID moduleId){
        Optional<ModuleModel> moduleModelOptional = moduleService.findModuleIntoCourse(courseId, moduleId);
        if(!moduleModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("<Module not found for this course.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(moduleModelOptional.get());
    }
}
