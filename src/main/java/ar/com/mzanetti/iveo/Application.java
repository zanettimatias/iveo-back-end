package ar.com.mzanetti.iveo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        nu.pattern.OpenCV.loadLocally();
        SpringApplication.run(Application.class, args);
    }
}
