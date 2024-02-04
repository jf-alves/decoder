package io.github.jfalves.course.service.impl;

import io.github.jfalves.course.model.CourseModel;
import io.github.jfalves.course.model.LessonModel;
import io.github.jfalves.course.model.ModuleModel;
import io.github.jfalves.course.repository.CourseRepository;
import io.github.jfalves.course.repository.LessonRepository;
import io.github.jfalves.course.repository.ModuleRepository;
import io.github.jfalves.course.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository repository;
    private final ModuleRepository moduleRepository;
    private final LessonRepository lessonRepository;

    @Override
    @Transactional
    public void delete(CourseModel courseModel) {
        List<ModuleModel> moduleModelList = moduleRepository.findAllModulesIntoCourse(courseModel.getCourseId());
        if (!moduleModelList.isEmpty()) {
            for (ModuleModel module : moduleModelList) {
                List<LessonModel> lessonModelList = lessonRepository.findAllLessonsIntoModule(module.getModuleId());
                if (!lessonModelList.isEmpty()) {
                    lessonRepository.deleteAll(lessonModelList);
                }
            }
            moduleRepository.deleteAll(moduleModelList);
        }
        repository.delete(courseModel);
    }

    @Override
    public CourseModel save(CourseModel courseModel) {
        return repository.save(courseModel);
    }

    @Override
    public Optional<CourseModel> findById(UUID courseId) {
        return repository.findById(courseId);
    }

    @Override
    public Page<CourseModel> findAll(Specification<CourseModel> spec, Pageable pageable) {
        return repository.findAll(spec, pageable);
    }

}
