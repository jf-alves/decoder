package io.github.jfalves.course.service.impl;

import io.github.jfalves.course.model.LessonModel;
import io.github.jfalves.course.model.ModuleModel;
import io.github.jfalves.course.repository.LessonRepository;
import io.github.jfalves.course.repository.ModuleRepository;
import io.github.jfalves.course.service.ModuleService;
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
public class ModuleServiceImpl implements ModuleService {

    private final ModuleRepository repository;
    private final LessonRepository lessonRepository;

    @Override
    @Transactional
    public void delete(ModuleModel moduleModel) {
        List<LessonModel> lessonModelList = lessonRepository.findAllLessonsIntoModule(moduleModel.getModuleId());
        if(!lessonModelList.isEmpty()){
            lessonRepository.deleteAll(lessonModelList);
        }
        repository.delete(moduleModel);
    }

    @Override
    public ModuleModel save(ModuleModel moduleModel) {
        return repository.save(moduleModel);
    }

    @Override
    public Optional<ModuleModel> findModuleIntoCourse(UUID courseId, UUID moduleId) {
        return repository.findModuleIntoCourse(courseId, moduleId);
    }

    @Override
    public List<ModuleModel> findAllByCourse(UUID courseId) {
        return repository.findAllModulesIntoCourse(courseId);
    }

    @Override
    public Optional<ModuleModel> findById(UUID moduleId) {
        return repository.findById(moduleId);
    }

    @Override
    public Page<ModuleModel> findAllByCourse(Specification<ModuleModel> spec, Pageable pageable) {
        return repository.findAll(spec, pageable);
    }
}
