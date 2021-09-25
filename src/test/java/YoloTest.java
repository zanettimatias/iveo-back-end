import ar.com.mzanetti.iveo.dto.ObjectDetectionResult;
import ar.com.mzanetti.iveo.service.YoloNetServiceImpl;

import org.junit.Test;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.File;
import java.util.List;

import static org.opencv.highgui.HighGui.imshow;
import static org.opencv.highgui.HighGui.waitKey;
import static org.opencv.imgproc.Imgproc.LINE_8;
import static org.opencv.imgproc.Imgproc.rectangle;


public class YoloTest {

    @Test
    public void findObjects() throws Exception {
        nu.pattern.OpenCV.loadLocally();
        String path = "src/test/resources";
        File file = new File(path);
        String absolutePath = file.getAbsolutePath();


        Mat image = Imgcodecs.imread(absolutePath + File.separator + "bd" + File.separator + "sal.png");

/*        YoloNetServiceImpl yolo = new YoloNetServiceImpl(
                absolutePath + File.separator + "yolov3.cfg",
                absolutePath + File.separator + "yolov3.weights",
                absolutePath + File.separator +"coco.names",
                608, 608);*/
        YoloNetServiceImpl yolo = new YoloNetServiceImpl();
        yolo.setup();

        List<ObjectDetectionResult> results = yolo.predict(image);

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
