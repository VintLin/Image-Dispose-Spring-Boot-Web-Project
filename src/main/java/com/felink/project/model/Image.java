package com.felink.project.model;

import java.util.Date;
import javax.persistence.*;

public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "image_name")
    private String imageName;

    @Column(name = "image_path")
    private String imagePath;

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
     * @return image_name
     */
    public String getImageName() {
        return imageName;
    }

    /**
     * @param imageName
     */
    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    /**
     * @return image_path
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * @param imagePath
     */
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
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