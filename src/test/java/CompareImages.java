import org.junit.Test;
import org.opencv.calib3d.Calib3d;
import org.opencv.core.*;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.ORB;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class CompareImages {

    public final String img1Url = "C:\\Users\\Usuario\\IdeaProjects\\iveo-backend\\src\\test\\resources\\2.png";
    public final String img2Url = "C:\\Users\\Usuario\\IdeaProjects\\iveo-backend\\src\\test\\resources\\3.png";
    public final String img3Url = "C:\\Users\\Usuario\\IdeaProjects\\iveo-backend\\src\\test\\resources\\1.png";

    @Test
    public void compareImages() {
        nu.pattern.OpenCV.loadLocally();
        System.out.println(img1Url + "-" + img2Url);
        ORB detector = ORB.create();

        // first image
        Mat img1 = Imgcodecs.imread(img1Url, Imgcodecs.IMREAD_GRAYSCALE);
        Mat descriptors1 = new Mat();
        MatOfKeyPoint keypoints1 = new MatOfKeyPoint();

        detector.detect(img1, keypoints1);
        detector.compute(img1, keypoints1, descriptors1);

        // second image
        Mat img2 = Imgcodecs.imread(img2Url, Imgcodecs.IMREAD_GRAYSCALE);
        Mat descriptors2 = new Mat();
        MatOfKeyPoint keypoints2 = new MatOfKeyPoint();

        detector.detect(img2, keypoints2);
        detector.compute(img2, keypoints2, descriptors2);
        double nn_match_ratio = 0.8;

        //! [2-nn matching]
        DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE_HAMMING);
        List<MatOfDMatch> knnMatches = new ArrayList<>();
        matcher.knnMatch(img2, img2, knnMatches, 2);
        //! [2-nn matching]

        //! [ratio test filtering]
        float ratioThreshold = 0.8f; // Nearest neighbor matching ratio
        List<KeyPoint> listOfMatched1 = new ArrayList<>();
        List<KeyPoint> listOfMatched2 = new ArrayList<>();
        List<KeyPoint> listOfKeypoints1 = keypoints1.toList();
        List<KeyPoint> listOfKeypoints2 = keypoints2.toList();
        for (int i = 0; i < listOfKeypoints1.size() -1; i++) {
            DMatch[] matches = knnMatches.get(i).toArray();
            float dist1 = matches[0].distance;
            float dist2 = matches[1].distance;
            if (dist1 < ratioThreshold * dist2) {
                listOfMatched1.add(listOfKeypoints1.get(matches[0].queryIdx));
                listOfMatched2.add(listOfKeypoints2.get(matches[0].trainIdx));
            }
        }
        //! [ratio test filtering]

        //! [homography check]
        LinkedList<Point> object1 = new LinkedList<Point>();
        LinkedList<Point> object2 = new LinkedList<Point>();


        for(int i = 0; i<listOfMatched1.size(); i++){
            object1.addLast(listOfKeypoints1.get(i).pt);
            object2.addLast(listOfKeypoints2.get(i).pt);
        }

        MatOfPoint2f obj1 = new MatOfPoint2f();
        obj1.fromList(object1);

        MatOfPoint2f obj2 = new MatOfPoint2f();
        obj2.fromList(object2);


        Mat H = Calib3d.findHomography(obj1, obj2);
        double inlierThreshold = 2.5; // Distance threshold to identify inliers with homography check
        List<KeyPoint> listOfInliers1 = new ArrayList<>();
        List<KeyPoint> listOfInliers2 = new ArrayList<>();
        List<DMatch> listOfGoodMatches = new ArrayList<>();
        for (int i = 0; i < listOfMatched1.size(); i++) {
            Mat col = new Mat(3, 1, CvType.CV_64F);
            double[] colData = new double[(int) (col.total() * col.channels())];
            colData[0] = listOfMatched1.get(i).pt.x;
            colData[1] = listOfMatched1.get(i).pt.y;
            colData[2] = 1.0;
            col.put(0, 0, colData);



            Mat colRes = new Mat();
            Core.gemm(H, col, 1.0, new Mat(), 0.0, colRes);
            colRes.get(0, 0, colData);
            Core.multiply(colRes, new Scalar(1.0 / colData[2]), col);
            col.get(0, 0, colData);

            double dist = Math.sqrt(Math.pow(colData[0] - listOfMatched2.get(i).pt.x, 2) +
                    Math.pow(colData[1] - listOfMatched2.get(i).pt.y, 2));

            if (dist < inlierThreshold) {
                listOfGoodMatches.add(new DMatch(listOfInliers1.size(), listOfInliers2.size(), 0));
                listOfInliers1.add(listOfMatched1.get(i));
                listOfInliers2.add(listOfMatched2.get(i));
            }
        }

        double inlierRatio = listOfInliers1.size() / (double) listOfMatched1.size();
        System.out.println("A-KAZE Matching Results");
        System.out.println("*******************************");
        System.out.println("# Keypoints 1:                        \t" + listOfKeypoints1.size());
        System.out.println("# Keypoints 2:                        \t" + listOfKeypoints2.size());
        System.out.println("# Matches:                            \t" + listOfMatched1.size());
        System.out.println("# Inliers:                            \t" + listOfInliers1.size());
        System.out.println("# Inliers Ratio:                      \t" + inlierRatio);

    }


    @Test
    public void CompareHist() {
        //! [Load three images with different environment settings]
        nu.pattern.OpenCV.loadLocally();
        Mat srcBase = Imgcodecs.imread(img1Url);
        Mat srcTest1 = Imgcodecs.imread(img2Url);
        Mat srcTest2 = Imgcodecs.imread(img3Url);
        if (srcBase.empty() || srcTest1.empty() || srcTest2.empty()) {
            System.err.println("Cannot read the images");
            System.exit(0);
        }
        //! [Load three images with different environment settings]

        //! [Convert to HSV]
        Mat hsvBase = new Mat(), hsvTest1 = new Mat(), hsvTest2 = new Mat();
        Imgproc.cvtColor(srcBase, hsvBase, Imgproc.COLOR_BGR2HSV);
        Imgproc.cvtColor(srcTest1, hsvTest1, Imgproc.COLOR_BGR2HSV);
        Imgproc.cvtColor(srcTest2, hsvTest2, Imgproc.COLOR_BGR2HSV);
        //! [Convert to HSV]

        //! [Convert to HSV half]
        Mat hsvHalfDown = hsvBase.submat(new Range(hsvBase.rows() / 2, hsvBase.rows() - 1), new Range(0, hsvBase.cols() - 1));
        //! [Convert to HSV half]

        //! [Using 50 bins for hue and 60 for saturation]
        int hBins = 50, sBins = 60;
        int[] histSize = {hBins, sBins};

        // hue varies from 0 to 179, saturation from 0 to 255
        float[] ranges = {0, 180, 0, 256};

        // Use the 0-th and 1-st channels
        int[] channels = {0, 1};
        //! [Using 50 bins for hue and 60 for saturation]

        //! [Calculate the histograms for the HSV images]
        Mat histBase = new Mat(), histHalfDown = new Mat(), histTest1 = new Mat(), histTest2 = new Mat();

        List<Mat> hsvBaseList = Arrays.asList(hsvBase);
        Imgproc.calcHist(hsvBaseList, new MatOfInt(channels), new Mat(), histBase, new MatOfInt(histSize), new MatOfFloat(ranges), false);
        Core.normalize(histBase, histBase, 0, 1, Core.NORM_MINMAX);

        List<Mat> hsvHalfDownList = Arrays.asList(hsvHalfDown);
        Imgproc.calcHist(hsvHalfDownList, new MatOfInt(channels), new Mat(), histHalfDown, new MatOfInt(histSize), new MatOfFloat(ranges), false);
        Core.normalize(histHalfDown, histHalfDown, 0, 1, Core.NORM_MINMAX);

        List<Mat> hsvTest1List = Arrays.asList(hsvTest1);
        Imgproc.calcHist(hsvTest1List, new MatOfInt(channels), new Mat(), histTest1, new MatOfInt(histSize), new MatOfFloat(ranges), false);
        Core.normalize(histTest1, histTest1, 0, 1, Core.NORM_MINMAX);

        List<Mat> hsvTest2List = Arrays.asList(hsvTest2);
        Imgproc.calcHist(hsvTest2List, new MatOfInt(channels), new Mat(), histTest2, new MatOfInt(histSize), new MatOfFloat(ranges), false);
        Core.normalize(histTest2, histTest2, 0, 1, Core.NORM_MINMAX);
        //! [Calculate the histograms for the HSV images]

        //! [Apply the histogram comparison methods]
        for (int compareMethod = 0; compareMethod < 4; compareMethod++) {
            double baseBase = Imgproc.compareHist(histBase, histBase, compareMethod);
            double baseHalf = Imgproc.compareHist(histBase, histHalfDown, compareMethod);
            double baseTest1 = Imgproc.compareHist(histBase, histTest1, compareMethod);
            double baseTest2 = Imgproc.compareHist(histBase, histTest2, compareMethod);

            System.out.println("Method " + compareMethod + " Perfect, Base-Half, Base-Test(1), Base-Test(2) : " + baseBase + " / " + baseHalf
                    + " / " + baseTest1 + " / " + baseTest2);
        }
        //! [Apply the histogram comparison methods]
    }
}

  /*  public class CompareHistDemo {
        public static void main(String[] args) {
            // Load the native OpenCV library
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

            new CompareHist().run(args);
        }
    }*/

