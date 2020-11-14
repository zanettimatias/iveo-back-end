package ar.com.mzanetti.iveo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {

    @RequestMapping("/test")
    public ResponseEntity<String> testController(){
        return ResponseEntity.ok("Bueno");
    }

}
