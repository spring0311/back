package cc.mrbird.febs.system.controller;

import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.entity.FebsResponse;
import cc.mrbird.febs.system.entity.Car;
import cc.mrbird.febs.system.entity.HtmlDispose;
import cc.mrbird.febs.system.entity.PictureDispose;
import cc.mrbird.febs.system.service.ICarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

/**
 * 图片处理控制层
 *
 * @author weiZiHao
 * @date 2020/12/25
 */
@RestController
public class PictureController extends BaseController {

    @Autowired
    private ICarService carService;

    @Autowired
    private HtmlDispose htmlDispose;

    @Autowired
    private PictureDispose pictureDispose;

    @Value("${image.url}")
    private String imgUrl;


    @Value("${images.url}")
    private String imgUrls;

    @RequestMapping("getImg/{carId}")
    public FebsResponse uploadPictures(Car car) throws IOException {
        Car dao = carService.getById(car.getCarId());
        /**
         * 生成图片模板
         */
        String html2img = htmlDispose.html2png(dao);
        /**
         * 添加印章
         */
        String imgName = UUID.randomUUID().toString() + ".png";
        pictureDispose.getFinalImg(html2img, imgName);
        this.carService.updatetongguo(car.getCarId().toString());
        Car cars=new Car();
        cars.setCarId(car.getCarId());
        cars.setTemplateadress(imgUrls+imgName);
        cars.setAudittime(new Date());
        carService.saveOrUpdate(cars);
        return new FebsResponse().success();
    }

}
