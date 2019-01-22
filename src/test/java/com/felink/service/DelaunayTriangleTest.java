package com.felink.service;

import com.felink.project.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@Transactional
@Rollback
public class DelaunayTriangleTest {
    private Logger log = LoggerFactory.getLogger(DelaunayTriangleTest.class);
    @Test
    public void delaunayTest() {
    }
}
