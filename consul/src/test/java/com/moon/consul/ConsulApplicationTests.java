package com.moon.consul;

import com.moon.consul.entity.HeroNode;
import com.moon.consul.entity.SingleLinkedList;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
@Slf4j
class ConsulApplicationTests {

    @Test
    void contextLoads() {
        SingleLinkedList singleLinkedList = new SingleLinkedList();
        singleLinkedList.addByOrder(new HeroNode(1,"宋江","及时雨"));
        singleLinkedList.addByOrder(new HeroNode(2,"吴用","智多星"));
        singleLinkedList.addByOrder(new HeroNode(4,"林冲","豹子头"));
        singleLinkedList.addByOrder(new HeroNode(3,"卢俊义","玉麒麟"));
        log.info("{}-------------",singleLinkedList);
    }

}
