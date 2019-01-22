package com.felink.project.model;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

public class ResponseJSON {
    private int resultCode;
    private String resultMessage;
    private JSONObject data;

    public ResponseJSON(int resultCode, String resultMessage, JSONObject data) {
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
        this.data = data;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public JSONObject getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }

    public JSONObject getJSON() {
        JSONObject responseBody = new JSONObject();
        responseBody.put("ResultCode", resultCode);
        responseBody.put("ResultMessage", resultMessage);
        responseBody.put("Data", data);
        return responseBody;
    }

    public static boolean isJSONValid(String text) {
        try {
            JSONObject.fromObject(text);
        } catch (JSONException ex) {
            try {
                JSONArray.fromObject(text);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }

}
