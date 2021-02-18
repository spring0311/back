package cc.mrbird.febs.system.entity;


import cc.mrbird.febs.system.service.ICarService;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

/**
 * 图片处理
 *
 * @author weiZiHao
 * @date 2020/12/23
 */
@Component
public class PictureDispose {
    @Autowired
    private static IService<Car> carService;
    @Value("${image.url}")
    private String imgUrl;

    public void getFinalImg(String oldName, String newName) {
        //模板图片 html2img 的图片路径
        String tiandi = imgUrl + oldName;
        //印章图片路径
        String haitu = imgUrl + "thisone.png";
        //处理后保存的路径
        String desImage = imgUrl + newName;
        //拼接方法
        overlapImage(tiandi, haitu, desImage);
        File file = new File(tiandi);
        file.delete();
    }

    private static void overlapImage(String backgroundPath, String frontgroudPath, String outPutPath) {
        try {

            //设置图片大小
//            BufferedImage background = ImageIO.read(new File(backgroundPath));
//            BufferedImage frontgroud = ImageIO.read(new File(frontgroudPath));
            BufferedImage background = resizeImagePng(800, 1130, ImageIO.read(new File(backgroundPath)));
            BufferedImage frontgroud = resizeImagePng(130, 70, ImageIO.read(new File(frontgroudPath)));

            //在背景图片中添加入需要写入的信息，
            Graphics2D g = background.createGraphics();

            //设置为透明覆盖
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 1.0f));
            //在背景图片上相框
            g.drawImage(frontgroud, 629, 217, frontgroud.getWidth(), frontgroud.getHeight(), null);
            g.drawImage(frontgroud, 629, 580, frontgroud.getWidth(), frontgroud.getHeight(), null);
            g.drawImage(frontgroud, 629, 937, frontgroud.getWidth(), frontgroud.getHeight(), null);

            g.dispose();
            //输出图片
            ImageIO.write(background, "png", new FileOutputStream(outPutPath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 重定义图片尺寸
     *
     * @param x
     * @param y
     * @param bfi
     * @return
     */
    private static BufferedImage resizeImagePng(int x, int y, BufferedImage bfi) {
        BufferedImage bufferedImage = new BufferedImage(x, y, BufferedImage.TYPE_INT_ARGB);
        bufferedImage.getGraphics().drawImage(
                bfi.getScaledInstance(x, y, Image.SCALE_SMOOTH), 0, 0, null);
        return bufferedImage;
    }

}
