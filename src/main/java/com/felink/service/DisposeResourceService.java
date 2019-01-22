package com.felink.service;

import com.felink.service.common.model.BasePoint;
import com.felink.service.configurer.Path;
import com.felink.service.common.model.BaseVector;
import com.felink.service.dispose.dynamic.DynamicFactory;
import com.felink.service.dispose.effects.EffectsFactory;
import com.felink.service.dispose.transitions.TransitionsFactory;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class DisposeResourceService {
    public static String doEffects(int tempId, String token, String ...contents) {
        return EffectsFactory.doEffects(tempId, token, contents);
    }

    public static String doTransitions(int tempId, String token, String suffix) {
        LinkedList<String> images = new LinkedList<>();
        for(int i = 0; i < 4; i++) {
            images.add(Path.TRANSITIONS_INPUT_PATH + token + File.separator + "origin_" + i + "." + suffix);
        }
        int[] trans = TransitionsFactory.getTrans(tempId);
        return TransitionsFactory.doTransitions(token, trans, images);
    }

    public static String doDynamic(String token, String imageSuffix, List<BaseVector> vectors, List<BasePoint> points) {
        return DynamicFactory.doDynamic(token, imageSuffix, vectors, points);
    }
}
