package io.github.jfalves.course.service.impl;

import io.github.jfalves.course.service.UtilsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UtilsServiceImpl implements UtilsService {

    @Value("${api.url.authuser}")
    String REQUEST_URL_AUTHUSER;

    public String createUrlGetAllUsersByCourse(UUID courseId, Pageable pageable) {
       return "/users?courseId=" + courseId + "&page=" + pageable.getPageNumber() + "&size="
                + pageable.getPageSize() + "&sort=" + pageable.getSort().toString().replaceAll(": ", ",");
    }


}
