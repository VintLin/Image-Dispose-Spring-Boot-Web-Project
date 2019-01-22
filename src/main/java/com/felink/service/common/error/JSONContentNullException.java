package com.felink.service.common.error;

/**
 * JSON 数据不存在异常
 * @author linwentao
 * @see com.felink.service.common.utility.JSONUtil
 */
public class JSONContentNullException extends Exception {
    private String JSONName;
    public JSONContentNullException(String name) {
        JSONName = name;
    }

    public String getJSONName() {
        return JSONName;
    }
}
