package com.example.hello;


import com.example.hello.util.QRCodeUtil;

public class QrCodeTest {

    public static void main(String[] args) throws Exception {
        // 存放在二维码中的内容
        String text = "测试文本内容";
        // 嵌入二维码的图片路径
        String imgPath = "D:/MyDemo3/qrCodeImg/info.jpg";
        // 生成的二维码的路径及名称
        String destPath = "D:/MyDemo3/qrCodeImg/jam.jpg";
        //生成二维码
        QRCodeUtil.encode(text, imgPath, destPath, true);
        // 解析二维码
        String str = QRCodeUtil.decode(destPath);
        // 打印出解析出的内容
        System.out.println(str);

    }

}

