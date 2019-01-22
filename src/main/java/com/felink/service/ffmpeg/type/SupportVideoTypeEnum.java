package com.felink.service.ffmpeg.type;

public enum SupportVideoTypeEnum {
    AVI("avi"), MPG("mpg"), GP3("3gp"),
    MOV("mov"), MP4("mp4"), ASF("asf"),
    ASX("asx"), FLV("flv");

    private String type;
    SupportVideoTypeEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static boolean exist(String typeName) {
        typeName = typeName.toLowerCase();
        return getEnumItem(typeName) != null;
    }

    public static SupportVideoTypeEnum getEnumItem(String typeName) {
        for(SupportVideoTypeEnum type: SupportVideoTypeEnum.values()) {
            if(type.getType().equals(typeName)){
                return type;
            }
        }
        return null;
    }

}
