package io.github.jfalves.course.service.impl;

import io.github.jfalves.course.model.LessonModel;
import io.github.jfalves.course.repository.LessonRepository;
import io.github.jfalves.course.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository repository;

    @Override
    public LessonModel save(LessonModel lessonModel) {
        return repository.save(lessonModel);
    }

    @Override
    public Optional<LessonModel> findLessonIntoModule(UUID moduleId, UUID lessonId) {
        return repository.findLessonIntoModule(moduleId ,lessonId);
    }

    @Override
    public void delete(LessonModel lessonModel) {
        repository.delete(lessonModel);
    }

    @Override
    public List<LessonModel> findAllByModule(UUID moduleId) {
        return repository.findAllLessonsIntoModule(moduleId);
    }

    @Override
    public Page<LessonModel> findAllByModule(Specification<LessonModel> spec, Pageable pageable) {
        return repository.findAll(spec, pageable);
    }
}
