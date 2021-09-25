import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class OpenCvLibrary {

    @Bean
    void getLibrary (){
        nu.pattern.OpenCV.loadLocally();
    }
}
