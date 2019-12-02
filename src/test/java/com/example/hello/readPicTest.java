package com.example.hello;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class readPicTest {

    public static void main(String[] args) throws Exception {
        /*System.out.println("开始读取图片信息...");
//        File jpegFile = new File("g:\\123.jpg");
        File jpegFile = new File("D:/MyDemo3/qrCodeImg/999.jpg");
        Metadata metadata;
        try {
            metadata = JpegMetadataReader.readMetadata(jpegFile);
            Iterator<Directory> it = metadata.getDirectories().iterator();
            while (it.hasNext()) {
                Directory exif = it.next();
                Iterator<Tag> tags = exif.getTags().iterator();
                while (tags.hasNext()) {
                    Tag tag = (Tag) tags.next();
                    System.out.println(tag);
                }
            }
            System.out.println("图片信息读取完成！");
        } catch (JpegProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        File file = new File("D:/MyDemo3/qrCodeImg/999.jpg");
        Metadata metadata = ImageMetadataReader.readMetadata(file);
        for (Directory directory : metadata.getDirectories()) {
            for (Tag tag : directory.getTags()) {
                String tagName = tag.getTagName(); // 标签名
                String desc = tag.getDescription(); // 标签信息
                if (tagName.equals("Image Height")) {
                    System.out.println("图片高度: " + desc);
                } else if (tagName.equals("Image Width")) {
                    System.out.println("图片宽度: " + desc);
                } else if (tagName.equals("Date/Time Original")) {
                    System.out.println("拍摄时间: " + desc);
                } else if (tagName.equals("GPS Latitude")) {
                    System.err.println("纬度 : " + desc);
// System.err.println("纬度(度分秒格式) : "+pointToLatlong(desc));
                } else if (tagName.equals("GPS Longitude")) {
                    System.err.println("经度: " + desc);
// System.err.println("经度(度分秒格式): "+pointToLatlong(desc));
                }
            }
        }
    }

    public static String pointToLatlong(String point) {
        Double du = Double.parseDouble(point.substring(0, point.indexOf("°")).trim());
        Double fen = Double.parseDouble(point.substring(point.indexOf("°") + 1, point.indexOf("'")).trim());
        Double miao = Double.parseDouble(point.substring(point.indexOf("'") + 1, point.indexOf("\"")).trim());
        Double duStr = du + fen / 60 + miao / 60 / 60;
        return duStr.toString();
    }
}
