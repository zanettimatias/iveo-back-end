package ar.com.mzanetti.iveo.controller;

import ar.com.mzanetti.iveo.business.CompareBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {

    @Autowired
    CompareBusiness compareBusiness;

    @RequestMapping("/test")
    public ResponseEntity<String> testController() throws Exception {
        compareBusiness.getBestCandidate(null);
        return ResponseEntity.ok("Bueno");
    }

    @RequestMapping("/add")
    public ResponseEntity<String> addImage() throws Exception {
        return ResponseEntity.ok("Bueno");
    }
}
