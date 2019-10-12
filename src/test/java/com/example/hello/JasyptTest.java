package com.example.hello;


import com.alibaba.druid.filter.config.ConfigTools;
import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = HelloApplication.class)
public class JasyptTest {

    @Autowired
    private StringEncryptor stringEncryptor;

    @Test
    public void encryptPwd() throws Exception {
        //加密数据库真实密码123456
        String result = stringEncryptor.encrypt("123456");
        System.out.println(result);
        System.out.println("11111");
    }

    @Test
    public void encryptPwdDruid() throws Exception {
        try {
            String[] password = new String[]{"123456"};
            ConfigTools.main(password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
