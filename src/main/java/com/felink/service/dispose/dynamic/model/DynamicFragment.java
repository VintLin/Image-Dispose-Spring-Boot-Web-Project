package com.felink.service.dispose.dynamic.model;

import com.felink.service.common.model.*;
import com.felink.service.common.utility.FileUtil;
import com.felink.service.common.utility.ScanningLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * 单帧处理
 * @author linwentao
 * @see com.felink.service.dispose.dynamic.dynamic.DynamicControler
 */
public class DynamicFragment implements Runnable{
    static private Logger logger = LoggerFactory.getLogger(DynamicFragment.class);
    static private Set<BaseTriangle> fixTriangles;
    static private Set<BaseTriangle> motionTriangles;
    static private BufferedImage originImage;

    private BufferedImage backgroundImage;
    private BufferedImage foregroundImage;
    private List<BaseVector> foregroundVector = new ArrayList<>();
    private List<BaseVector> backgroundVector = new ArrayList<>();
    private double alpha;
    private String savePath;


    /**
     * 初始化所有静态变量
     * @param fixTriangles 图层中不变的三角区域
     * @param motionTriangles 图层中想要形变的三角区域
     * @param image 初始图片
     */
    public static void initFragment(Set<BaseTriangle> fixTriangles, Set<BaseTriangle> motionTriangles, BufferedImage image) {
        DynamicFragment.fixTriangles = fixTriangles;
        DynamicFragment.motionTriangles = motionTriangles;
        originImage = image;
    }

    /**
     * 初始化帧
     * @param saveFilePath 文件保存路径
     * @param transparent 前景与背景混合比例
     */
    public DynamicFragment(String saveFilePath, double transparent) {
        savePath = saveFilePath;
        alpha = transparent;
    }

    /**
     * 添加形变路径
     * @param foreVector 前景图的形变路径
     * @param backVector 背景图的形变路径
     */
    public void addVector(BaseVector foreVector, BaseVector backVector) {
        foregroundVector.add(foreVector);
        backgroundVector.add(backVector);
    }

    /**
     * 处理帧图片并保存
     */
    @Override
    public void run() {
        initLayer(); // 初始化前景与背景图层
        if(alpha == 1) { // 当alpha为1时前景不与背景叠加, 故只处理前景图层
            dynamicLayer(foregroundVector, backgroundImage);
        } else if(alpha == 0) { // 当alpha为0时背景不与前景叠加,故只处理背景图层
            dynamicLayer(backgroundVector, backgroundImage);
        } else { // 处理前景图层以及背景图层,并依据alpha值按比例叠加
            dynamicLayer(foregroundVector, foregroundImage);
            dynamicLayer(backgroundVector, backgroundImage);
            overlayLayer(foregroundImage, backgroundImage);
        }
        maskFixLayer(backgroundImage); // 遮罩不变区域
        saveImage(backgroundImage); // 处理完毕,保存图片
    }

    /**
     * 处理并形变图层
     * @param vectors 形变路径
     * @param image 需要处理的图层
     */
    private void dynamicLayer(List<BaseVector> vectors, BufferedImage image) {
        for(BaseTriangle originTriangle: motionTriangles) {
            BaseTriangle transformTriangle = originTriangle.clone();
            for(BaseVector vector: vectors){
                transformTriangle.replace(vector.getFrom(), vector.getTo());
            }
            Matrix3D matrix = new Matrix3D(originTriangle, transformTriangle);
            ScanningLine lines = new ScanningLine(transformTriangle.getVertex());
            for (int array[] : lines.getIndex()) {
                if(array[0] > 0 && array[0] < image.getHeight()) {
                    for (int x = array[1]; x <= array[2]; x++) {
                        if (x > 0 && x < image.getWidth()) {
                            image.setRGB(x, array[0], tackColor(matrix.transform(x, array[0])));
//                            if (x == array[1] || x == array[2] - 1 ) {
//                                image.setRGB(x, array[0], 0x00aaff);
//                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 初始化前景图层以及背景图层
     */
    private synchronized void initLayer() {
        foregroundImage = new BufferedImage(originImage.getWidth(), originImage.getHeight(), BufferedImage.TYPE_INT_RGB);
        backgroundImage = new BufferedImage(originImage.getWidth(), originImage.getHeight(), BufferedImage.TYPE_INT_RGB);
    }

    /**
     * 保存图片
     * @param image 需要保存的图片
     */
    private void saveImage(BufferedImage image) {
        try {
            FileUtil.mkdirs(savePath);
            ImageIO.write(image, FileUtil.getFileSuffix(savePath), new File(savePath));
            foregroundImage = null;
            backgroundImage = null;
            foregroundVector = null;
            backgroundVector = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 按比例叠加两图层, 呈现情景图层逐渐透明的效果
     * @param foreground 前景图层
     * @param background 背景图层
     */
    private void overlayLayer(BufferedImage foreground, BufferedImage background) {
        for(int y = 0; y < originImage.getHeight(); y++) {
            for(int x = 0; x < originImage.getWidth(); x++) {
                int back = background.getRGB(x, y);
                int fore = foreground.getRGB(x, y);
                backgroundImage.setRGB(x, y, overlayColor(fore, back, alpha));
            }
        }
    }

    /**
     * 按比例叠加两色
     * @param foregroundColor 前景色
     * @param backgroundColor 背景色
     * @param alpha 比例
     * @return RGB
     */
    private int overlayColor(int foregroundColor, int backgroundColor, double alpha) {
        Color f = new Color(foregroundColor);
        Color b = new Color(backgroundColor);
        int R = checkScope((int)(f.getRed() * alpha + b.getRed() * (1 - alpha)), 255, 0);
        int G = checkScope((int)(f.getGreen() * alpha + b.getGreen() * (1 - alpha)), 255, 0);
        int B = checkScope((int)(f.getBlue() * alpha + b.getBlue() * (1 - alpha)), 255, 0);
        return new Color(R, G, B).getRGB();
    }

    /**
     * 遮罩不变的区域
     * @param image 被遮罩图层
     */
    private void maskFixLayer(BufferedImage image) {
        for(BaseTriangle fixTriangle: fixTriangles) {
            ScanningLine lines = new ScanningLine(fixTriangle.getVertex());
            for (int array[] : lines.getIndex()) {
                if(array[0] > 0 && array[0] < image.getHeight())
                    for (int x = array[1]; x <= array[2]; x++) {
                        if(x > 0 && x < image.getWidth())
                            image.setRGB(x, array[0], originImage.getRGB(x, array[0]));
                    }
            }
        }
    }

    /**
     * 获取带小数坐标颜色
     * @param coord 坐标
     * @return RGB
     */
    private int tackColor(double[] coord) {return tackColor(coord[0], coord[1]); }

    /**
     * 由于矩阵变换计算出的坐标大多为浮点型,
     * 为了让图层形变自然需根据该坐标按比例
     * 叠加其周边整型坐标的颜色。
     * @param x x
     * @param y y
     * @return RGB
     */
    private int tackColor(double x, double y) {
        int floorX = checkScope((int)x, originImage.getWidth() - 1, 1);
        int topX = checkScope(floorX + 1, originImage.getWidth() - 1, 1);
        int floorY = checkScope((int)y, originImage.getHeight() - 1, 1);
        int topY = checkScope(floorY + 1, originImage.getHeight() - 1, 1);
        double ratioX = x - floorX;
        double ratioY = y - floorY;

        Color ff = new Color(originImage.getRGB(floorX, floorY));
        Color tf = new Color(originImage.getRGB(topX, floorY));
        Color ft = new Color(originImage.getRGB(floorX, topY));
        Color tt = new Color(originImage.getRGB(topX, topY));

        int R = calculateColor(ff.getRed(), tf.getRed(), ft.getRed(), tt.getRed(), ratioX, ratioY);
        int G = calculateColor(ff.getGreen(), tf.getGreen(), ft.getGreen(), tt.getGreen(), ratioX, ratioY);
        int B = calculateColor(ff.getBlue(), tf.getBlue(), ft.getBlue(), tt.getBlue(), ratioX, ratioY);

        return new Color(checkScope(R, 255, 0), checkScope(G, 255, 0), checkScope(B, 255, 0)).getRGB();
    }

    /**
     * 获取叠加后的RGB值
     * @param ff 向下取整x 向下取整y 对应RGB值
     * @param tf 向上取整x 向下取整y 对应RGB值
     * @param ft 向下取整x 向上取整y 对应RGB值
     * @param tt 向上取整x 向上取整y 对应RGB值
     * @param ratioX 左右RGB叠加比例
     * @param ratioY 上下RGB叠加比例
     * @return RGB
     */
    private int calculateColor(int ff, int tf, int ft, int tt, double ratioX, double ratioY){
        double c1 = (ff + ratioX * (tf - ff));
        double c2 = (ft + ratioX * (tt - ft));
        return (int)(c1 + ratioY * (c2 - c1));
    }

    /**
     * 检测是否超出边界
     * @param value 值
     * @param max 限定最大值
     * @param min 限定最小值
     * @return value
     */
    private int checkScope(int value, int max, int min){
        return value > max? max: value < min? min: value;
    }
}
