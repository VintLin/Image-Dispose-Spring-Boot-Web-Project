package com.felink.service;

import com.felink.project.Application;
import com.felink.service.common.model.BasePoint;
import com.felink.service.common.model.BaseVector;
import com.felink.service.common.utility.JSONUtil;
import com.felink.service.configurer.Path;
import com.felink.service.dispose.dynamic.dynamic.DynamicControler;
import com.felink.service.ffmpeg.core.Executor;
import org.slf4j.Logger;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@Transactional
@Rollback
public class DynamicTest {
    private String file = Path.DYNAMIC_INPUT_PATH + "1.jpg";
    private String token = "test-dynamic-";
    private Logger log = LoggerFactory.getLogger(EffectsTest.class);


    public void dynamicTest() {
        String path = Path.DYNAMIC_INPUT_PATH + "test-dynamic-do" + File.separator;
        String vector = "[{\"begin\":[67.83333587646484,523],\"end\":[46.5,513.6666870117188]},{\"begin\":[27.83333396911621,497],\"end\":[24.16666603088379,473]},{\"begin\":[28.5,446.6666564941406],\"end\":[63.16666793823242,437.3333435058594]},{\"begin\":[120.83333587646484,550.3333129882812],\"end\":[100.5,534.3333129882812]},{\"begin\":[61.83333206176758,547],\"end\":[30.83333396911621,534.3333129882812]},{\"begin\":[109.83333587646484,502.3333435058594],\"end\":[108.5,467.3333435058594]},{\"begin\":[138.5,463.6666564941406],\"end\":[165.5,472.6666564941406]},{\"begin\":[94.16666412353516,424.6666564941406],\"end\":[137.1666717529297,427.6666564941406]},{\"begin\":[167.1666717529297,420.6666564941406],\"end\":[204.1666717529297,391]},{\"begin\":[228.5,382],\"end\":[276.5,387]},{\"begin\":[306.1666564941406,475.3333435058594],\"end\":[341.8333435058594,451]},{\"begin\":[219.1666717529297,486.6666564941406],\"end\":[254.5,482.6666564941406]},{\"begin\":[184.8333282470703,477],\"end\":[204.5,481.3333435058594]},{\"begin\":[265.5,474.6666564941406],\"end\":[295.8333435058594,466.6666564941406]},{\"begin\":[349.8333435058594,424.3333435058594],\"end\":[353.5,371.6666564941406]},{\"begin\":[297.1666564941406,390.3333435058594],\"end\":[330.8333435058594,382.3333435058594]},{\"begin\":[342.8333435058594,343.3333435058594],\"end\":[329.1666564941406,325]},{\"begin\":[310.8333435058594,306.3333435058594],\"end\":[286.5,310.3333435058594]},{\"begin\":[281.1666564941406,322],\"end\":[253.8333282470703,344.3333435058594]},{\"begin\":[215.8333282470703,359],\"end\":[223.1666717529297,327]},{\"begin\":[237.1666717529297,310],\"end\":[260.8333435058594,269.6666564941406]},{\"begin\":[68.5,413.3333435058594],\"end\":[87.83333587646484,371.6666564941406]},{\"begin\":[86.83333587646484,344],\"end\":[50.83333206176758,335.3333435058594]},{\"begin\":[25.16666603088379,324],\"end\":[15.833333015441895,287.3333435058594]},{\"begin\":[28.5,236],\"end\":[62.16666793823242,195.6666717529297]},{\"begin\":[82.16666412353516,303.3333435058594],\"end\":[52.16666793823242,279]},{\"begin\":[63.5,248.6666717529297],\"end\":[95.83333587646484,210]},{\"begin\":[118.83333587646484,215.6666717529297],\"end\":[153.1666717529297,238.6666717529297]},{\"begin\":[152.8333282470703,257.3333435058594],\"end\":[129.1666717529297,290]},{\"begin\":[22.16666603088379,207.3333282470703],\"end\":[60.5,147.3333282470703]},{\"begin\":[81.5,66.66666412353516],\"end\":[35.83333206176758,67.66666412353516]},{\"begin\":[90.83333587646484,40],\"end\":[43.83333206176758,32.33333206176758]},{\"begin\":[160.5,95],\"end\":[108.16666412353516,80.33333587646484]},{\"begin\":[146.1666717529297,73.66666412353516],\"end\":[111.16666412353516,48]},{\"begin\":[198.5,102.66666412353516],\"end\":[180.8333282470703,98.33333587646484]},{\"begin\":[209.5,82.33333587646484],\"end\":[180.1666717529297,74.33333587646484]},{\"begin\":[245.8333282470703,109.66666412353516],\"end\":[210.5,105.66666412353516]},{\"begin\":[282.8333435058594,260],\"end\":[334.1666564941406,229]},{\"begin\":[349.5,216.6666717529297],\"end\":[350.1666564941406,194]},{\"begin\":[320.1666564941406,184.6666717529297],\"end\":[270.5,133]},{\"begin\":[345.8333435058594,30.33333396911621],\"end\":[324.5,51.66666793823242]},{\"begin\":[302.8333435058594,77],\"end\":[266.1666564941406,86.66666412353516]},{\"begin\":[245.1666717529297,87.33333587646484],\"end\":[230.5,87]}]";
        String points = "[[13.166666984558105,590],[36.83333206176758,564.6666870117188],[66.5,575.6666870117188],[17.83333396911621,564.6666870117188],[86.5,572.6666870117188],[111.5,569.3333129882812],[128.1666717529297,581],[151.5,582.6666870117188],[170.8333282470703,598.6666870117188],[191.8333282470703,566.3333129882812],[207.5,541.3333129882812],[234.5,533.6666870117188],[248.1666717529297,512],[263.1666564941406,487.6666564941406],[290.1666564941406,487],[306.1666564941406,520],[305.1666564941406,553.6666870117188],[334.5,559.6666870117188],[356.5,562]]";
        try {
            long startTime = System.currentTimeMillis();
            List<BaseVector> vs = JSONUtil.getVectorsByJSON(vector);
            List<BasePoint> ps = JSONUtil.getPointsByJSON(points);
            DynamicControler dynamicControler = new DynamicControler(path + "origin.png", path, vs, ps);
            dynamicControler.doDynamic();
            String tempImagePath = path + "%d.png";
            String outputPath = Path.DYNAMIC_OUTPUT_PATH + "test-dynamic-do"  + ".mp4";
            String backgroundPath = Path.BACKGROUND_PATH + "720x1080background_video.mp4";
            String audio = Path.AUDIO_PATH + "audio_1.mp3";
//            Executor.getInstance().setVideoInVideo( backgroundPath, tempImagePath, audio, outputPath);
            Executor.getInstance().imageToVideo(tempImagePath, outputPath, null, 30);
            long endTime = System.currentTimeMillis();
            System.out.println("程序"+ (endTime-startTime) +"s");
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}
