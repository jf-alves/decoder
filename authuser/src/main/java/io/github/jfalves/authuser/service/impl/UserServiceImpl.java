package io.github.jfalves.authuser.service.impl;

import io.github.jfalves.authuser.model.UserModel;
import io.github.jfalves.authuser.repository.UserModelRepository;
import io.github.jfalves.authuser.service.UserService;
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
public class UserServiceImpl implements UserService {

    private final UserModelRepository repository;

    @Override
    public List<UserModel> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<UserModel> findById(UUID userId) {
        return repository.findById(userId);
    }

    @Override
    public void delete(UserModel userModel) {
        repository.delete(userModel);
    }

    @Override
    public void save(UserModel userModel) {
        repository.save(userModel);
    }

    @Override
    public boolean existsByUsername(String username) {
        return repository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }


    @Override
    public Page<UserModel> findAll(Specification<UserModel> spec, Pageable pageable) {
        return repository.findAll(spec, pageable);
    }
}
