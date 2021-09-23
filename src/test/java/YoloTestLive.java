import ar.com.mzanetti.iveo.dto.ObjectDetectionResult;
import ar.com.mzanetti.iveo.service.YoloNetService;
import com.github.sarxos.webcam.Webcam;
import org.junit.Test;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.List;

import static org.opencv.highgui.HighGui.imshow;
import static org.opencv.highgui.HighGui.waitKey;
import static org.opencv.imgproc.Imgproc.LINE_8;
import static org.opencv.imgproc.Imgproc.rectangle;

public class YoloTestLive {


    @Test
    public void showYoloLive(){
        nu.pattern.OpenCV.loadLocally();
        String path = "src/test/resources";
        File file = new File(path);
        String absolutePath = file.getAbsolutePath();


        VideoCapture videoCapture = new VideoCapture(0);
        Mat image = new Mat();


        while (true){
            videoCapture.read(image);
            YoloNetService yolo = new YoloNetService(
                    absolutePath + File.separator + "yolov3.cfg",
                    absolutePath + File.separator + "yolov3.weights",
                    absolutePath + File.separator +"coco.names",
                    608, 608);
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

            showResult(image);
        }
    }
    public static void showResult(Mat img) {
        Imgproc.resize(img, img, new Size(640, 480));
        MatOfByte matOfByte = new MatOfByte();
        byte[] byteArray = matOfByte.toArray();
        BufferedImage bufImage = null;
        try {
            InputStream in = new ByteArrayInputStream(byteArray);
            bufImage = ImageIO.read(in);
            JFrame frame = new JFrame();
            frame.getContentPane().add(new JLabel(new ImageIcon(bufImage)));
            frame.pack();
            frame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
