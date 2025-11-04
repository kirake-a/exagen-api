package com.lisoft.exagen.infrastructure.api;

import com.lisoft.exagen.application.dtos.ResponseWrapper;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/secure")
@SecurityRequirement(name = "bearerAuth")
public class SecureRouter {

    @GetMapping("/")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseWrapper<String>> index() {
        return new ResponseEntity<>(
                new ResponseWrapper<>(
                        true,
                        "Hi, welcome to Exagen API",
                        "I am alive"
                ),
                HttpStatus.OK
        );
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ResponseWrapper<String>> userRoleAccess() {
        return new ResponseEntity<>(
                new ResponseWrapper<>(
                        true,
                        "Successful access to route access by users",
                        "Hello normal user"
                ),
                HttpStatus.OK
        );
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseWrapper<String>> adminRoleAccess() {
        return new ResponseEntity<>(
                new ResponseWrapper<>(
                        true,
                        "Successful access to route access by admins",
                        "Hello admin user"
                ),
                HttpStatus.OK
        );
    }
}
