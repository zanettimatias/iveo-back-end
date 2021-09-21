package ar.com.mzanetti.iveo.service;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.features2d.AKAZE;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

@Service
public class CompareFactory {

    @Value("{transform.mat}")
    String transformMat;

    public void compare (Mat img1, Mat img2){

        Mat desc1 = new Mat(), desc2 = new Mat();
        Mat homography = loadHomography();
        akazeDetect(img1,img2,desc1,desc2);



    }

    @Bean
    public Mat loadHomography(){
        File file = new File(transformMat);
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder;
        Document document;
        Mat homography = new Mat(3, 3, CvType.CV_64F);
        double[] homographyData = new double[(int) (homography.total()*homography.channels())];
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.parse(file);
            String homographyStr = document.getElementsByTagName("data").item(0).getTextContent();
            String[] splited = homographyStr.split("\\s+");
            int idx = 0;
            for (String s : splited) {
                if (!s.isEmpty()) {
                    homographyData[idx] = Double.parseDouble(s);
                    idx++;
                }
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            System.exit(0);
        } catch (SAXException e) {
            e.printStackTrace();
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }

        homography.put(0, 0, homographyData);
        return homography;
    }

    public void akazeDetect(Mat img1, Mat img2, Mat desc1, Mat desc2){
        AKAZE akaze = AKAZE.create();
        MatOfKeyPoint kpts1 = new MatOfKeyPoint(), kpts2 = new MatOfKeyPoint();
        akaze.detectAndCompute(img1, new Mat(), kpts1, desc1);
        akaze.detectAndCompute(img2, new Mat(), kpts2, desc2);
    }


}
