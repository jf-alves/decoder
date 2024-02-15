package io.github.jfalves.authuser.service.impl;

import io.github.jfalves.authuser.service.UtilsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UtilsServiceImpl implements UtilsService {

    @Value("${api.url.course}")
    String REQUEST_URL_COURSE;

    public String createUrlGetAllCoursesByUser(UUID userId, Pageable pageable) {
       return "/courses?userId=" + userId + "&page=" + pageable.getPageNumber() + "&size="
                + pageable.getPageSize() + "&sort=" + pageable.getSort().toString().replaceAll(": ", ",");
    }
}
