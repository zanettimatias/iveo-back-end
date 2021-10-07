import ar.com.mzanetti.iveo.Application;
import ar.com.mzanetti.iveo.service.ORBFlannPatternFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {OpenCvLibrary.class, Application.class})
public class CompareDef {

    @Autowired
    ORBFlannPatternFactory orbFlannPatternFactory;

    @Test
    public void compare() {
        String path = "src/test/resources";
        File file = new File(path);
        String absolutePath = file.getAbsolutePath();

        Mat img1 = Imgcodecs.imread(absolutePath + File.separator + "bd" + File.separator + "8.png", Imgcodecs.IMREAD_GRAYSCALE);
        Imgproc.resize(img1,img1,new Size(450,450));
        Mat img2 = Imgcodecs.imread(absolutePath + File.separator + "bd" + File.separator + "1.png", Imgcodecs.IMREAD_GRAYSCALE);
        Imgproc.resize(img2,img2,new Size(450,450));


        System.setProperty("java.awt.headless", "false");
        System.out.println(orbFlannPatternFactory.compare(img1,img2));

    }

}
