package com.peiport.podcruisedatasystem;

import org.junit.Test;

import java.io.File;

public class FileOperator {
    @Test
    public void rename(){
        File file = new File("D:\\ftpServer\\data\\test");
        File[] files = file.listFiles();
        for (File f:files){
            String date= (f.getName().split("_")[9]).split("\\.")[0];
            System.out.println(date);
            String name = f.getName().split("_",8)[0];
            System.out.println(name);
           //f.renameTo(new File("D:\\ftpServer\\data\\test\\"));
        }

    }

}
