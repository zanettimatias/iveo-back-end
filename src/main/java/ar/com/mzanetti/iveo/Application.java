package ar.com.mzanetti.iveo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class Application {
    public static void main(String[] args) {
        nu.pattern.OpenCV.loadLocally();
        SpringApplication.run(Application.class, args);
    }
}
