package com.heavenscode.rac.web.rest;

import com.heavenscode.rac.repository.UserRepository;
import com.heavenscode.rac.service.UserService;
import com.heavenscode.rac.service.dto.UserDTO;
import java.util.*;
import java.util.Collections;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.PaginationUtil;

@RestController
@RequestMapping("/api")
public class PublicUserResource {

    private static final List<String> ALLOWED_ORDERED_PROPERTIES = Collections.unmodifiableList(
        Arrays.asList("id", "login", "firstName", "lastName", "email", "activated", "langKey")
    );

    private final Logger log = LoggerFactory.getLogger(PublicUserResource.class);

    private final UserService userService;
    private final UserRepository userRepository;

    public PublicUserResource(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    /**
     * {@code GET /users} : get all users with only public information - calling this method is allowed for anyone.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body all users.
     */
    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllPublicUsers(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get all public User names");
        if (!onlyContainsAllowedProperties(pageable)) {
            return ResponseEntity.badRequest().build();
        }

        final Page<UserDTO> page = userService.getAllPublicUsers(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * {@code GET /users/usernames} : get Employee UserName values for the requested Employee IDs.
     *
     * @param ids Employee IDs to resolve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the matching users.
     */
    @GetMapping("/users/usernames")
    public ResponseEntity<List<UserDTO>> getUserNamesByIds(@RequestParam("ids") List<Long> ids) {
        log.debug("REST request to get Employee UserName values for ids : {}", ids);

        List<Long> distinctIds = ids.stream().filter(id -> id != null && id > 0).distinct().collect(Collectors.toList());
        if (distinctIds.isEmpty()) {
            return ResponseEntity.ok(List.of());
        }

        List<UserDTO> users = userRepository
            .findUserNamesByIdIn(distinctIds)
            .stream()
            .map(userName -> {
                UserDTO dto = new UserDTO();
                dto.setId(userName.getId());
                dto.setLogin(userName.getLogin());
                return dto;
            })
            .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    private boolean onlyContainsAllowedProperties(Pageable pageable) {
        return pageable.getSort().stream().map(Sort.Order::getProperty).allMatch(ALLOWED_ORDERED_PROPERTIES::contains);
    }
}
