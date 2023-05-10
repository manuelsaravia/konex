package com.konex.project.main.general;


import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags="Controlador General")
@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping(path="api/general")
public class GeneralController {

    @GetMapping()
    public ResponseEntity<?> get(){
        return new ResponseEntity<>("hello", HttpStatus.OK);
    }
}
