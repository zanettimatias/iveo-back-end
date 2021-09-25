import ar.com.mzanetti.iveo.dto.ObjectDetectionResult;
import ar.com.mzanetti.iveo.service.YoloNetServiceImpl;
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

import static org.opencv.highgui.HighGui.waitKey;
import static org.opencv.imgproc.Imgproc.LINE_8;
import static org.opencv.imgproc.Imgproc.rectangle;

public class YoloTestLive {


    @Test
    public void showYoloLive() throws Exception {
        nu.pattern.OpenCV.loadLocally();
        String path = "src/test/resources";
        File file = new File(path);
        String absolutePath = file.getAbsolutePath();


        VideoCapture videoCapture = new VideoCapture(0);
        Mat image = new Mat();
        JFrame frame = new JFrame();
        JLabel jLabel = new JLabel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,600);
        frame.setContentPane(jLabel);
        frame.setVisible(true);
        frame.pack();
        Integer i = 0;

        while (true){
            videoCapture.read(image);
            frame.getContentPane().removeAll();

      /*      YoloNetServiceImpl yolo = new YoloNetServiceImpl(
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
            jLabel.setIcon(new ImageIcon(getImage(image)));
            jLabel.repaint();
        }
    }
    public BufferedImage getImage(Mat img) {
        Imgproc.resize(img, img, new Size(640, 480));
        MatOfByte matOfByte = new MatOfByte();
        Imgcodecs.imencode(".jpg", img, matOfByte);
        byte[] byteArray = matOfByte.toArray();
        BufferedImage bufImage = null;
        try {
            InputStream in = new ByteArrayInputStream(byteArray);
            bufImage = ImageIO.read(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bufImage;
    }

}
