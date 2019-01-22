package com.felink.service;

import com.felink.project.Application;
import com.felink.service.common.model.BaseImage;
import com.felink.service.configurer.Path;
import com.felink.service.dispose.transitions.transitions.AbstractTransitions;
import com.felink.service.dispose.transitions.transitions.CircleTransitions;
import com.felink.service.dispose.transitions.transitions.MultiCircleTransitions;
import com.felink.service.dispose.transitions.transitions.RectangleTransitions;
import org.slf4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@Transactional
@Rollback
public class TransitionsTest {
    private int tempId = 0;
    private String token = "test-transitions-";
    private Logger log = LoggerFactory.getLogger(EffectsTest.class);
    private String file1 = Path.TRANSITIONS_INPUT_PATH + "1.jpg";
    private String file2 = Path.TRANSITIONS_INPUT_PATH + "2.jpg";

    @Test
    public void circleTest() {
        token += "Circle";
        String tempPath = Path.TRANSITIONS_INPUT_PATH + token;
        AbstractTransitions transitions = new CircleTransitions(file1, file2, tempPath, 0);
        transitions.doTransform();
        log.info("Circle Done");
    }

    @Test
    public void multiCircleTest() {
        token += "MultiCircle";
        String tempPath = Path.TRANSITIONS_INPUT_PATH + token;
        AbstractTransitions transitions = new MultiCircleTransitions(file1, file2, tempPath, 0);
        transitions.doTransform();
        log.info("MultiCircle Done");
    }

    @Test
    public void rectangleTest() {
        token += "Rectangle";
        String tempPath = Path.TRANSITIONS_INPUT_PATH + token;
        AbstractTransitions transitions = new RectangleTransitions(file1, file2, tempPath, 0);
        transitions.doTransform();
        log.info("Rectangle Done");
    }

    @Test
    public void transitions_one() {
        tempId = 0;
        token += "0";
        String outputPath = DisposeResourceService.doTransitions(tempId, token, "jpg");
        log.info("OUTPUT PATH: " + outputPath);
    }

    @Test
    public void transitions_two() {
        tempId = 1;
        token += "1";
        String outputPath = DisposeResourceService.doTransitions(tempId, token, "jpg");
        log.info("OUTPUT PATH: " + outputPath);
    }

    @Test
    public void transitions_three() {
        tempId = 2;
        token += "2";
        String outputPath = DisposeResourceService.doTransitions(tempId, token, "jpg");
        log.info("OUTPUT PATH: " + outputPath);
    }
}
