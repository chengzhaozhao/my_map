package com.czz.map;

import com.czz.map.service.MyMap;
import com.czz.map.service.impl.MyHashMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MyMapApplicationTests {

    @Test
    public void contextLoads() {
        MyMap<String,String> myMap = new MyHashMap<>();

        for (int i = 0; i < 500; i++) {
            myMap.put("key"+i,"value"+i);
        }

        for (int i = 0; i < 500; i++) {
            System.out.println("key"+i+",value is" + myMap.get("key"+i));
        }
    }

}
