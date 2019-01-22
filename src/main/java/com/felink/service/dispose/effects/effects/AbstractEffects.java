package com.felink.service.dispose.effects.effects;

import com.felink.service.configurer.Path;
import com.felink.service.common.model.BaseImage;

abstract class AbstractEffects {

    BaseImage image;

    AbstractEffects(String inputPath, String savePath) {
        image = new BaseImage(inputPath, savePath);
        doEffects();
    }

    abstract void doEffects();

    public String getSavePath() {
        return image.getSavePath() + "%d." + image.getSuffix();
    }
}
