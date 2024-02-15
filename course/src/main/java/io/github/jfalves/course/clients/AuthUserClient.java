package io.github.jfalves.course.clients;

import io.github.jfalves.course.dto.ResponsePageDto;
import io.github.jfalves.course.dto.UserDto;
import io.github.jfalves.course.service.UtilsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Log4j2
@Component
@RequiredArgsConstructor
public class AuthUserClient {

    private final RestTemplate template;
    private final UtilsService service;

    @Value("${api.url.authuser}")
    String REQUEST_URL_AUTHUSER;

    public Page<UserDto> getAllUsersByCourse(UUID courseId, Pageable pageable){
        List<UserDto> searchResult = null;
        ResponseEntity<ResponsePageDto<UserDto>> result = null;
        String url = service.createUrlGetAllUsersByCourse(courseId, pageable);

        log.debug("Request URL: {} ", url);
        log.info("Request URL: {} ", url);

        try{
            ParameterizedTypeReference<ResponsePageDto<UserDto>> responseType = new ParameterizedTypeReference<ResponsePageDto<UserDto>>()  {
            };
            result = template.exchange(url, HttpMethod.GET, null, responseType);
            searchResult = result.getBody().getContent();
        }catch (HttpStatusCodeException e){
            log.error("Error request /users courseId {} ", e);
        }
        log.info("Ending request /users courseId {} ", courseId);
        return result.getBody();
    }

    public ResponseEntity<UserDto>getOneUseById(UUID userId){
        String url = REQUEST_URL_AUTHUSER + "/user/" + userId;
        return template.exchange(url, HttpMethod.GET, null, UserDto.class);
    }
}
