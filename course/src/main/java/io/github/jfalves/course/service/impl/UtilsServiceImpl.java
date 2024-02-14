package io.github.jfalves.course.service.impl;

import io.github.jfalves.course.service.UtilsService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UtilsServiceImpl implements UtilsService {

    String REQUEST_URI = "http://localhost:8087";

    public String createUrlGetAllUsersByCourse(UUID courseId, Pageable pageable) {
       return REQUEST_URI + "/users?courseId=" + courseId + "&page=" + pageable.getPageNumber() + "&size="
                + pageable.getPageSize() + "&sort=" + pageable.getSort().toString().replaceAll(": ", ",");
    }


}
