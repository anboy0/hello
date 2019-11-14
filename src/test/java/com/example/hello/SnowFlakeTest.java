package com.example.hello;

import com.example.hello.util.snowFlake.SnowFlake;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = HelloApplication.class)
public class SnowFlakeTest {

    @Test
    public static void main(String[] args) {
        SnowFlake snowFlake = new SnowFlake(2, 3);
        for (int i = 0; i < (1 << 12); i++) {
            System.out.println(snowFlake.nextId());
        }


    }

}
