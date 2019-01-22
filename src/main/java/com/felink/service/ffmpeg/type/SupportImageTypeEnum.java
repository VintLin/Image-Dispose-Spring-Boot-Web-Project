package com.felink.service.ffmpeg.type;

public enum SupportImageTypeEnum {
    JPG("jpg"), GIF("gif"), JPEG("jpeg");

    private String type;
    SupportImageTypeEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static boolean exist(String typeName) {
        typeName = typeName.toLowerCase();
        return getEnumItem(typeName) != null;
    }

    public static SupportImageTypeEnum getEnumItem(String typeName) {
        for(SupportImageTypeEnum type: SupportImageTypeEnum.values()) {
            if(type.getType().equals(typeName)){
                return type;
            }
        }
        return null;
    }
}
