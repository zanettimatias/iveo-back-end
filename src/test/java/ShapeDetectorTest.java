import org.junit.Test;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.opencv.highgui.HighGui.imshow;
import static org.opencv.highgui.HighGui.waitKey;
import static org.opencv.imgproc.Imgproc.*;

public class ShapeDetectorTest {

    @Test
    public void shapeDetector() {


        nu.pattern.OpenCV.loadLocally();
        String path = "src/test/resources";
        File file = new File(path);
        String absolutePath = file.getAbsolutePath();


        Mat image = Imgcodecs.imread(absolutePath + File.separator + "bd" + File.separator + "1.png");
        //shapeDector(image);
        contours(image);
    }

    void contours(Mat image) {
        Mat grayMat = new Mat();
        Mat cannyEdges = new Mat();
        Mat hierarchy = new Mat();

        List<MatOfPoint> contourList = new ArrayList<MatOfPoint>(); //A list to store all the contours

        //Converting the image to grayscale
        Imgproc.cvtColor(image, grayMat, COLOR_BGR2GRAY);

        Imgproc.Canny(image, cannyEdges, 10, 100);

        //finding contours
        Imgproc.findContours(cannyEdges, contourList, hierarchy, Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);

        //Drawing contours on a new image
        Mat contours = new Mat();
        contours.create(cannyEdges.rows(), cannyEdges.cols(), CvType.CV_8UC3);
        Random r = new Random();
        for (int i = 0; i < contourList.size(); i++) {
            Imgproc.drawContours(contours, contourList, i, new Scalar(r.nextInt(255), r.nextInt(255), r.nextInt(255)), -1);
        }
        //Converting Mat back to Bitma
        imshow("YOLO", contours);
        waitKey();
    }


    void shapeDector(Mat image) {
        Mat resized = new Mat();
        Mat cannyEdges = new Mat();
        //Imgproc.resize(image,resized,new Size(300,300));
        resized = image;
        float ratio = image.height() / (float) resized.height();
        Imgproc.cvtColor(resized,resized,COLOR_BGR2GRAY);
        //Imgproc.GaussianBlur(resized,resized,new Size(5, 5),0);
        Imgproc.threshold(resized,resized,60,255,THRESH_BINARY);
        Imgproc.Canny(image, cannyEdges, 10, 100);
        List<MatOfPoint> contourList = new ArrayList<MatOfPoint>(); //A list to store all the contours
        Mat hierarchy = new Mat();
        Imgproc.findContours(resized,contourList,hierarchy,RETR_EXTERNAL,CHAIN_APPROX_NONE);

        Mat contours = new Mat();
        contours.create(cannyEdges.rows(), cannyEdges.cols(), CvType.CV_8UC3);
        Random r = new Random();
        for (int i = 0; i < contourList.size(); i++) {


            Moments m =  Imgproc.moments(contourList.get(i));

            double cX = ((m.get_m10()/ m.get_m10()) * ratio);
            double cY = ((m.get_m01()/ m.get_m00()) * ratio);

           /* Hay que seguir aca*/


            Imgproc.drawContours(contours, contourList, i, new Scalar(r.nextInt(255), r.nextInt(255), r.nextInt(255)), -1);
        }
        //Converting Mat back to Bitma
        imshow("YOLO", contours);
        waitKey();
    }



}
