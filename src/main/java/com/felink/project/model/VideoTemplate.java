package com.felink.project.model;

import net.sf.json.JSONObject;

import javax.persistence.*;

@Table(name = "video_template")
public class VideoTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "temp_type")
    private Integer tempType;

    @Column(name = "temp_id")
    private Integer tempId;

    private String image;

    private String video;

    private String intro;

    @Column(name = "image_num")
    private Integer imageNum;

    @Column(name = "text_num")
    private Integer textNum;

    @Column(name = "MaxLetter")
    private Integer maxletter;

//    public JSONObject getJSON() {
//        JSONObject obj = new JSONObject();
//        obj.put("tempId", tempId);
//        obj.put("dynamic", dynamic);
//        obj.put("video", video);
//        obj.put("intro", intro);
//        obj.put("imageNum", imageNum);
//        obj.put("textNum", textNum);
//        obj.put("MaxLetter", maxletter);
//        return obj;
//    }

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

//    /**
//     * @return temp_type
//     */
//    public Integer getTempType() {
//        return tempType;
//    }

    /**
     * @param tempType
     */
    public void setTempType(Integer tempType) {
        this.tempType = tempType;
    }

    /**
     * @return temp_id
     */
    public Integer getTempId() {
        return tempId;
    }

    /**
     * @param tempId
     */
    public void setTempId(Integer tempId) {
        this.tempId = tempId;
    }

    /**
     * @return dynamic
     */
    public String getImage() {
        return image;
    }

    /**
     * @param image
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * @return video
     */
    public String getVideo() {
        return video;
    }

    /**
     * @param video
     */
    public void setVideo(String video) {
        this.video = video;
    }

    /**
     * @return intro
     */
    public String getIntro() {
        return intro;
    }

    /**
     * @param intro
     */
    public void setIntro(String intro) {
        this.intro = intro;
    }

    /**
     * @return image_num
     */
    public Integer getImageNum() {
        return imageNum;
    }

    /**
     * @param imageNum
     */
    public void setImageNum(Integer imageNum) {
        this.imageNum = imageNum;
    }

    /**
     * @return text_num
     */
    public Integer getTextNum() {
        return textNum;
    }

    /**
     * @param textNum
     */
    public void setTextNum(Integer textNum) {
        this.textNum = textNum;
    }

    /**
     * @return MaxLetter
     */
    public Integer getMaxletter() {
        return maxletter;
    }

    /**
     * @param maxletter
     */
    public void setMaxletter(Integer maxletter) {
        this.maxletter = maxletter;
    }
}