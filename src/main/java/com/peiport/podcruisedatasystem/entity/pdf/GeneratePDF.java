package com.peiport.podcruisedatasystem.entity.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.peiport.podcruisedatasystem.entity.Report;
import com.peiport.podcruisedatasystem.common.util.PDFUtil;
import com.peiport.podcruisedatasystem.common.util.TimeConvertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;

/**
 * 创建pdf
 *
 * @Author kylin
 * 2018.1.8
 */
@Component
public class GeneratePDF {
    private static final Logger LOGGER = LoggerFactory.getLogger(GeneratePDF.class);
        //临时文件保存目录
    private static String tempPath;

    @Value("${web.temp}")
    public void setTempPath(String tempPath) {
        GeneratePDF.tempPath = tempPath;
    }

    /**
     * 生成pdf
     *
     * @param os
     * @param
     */
    public static void createPDF(OutputStream os, Report report, String imgPath)throws Exception {
        Document document = new Document(PageSize.B4);
       // System.out.println("width="+PageSize.B4.getWidth()+",height="+PageSize.B4.getHeight());
        try {
            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            //设置字体样式
            Font textFont = new Font(bfChinese, 12, Font.NORMAL);//正常
            Font boldFont = new Font(bfChinese, 11, Font.BOLD); //加粗
            Font firsetTitleFont = new Font(bfChinese, 22, Font.BOLD); //一级标题
            Font secondTitleFont = new Font(bfChinese, 16, Font.BOLD); //二级标题
            Font underlineFont = new Font(bfChinese, 11, Font.UNDERLINE); //下划线斜体
            //创建输出流
            PdfWriter pdfWriter = PdfWriter.getInstance(document, os);
            document.open();
            //B4规格width=708.0,height=1000.0

            //添加logo
            Image logoImg = Image.getInstance(imgPath);
            logoImg .scaleToFit(100, 200);  //设置图片大小
            logoImg .setAbsolutePosition(10, 875);  //设置图片坐标
            document.add(logoImg);
            //创建table对象
            Paragraph p0 = new Paragraph();
            PdfPTable table = new PdfPTable(6);
            table.setSpacingBefore(10);
            table.setHorizontalAlignment(Element.ALIGN_RIGHT);   //设置表格居中
            table.setTotalWidth(new float[]{80, 130, 80, 80, 80, 80}); //设置列宽
            table.setLockedWidth(true); //锁定列宽

            PdfPCell cell = new PdfPCell();

            //添加表格内容
            table.addCell(PDFUtil.mergeTitleCol("巡航检测报告",secondTitleFont,6));

            table.addCell(PDFUtil.getPDFCell("单位名称", textFont));
            table.addCell(PDFUtil.mergeCol(report.getCompanyName(), textFont, 5));

            table.addCell(PDFUtil.getPDFCell("管理局", textFont));
            table.addCell(PDFUtil.getPDFCell(report.getOffice(), textFont));
            table.addCell(PDFUtil.getPDFCell("电压等级", textFont));
            table.addCell(PDFUtil.mergeCol(report.getVoltage(),textFont,3));

            table.addCell(PDFUtil.getPDFCell("线路名", textFont));
            table.addCell(PDFUtil.getPDFCell(report.getRouteName(), textFont));
            table.addCell(PDFUtil.getPDFCell("塔号", textFont));
            table.addCell(PDFUtil.mergeCol(report.getTowerNum(),textFont,3));

            table.addCell(PDFUtil.getPDFCell("设备名称", textFont));
            table.addCell(PDFUtil.getPDFCell(report.getDeviceName(), textFont));
            table.addCell(PDFUtil.getPDFCell("缺陷位置", textFont));
            table.addCell(PDFUtil.mergeCol(report.getImprefectLocaltion(),textFont,3));

            table.addCell(PDFUtil.getPDFCell("可见光图片", textFont));
            table.addCell(PDFUtil.getPDFCell(report.getKjgImageName(), textFont));
            table.addCell(PDFUtil.getPDFCell("拍摄时间", textFont));
            table.addCell(PDFUtil.getPDFCell(TimeConvertUtil.dateFormat2(report.getKjgImageTime()), textFont));
            table.addCell(PDFUtil.getPDFCell("操作人", textFont));
            table.addCell(PDFUtil.getPDFCell(report.getKjgOperator(), textFont));

            table.addCell(PDFUtil.getPDFCell("红外图片", textFont));
            table.addCell(PDFUtil.getPDFCell(report.getHwImageName(), textFont));
            table.addCell(PDFUtil.getPDFCell("拍摄时间", textFont));
            table.addCell(PDFUtil.getPDFCell(TimeConvertUtil.dateFormat2(report.getHwImageTime()), textFont));
            table.addCell(PDFUtil.getPDFCell("操作人", textFont));
            table.addCell(PDFUtil.getPDFCell(report.getHwOperator(), textFont));

            cell = PDFUtil.mergeCol("", textFont, 6);
            cell.setFixedHeight(175);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setPaddingTop(5);
            cell.setPaddingLeft(10);
            cell.setPaddingBottom(5);
            table.addCell(cell);

            LOGGER.info("tempPath = "+tempPath);
            File file = new File(tempPath);
            if (file.exists()){
                LOGGER.info("tempPath存在");
                File[] files = file.listFiles();
                if (files != null)
                for (File f: files){
                    LOGGER.info("文件名称="+f.getName());
                    if (f.getName().contains("可见光截图")){
                        LOGGER.info("PDF文件添加可见光截图");
                        Image img1 = Image.getInstance(tempPath+f.getName());
                        img1.scaleToFit(267, 150);
                        img1.setAbsolutePosition(160, 530);
                        document.add(img1);
                    }
                    if (f.getName().contains("红外截图")){
                        LOGGER.info("PDF文件添加红外截图");
                        Image img2 = Image.getInstance(tempPath+f.getName());
                        img2.scaleToFit(200, 150);
                        img2.setAbsolutePosition(455, 530);
                        document.add(img2);
                    }
                }
            }

            table.addCell(PDFUtil.mergeCol("故障描述",textFont,6));
            table.addCell(PDFUtil.mergeCol(report.getErrorDescription(),textFont,6));
            table.addCell(PDFUtil.mergeCol("处理意见",textFont,6));
            table.addCell(PDFUtil.mergeCol(report.getHandlerAdvice(),textFont,6));

            table.addCell(PDFUtil.getPDFCell("分析人", textFont));
            table.addCell(PDFUtil.mergeCol(report.getAssayer(),textFont,2));
            table.addCell(PDFUtil.getPDFCell("报告时间", textFont));
            table.addCell(PDFUtil.mergeCol(TimeConvertUtil.dateFormat(report.getReportTime()),textFont,2));

            table.addCell(PDFUtil.getPDFCell("审核意见", textFont));
            table.addCell(PDFUtil.mergeCol(report.getAuditAdvice(),textFont,5));

            table.addCell(PDFUtil.getPDFCell("批准意见", textFont));
            table.addCell(PDFUtil.mergeCol(report.getApproveAdvice(),textFont,5));
            p0.add(table);
            document.add(p0);
        } finally {
            // 关闭文档
            document.close();
        }


    }
}
