package com.peiport.podcruisedatasystem;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.peiport.podcruisedatasystem.common.util.TimeConvertUtil;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.util.Properties;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PodcruisedatasystemApplicationTests {

    @Value("${web.video-path}")
    private String videoPath;

    @Test
    public void contextLoads() {
    }


    @Test
    public void test() throws UnsupportedEncodingException {
        Properties initProp = new Properties(System.getProperties());
        System.out.println("当前系统编码:" + initProp.getProperty("file.encoding"));
        System.out.println("当前系统语言:" + initProp.getProperty("user.language"));
    }

    @Test
    public void test2() {
        File file1 = new File(videoPath);
        if (!file1.exists()) {
            file1.mkdir();
        }
    }

    @Test
    public void test3() throws Exception {
        FileInputStream fileInputStream = new FileInputStream("F:/testVideo/环保小视频.mp4");
        BufferedInputStream bis = new BufferedInputStream(fileInputStream);
        FileOutputStream ops = new FileOutputStream("F:/testVideo/环保小视频test.mp4");
        BufferedOutputStream bos = new BufferedOutputStream(ops);
        int length = 0;
        byte[] buf = new byte[1024];
        while ((length = bis.read(buf)) != -1) {
            bos.write(buf);
            bos.flush();
        }
    }

    @Test
    public void test4() throws IOException {
        InputStream in = new FileInputStream("D:/ftpServer/data/红外视频_广州局_500KV_广园东路线_20180912094517/视频/红外视频_广州局_500KV_广园东路线_0000-00_20180912094517.mp4");
        FileOutputStream out = new FileOutputStream("F:/testVideo/HWVideoTest.mp4");
        byte[] buf = new byte[1024];
        while (in.read(buf) != -1) {
            out.write(buf);
            out.flush();
        }
    }

    @Value("${web.image-path}")
    private String imageSavePath;

    @Value("${web.ffmpeg-path}")
    private String ffmpegPath;

    public void test5() {

    }

    @Test
    public void testLookImg() throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("static/img/国家电网lr.jpg");
        InputStream in = classPathResource.getInputStream();
        FileOutputStream out = new FileOutputStream("D:/img.jpg");
        byte[] buf = new byte[1024];
        int len = 0;
        while ((len = in.read(buf)) !=-1 ){
           out.write(buf,0,len);
        }
        out.close();
        in.close();
    }

    @Test
    public void testPDBackImg() throws IOException, DocumentException {
        //设置字体样式
        BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        Font textFont = new Font(bfChinese, 12, Font.NORMAL);//正常
        String resource_jpg = "D:\\java\\projects\\podcruisedatasystem\\src\\main\\resources\\static\\img\\国家电网lr.jpg";//
        OutputStream out = new FileOutputStream("E:\\test\\testImg.pdf");
        Document document = new Document(PageSize.A4, 0, 0, 0, 0);
        System.out.println(PageSize.A4.getHeight()+","+PageSize.A4.getWidth());
        PdfWriter writer = PdfWriter.getInstance(document, out);
        document.open();
        Paragraph p0 = new Paragraph();

        Paragraph p1 = new Paragraph("收费站名称：", textFont);
        p1.setLeading(40);
        p0.add(p1);
        Image img1 = Image.getInstance("D:\\java\\projects\\podcruisedatasystem\\src\\main\\resources\\static\\img\\国家电网lr.jpg");
        img1.scaleToFit(100, 200);  //设置图片大小
        img1.setAbsolutePosition(0, 742);  //设置图片坐标
        document.add(img1);
        // 关闭文档
        document.close();


    }

    @Test
    public void deleteFile() throws IOException {
        File file = new File("E:/test");
        FileUtils.deleteDirectory(file);
    }

    @Test
    public void timeTest(){
        long i =4512;
        System.out.println(TimeConvertUtil.convertHMS(i));


    }
}
















