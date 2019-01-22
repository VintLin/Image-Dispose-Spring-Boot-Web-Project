package com.felink.service;

import com.felink.project.Application;
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
public class EffectsTest {
    private int tempId = 0;
    private String token = "test-effects-";
    private Logger log = LoggerFactory.getLogger(EffectsTest.class);

    @Test
    public void surNameTest() {
        tempId = 0;
        token += "0";
        String surname = "妙";
        String content = " 妙果虽圆心不有";
        String outputPath = DisposeResourceService.doEffects(tempId, token, surname, content);
        log.info("OUTPUT PATH: " + outputPath);
    }

    @Test
    public void twoSentencesTest() {
        tempId = 1;
        token += "1";
        String one = "东市买骏马";
        String two = "西市买长鞭";
        String outputPath = DisposeResourceService.doEffects(tempId, token, one, two);
        log.info("OUTPUT PATH: " + outputPath);
    }

    @Test
    public void wordTest() {
        tempId = 2;
        token += "2";
        String word = "世界";
        String outputPath = DisposeResourceService.doEffects(tempId, token, word);
        log.info("OUTPUT PATH: " + outputPath);
    }
}
