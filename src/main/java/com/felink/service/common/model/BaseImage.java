package com.felink.service.common.model;

import com.felink.service.common.utility.FileUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 图片
 * 存储图片相关信息
 * 处理图片方法
 * @author linwentao
 * @see com.felink.service.dispose.dynamic.dynamic.DynamicControler
 * @see com.felink.service.dispose.transitions.transitions.AbstractTransitions
 * @see com.felink.service.dispose.effects.effects.AbstractEffects
 */
public class BaseImage {
    protected String inputPath;
    protected String savePath;
    protected String suffix;
    protected int width;
    protected int height;
    protected BufferedImage image;
    protected BufferedImage operateImage;

    /**
     * 初始化BaseImage,最终该实例将保存两个BufferedImage
     * 其中 image 为原图缓存。
     * operateImage 为供图片处理用图片缓存
     * @param inputPath 图片本地路径
     * @param savePath 图片处理完成后的保存文件的目录
     */
    public BaseImage(String inputPath, String savePath) {
        try {
            this.inputPath = inputPath;
            this.savePath = savePath;
            File imageFile = new File(inputPath);
            image = ImageIO.read(imageFile);
            loadImage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 用于拷贝已有的BaseImage
     * @param inputPath 图片本地路径
     * @param savePath 图片处理完成后的保存文件的目录
     * @param image BufferedImage
     */
    private BaseImage(String inputPath, String savePath, BufferedImage image) {
        this.inputPath = inputPath;
        this.savePath = savePath;
        this.image = deepCopy(image);
        loadImage();
    }

    /**
     * 实例初始化
     */
    private void loadImage(){
        operateImage = deepCopy(image);
        suffix = FileUtil.getFileSuffix(inputPath);
        width = image.getWidth();
        height = image.getHeight();
    }

    /**
     * 生成当前实例的备份
     * @return BaseImage备份
     */
    public BaseImage deepCopy() {
        return new BaseImage(inputPath, savePath, image);
    }

    /**
     * 以 index为文件名保存文件在 savePath目录下
     * 并重置 operateImage
     * @param index 文件名
     */
    public void saveImage(int index) {
        saveImageWithoutReset(index);
        resetImage();
    }

    /**
     * 重置operateImage
     */
    void resetImage() {
        operateImage = deepCopy(image);
    }

    /**
     * 对BufferedImage实例进行深拷贝,复制备份。
     * @param buffered BufferedImage实例
     * @return BufferedImage
     */
    public static BufferedImage deepCopy(BufferedImage buffered) {
        ColorModel cm = buffered.getColorModel();
        boolean isAlpha = cm.isAlphaPremultiplied();
        WritableRaster raster = buffered.copyData(null);
        return new BufferedImage(cm, raster, isAlpha, null);
    }

    /**
     * 同 saveImage 相同但不重置operateImage
     * @param index 文件名
     */
    public void saveImageWithoutReset(int index) {
        try {
            FileUtil.mkdirs(savePath);
            ImageIO.write(operateImage, suffix, new File(savePath + index + "." + suffix));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取图片四个顶点
     * @return List<BasePoint> 定点
     */
    public List<BasePoint> getVertices() {
        ArrayList<BasePoint> points = new ArrayList<>();
        points.add(new BasePoint(0, 0));
        points.add(new BasePoint(width, 0));
        points.add(new BasePoint(width, height));
        points.add(new BasePoint(0, height));
        return points;
    }

    /**
     * 将图片修剪为背景图片大小,并以
     * origin_(原文件名).(后缀)
     * 的形式保存在原目录下
     * @param imagePath 原图
     * @param backgroundPath 背景图片
     * @return 保存路径
     */
    public static String tileImage(String imagePath, String backgroundPath) {
        try {
            String path = FileUtil.getSubFilePath(imagePath);
            String fileName = FileUtil.getSubFileNameNoSuffix(imagePath);
            String fileSuffix = FileUtil.getFileSuffix(backgroundPath);
            String savePath = path + "origin_" + fileName + "." + fileSuffix;
            BufferedImage bufImage = ImageIO.read(new File(imagePath));
            BufferedImage backgroundImage = ImageIO.read(new File(backgroundPath));
            int ScalingWidth, ScalingHeight;
            int onWidth = bufImage.getWidth();
            int onHeight = bufImage.getHeight();
            int bgWidth = backgroundImage.getWidth();
            int bgHeight = backgroundImage.getHeight();
            double ratioW = bgWidth / (double) onWidth;
            double ratioH = bgHeight / (double) onHeight;
            if (ratioH > ratioW) {
                ScalingHeight = (int) (onHeight * ratioW);
                ScalingWidth = bgWidth;
            } else {
                ScalingHeight = bgHeight;
                ScalingWidth = (int) (onWidth * ratioH);
            }
            Graphics2D g = backgroundImage.createGraphics();
            g.drawImage(bufImage, (bgWidth - ScalingWidth) / 2, (bgHeight - ScalingHeight) / 2,
                    ScalingWidth, ScalingHeight, null);
            g.dispose();
            ImageIO.write(backgroundImage, fileSuffix, new File(savePath));
            return savePath;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 获取当前实例的image(BufferedImage)
     * @return image
     */
    public BufferedImage getImage() { return image; }

    /**
     * 设置当前实例的image(BufferedImage)
     * @param image image
     */
    public void setImage(BufferedImage image) { this.image = image; }

    /**
     * 获取当前实例的operateImage(BufferedImage)
     * @return operateImage
     */
    public BufferedImage getOperateImage() {return operateImage;}

    /**
     * 设置当前实例的operateImage(BufferedImage)
     * @param image operateImage
     */
    public void setOperateImage(BufferedImage image) { this.operateImage = image; }

    /**
     * 获取原图片文件路径
     * @return inputPath
     */
    public String getInputPath() { return inputPath; }

    /**
     * 获取图片保存目录
     * @return savePath
     */
    public String getSavePath() { return savePath; }

    public String getSaveFilePath(int index) { return savePath + index + "." + suffix;}

    public String getSuffix() {return suffix;}

    public int getWidth() { return width; }

    public int getHeight() { return height; }
}
