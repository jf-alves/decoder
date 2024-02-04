package io.github.jfalves.course.controller;

import io.github.jfalves.course.dto.LessonDto;
import io.github.jfalves.course.model.LessonModel;
import io.github.jfalves.course.model.ModuleModel;
import io.github.jfalves.course.service.LessonService;
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
@RequestMapping("/lessons")
@CrossOrigin(origins = "*", maxAge = 3600)
public class LessonController {

    private final LessonService lessonService;
    private final ModuleService moduleService;


    @PostMapping("/modules/{moduleId}/lesson/save")
    public ResponseEntity<Object> saveLesson(@PathVariable(value = "moduleId") UUID moduleId,
                                             @RequestBody @Valid LessonDto dto) {

        Optional<ModuleModel> moduleModelOptional = moduleService.findById(moduleId);
        if (!moduleModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Module not found");
        }

        var lessonModel = new LessonModel();
        BeanUtils.copyProperties(dto, lessonModel);
        lessonModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        lessonModel.setModule(moduleModelOptional.get());

        return ResponseEntity.status(HttpStatus.CREATED).body(lessonService.save(lessonModel));
    }

    @DeleteMapping("/modules/{moduleId}/lesson/{lessonId}/delete")
    public ResponseEntity<Object> deleteLesson(@PathVariable(value = "moduleId") UUID moduleId,
                                               @PathVariable(value = "lessonId") UUID lessonId){

        Optional<LessonModel> lessonModelOptional = lessonService.findLessonIntoModule(moduleId, lessonId);
        if(!lessonModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lesson not found for this module.");
        }
        lessonService.delete(lessonModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Lesson deleted success");
    }

    @GetMapping("/modules/{moduleId}/lessons/all")
    public ResponseEntity<Page<LessonModel>> getAllLessons(@PathVariable(value = "moduleId")UUID moduleId,
                                                           SpecificationTemplate.LessonSpec spec,
                                                           @PageableDefault(page = 0,
                                                                   size = 10,
                                                                   sort = "lessonId",
                                                                   direction = Sort.Direction.ASC)
                                                           Pageable pageable
                                                          ){
        return ResponseEntity.status(HttpStatus.OK).body(lessonService.findAllByModule(SpecificationTemplate.lessonModuleId(moduleId).and(spec),pageable));
    }

    @GetMapping("/modules/{moduleId}/lesson/{lessonId}/one")
    public ResponseEntity<Object> getOneLesson(@PathVariable(value = "moduleId") UUID moduleId,
                                               @PathVariable(value = "lessonId") UUID lessonId){
        Optional<LessonModel> lessonModelOptional = lessonService.findLessonIntoModule(moduleId, lessonId);
        if(!lessonModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lesson not found for this module");
        }
        return ResponseEntity.status(HttpStatus.OK).body(lessonModelOptional.get());
    }
}
