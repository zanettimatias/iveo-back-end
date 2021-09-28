import ar.com.mzanetti.iveo.Application;
import ar.com.mzanetti.iveo.dto.ObjectDetectionResult;
import ar.com.mzanetti.iveo.service.YoloNetService;
import ar.com.mzanetti.iveo.service.YoloNetServiceImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.List;

import static org.opencv.highgui.HighGui.imshow;
import static org.opencv.highgui.HighGui.waitKey;
import static org.opencv.imgproc.Imgproc.LINE_8;
import static org.opencv.imgproc.Imgproc.rectangle;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {OpenCvLibrary.class, Application.class})
public class YoloTest {

    @Autowired
    YoloNetService yoloNetService;

    @Test
    public void findObjects() throws Exception {
        nu.pattern.OpenCV.loadLocally();
        String path = "src/test/resources";
        File file = new File(path);
        String absolutePath = file.getAbsolutePath();
        System.setProperty("java.awt.headless", "false");


        Mat image = Imgcodecs.imread(absolutePath + File.separator + "bd" + File.separator + "8.png");

        List<ObjectDetectionResult> results = yoloNetService.predict(image);

        System.out.printf("Detected %d objects:\n", results.size());
        for (ObjectDetectionResult result : results) {
            System.out.printf("\t%s - %.2f%%\n", result.className, result.confidence * 100f);

            // annotate on image
            rectangle(image,
                    new Point(result.x, result.y),
                    new Point(result.x + result.width, result.y + result.height),
                    new Scalar(0,0,1), 2, LINE_8, 0);
        }

        imshow("YOLO", image);
        waitKey();
    }
}
