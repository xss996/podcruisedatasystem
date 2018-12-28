package com.peiport.podcruisedatasystem.common;

public class Constant {

    //文件类型
    public static final int FILE_TYPE_LOCAL = 1;  //本地文件
    public static final int FILE_TYPE_FTP = 2;    //ftp文件

    //图片类型
    public static final String IMG_TYPE_HWJT = "红外截图"; //红外截图
    public static final String IMG_TYPE_HWRT = "红外热图"; //红外热图
    public static final String IMG_TYPE_KJGJT = "可见光截图"; //可见光截图

    //ftp文件名相关
    public static final String FTP_DIRCTORY_ROOT = "/data/";  //ftp文件存储根目录
    public static final String FTP_IMG_PATH_NAME = "/图片/";   //ftp图片存放路径名
    public static final String FTP_VIDEO_PATH_NAME = "/视频/"; //ftp视频存放路径名

    //通道种类
    public static final Integer CHANNEL_IR= 1;
    public static final Integer CHANNEL_TV = 2;

    //操作结果
    public static final int SUCCESS =1; //操作成功
    public static final int ERROR = 0; //失败

}
