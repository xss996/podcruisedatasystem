package com.peiport.podcruisedatasystem.util.ftp;

import com.peiport.podcruisedatasystem.entity.ftp.FTPObject;
import com.peiport.podcruisedatasystem.exception.MyException;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import sun.net.ftp.FtpClient;
import sun.net.ftp.FtpDirEntry;
import sun.net.ftp.FtpProtocolException;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Component
public class FTPUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(FTPUtil.class);


    /**
     * 根据用户名密码登录ftp服务器
     *
     *
     * @return
     */
    public static FTPClient loginFTP(FTPObject object) throws IOException {
        String ip = object.getIp();
        Integer port = object.getPort();
        String username = object.getUsername();
        String password = object.getPassword();
        //System.out.println(ip+port+loginName+loginPsw);
        FTPClient ftpClient = null;
        ftpClient = new FTPClient();
        try {
            ftpClient.connect(ip, port);
        } catch (IOException e) {
            throw new MyException("1001", "ftp建立连接失败，请确认ip端口号是否正确！", "loginFTP", "ftp登录失败");
        }
        ftpClient.enterLocalPassiveMode();
        ftpClient.setControlEncoding("GBK");   //设置编码为GBK,和电脑编码保持一致
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        LOGGER.info("ftp登录信息：ip="+ip+",port="+port+",username="+username+",password="+password);
        if (!ftpClient.login(username, password)) {
            LOGGER.error("ftp建立连接失败");
            throw new MyException("1001", "ftp建立连接失败，请确认账号密码是否正确！", "loginFTP", "ftp登录失败");
        }
        return ftpClient;
    }

    /**
     * 退出并销毁ftp
     *
     * @param ftpClient
     */
    public static void closeConnection(FTPClient ftpClient) {
        if (ftpClient != null) {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.logout();
                    ftpClient.disconnect();
                   // LOGGER.info("ftp退出关闭连接......");
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    /**
     * 查看ftp某个文件下的所有文件
     *
     * @param ftpClient
     * @param pathName
     * @return
     * @throws IOException
     * @throws FtpProtocolException
     */
    public static List<FTPFile> list(FTPClient ftpClient, String pathName) throws IOException, FtpProtocolException {
        List<FTPFile> list = new ArrayList<>();
        if (pathName.startsWith("/") && pathName.endsWith("/")) {
            ftpClient.changeWorkingDirectory(pathName);
            FTPFile[] files = ftpClient.listFiles();
            if (files != null) {
                list.addAll(Arrays.asList(files));
            }
        }
        return list;
    }

    /**
     * 关闭输入输出流
     *
     * @param out
     * @param in
     */
    public static void closeIO(OutputStream out, InputStream in) throws IOException {
        if (out != null)
            out.close();
        if (in != null)
            in.close();
    }


    /**
     *  
     *      * @Description 
     *      * @author xukaixun
     *      * @param zipSavePath   压缩好的zip包存放路径
     *      * @param sourceFile    待压缩的文件（单个文件或者整个文件目录）
     *      * @return  
     *      
     */
    public static void zipCompress(String zipSavePath, File sourceFile) throws Exception {
        //创建zip输出流
        ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipSavePath));
        compress(zos, sourceFile, sourceFile.getName());
        zos.close();
    }

    private static void compress(ZipOutputStream zos, File sourceFile, String fileName) throws Exception {
        if (sourceFile.isDirectory()) {
//如果是文件夹，取出文件夹中的文件（或子文件夹）
            File[] fileList = sourceFile.listFiles();
            if (fileList.length == 0)//如果文件夹为空，则只需在目的地zip文件中写入一个目录进入点
            {
                zos.putNextEntry(new ZipEntry(fileName + "/"));
            } else//如果文件夹不为空，则递归调用compress，文件夹中的每一个文件（或文件夹）进行压缩
            {
                for (File file : fileList) {
                    compress(zos, file, fileName + "/" + file.getName());
                }
            }
        } else {
            if (!sourceFile.exists()) {
                zos.putNextEntry(new ZipEntry("/"));
                zos.closeEntry();
            } else {
//单个文件，直接将其压缩到zip包中
                zos.putNextEntry(new ZipEntry(fileName));
                FileInputStream fis = new FileInputStream(sourceFile);
                byte[] buf = new byte[1024];
                int len;
//将源文件写入到zip文件中
                while ((len = fis.read(buf)) != -1) {
                    zos.write(buf, 0, len);
                }
                zos.closeEntry();
                fis.close();
            }
        }
    }


}
