package com.felink.service.ffmpeg.type;

public enum NonsupportTypeEnum {
    WMV9("wmv9"), RM("rm"), RMVB("RMVB");

    private String type;
    NonsupportTypeEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static boolean exist(String typeName) {
        typeName = typeName.toLowerCase();
        return getEnumItem(typeName) != null;
    }

    public static NonsupportTypeEnum getEnumItem(String typeName) {
        for(NonsupportTypeEnum type: NonsupportTypeEnum.values()) {
            if(type.getType().equals(typeName)){
                return type;
            }
        }
        return null;
    }
}
