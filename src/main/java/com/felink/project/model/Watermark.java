package com.felink.project.model;

import java.util.Date;
import javax.persistence.*;

public class Watermark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "watermark_name")
    private String watermarkName;

    @Column(name = "watermark_path")
    private String watermarkPath;

    private String remake;

    private Date stamp;

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

    /**
     * @return watermark_name
     */
    public String getWatermarkName() {
        return watermarkName;
    }

    /**
     * @param watermarkName
     */
    public void setWatermarkName(String watermarkName) {
        this.watermarkName = watermarkName;
    }

    /**
     * @return watermark_path
     */
    public String getWatermarkPath() {
        return watermarkPath;
    }

    /**
     * @param watermarkPath
     */
    public void setWatermarkPath(String watermarkPath) {
        this.watermarkPath = watermarkPath;
    }

    /**
     * @return remake
     */
    public String getRemake() {
        return remake;
    }

    /**
     * @param remake
     */
    public void setRemake(String remake) {
        this.remake = remake;
    }

    /**
     * @return stamp
     */
    public Date getStamp() {
        return stamp;
    }

    /**
     * @param stamp
     */
    public void setStamp(Date stamp) {
        this.stamp = stamp;
    }
}