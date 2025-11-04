package com.lisoft.exagen.infrastructure.api;

import com.lisoft.exagen.application.dtos.ResponseWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.lisoft.exagen.domain.utils.Constants.API_VERSION;

@RestController
@RequestMapping(API_VERSION + "/public")
public class PublicRouter {

    @GetMapping("/")
    public ResponseEntity<ResponseWrapper<String>> index() {
        return new ResponseEntity<>(
                new ResponseWrapper<>(
                        true,
                        "Successful connection to exagen API",
                        "Index router"
                ),
                HttpStatus.OK
        );
    }
}
