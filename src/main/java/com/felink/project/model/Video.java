package com.felink.project.model;

import java.util.Date;
import javax.persistence.*;

public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "video_name")
    private String videoName;

    @Column(name = "video_path")
    private String videoPath;

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
     * @return video_name
     */
    public String getVideoName() {
        return videoName;
    }

    /**
     * @param videoName
     */
    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    /**
     * @return video_path
     */
    public String getVideoPath() {
        return videoPath;
    }

    /**
     * @param videoPath
     */
    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
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