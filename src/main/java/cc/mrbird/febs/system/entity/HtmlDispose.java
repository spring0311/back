package cc.mrbird.febs.system.entity;

import com.openhtmltopdf.swing.Java2DRenderer;
import com.openhtmltopdf.util.FSImageWriter;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author weiZiHao
 * @date 2020/12/23
 */
@Component
public class HtmlDispose {

    private String html = "";

    @Value("${image.url}")
    private String imgUrl;

    //AtomicLong atomicLong = new AtomicLong(10000000l);

    /**
     * 传入车辆信息 返回图片名称
     *
     * @param tCar
     * @return
     * @throws IOException
     */
    public String html2png(Car tCar) throws IOException {
        String html = getHtmlStr(tCar);
        //string转为inputstream流
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(html.getBytes());
        //模板路径
        File targetFile = ResourceUtils.getFile(imgUrl + "picture.html");
        //inputstream流转为file
        FileUtils.copyInputStreamToFile(byteArrayInputStream, targetFile);
        //通过openhtmltopdf工具生成图片
        final Java2DRenderer renderer = new Java2DRenderer(targetFile, 800, 1130);
        final BufferedImage img = renderer.getImage();
        final FSImageWriter imageWriter = new FSImageWriter();
        imageWriter.setWriteCompressionQuality(0.9f);
        String imageName = UUID.randomUUID().toString() + ".png";
        //生成图片存储路径
        imageWriter.write(img, imgUrl + imageName);//输出路径
        return imageName;
    }

    /**
     * html放入参数
     *
     * @param tCar
     * @return
     */
    private String getHtmlStr(Car tCar) {
        //车属单位
        String depname = tCar.getDepname();
        //车架号
        String framenumber = tCar.getFramenumber();
        //牌号
        String carno = tCar.getCarno();
        //平台隶属
        String whereFrom = "平台隶属:南京天朗电子科技有限公司山东分公司<br/>" + getNow();
        //编号 8位数
        String number = tCar.getCarId().toString();
        String htmlStr = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\"/>\n" +
                "    <title>文本图片</title>\n" +
                "    <style>\n" +
                "\n" +
                "        .one-t td {\n" +
                "            border: solid black;\n" +
                "            border-width: 1px 1px 1px 1px;\n" +
                "            height: 30px;\n" +
                "        }\n" +
                "\n" +
                "        .two-t td {\n" +
                "            border: solid #216897;\n" +
                "            border-width: 1px 1px 1px 1px;\n" +
                "            height: 30px;\n" +
                "        }\n" +
                "\n" +
                "        .three-t td {\n" +
                "            border: solid #A64E00;\n" +
                "            border-width: 1px 1px 1px 1px;\n" +
                "            height: 30px;\n" +
                "        }\n" +
                "\n" +
                "        .remove td {\n" +
                "            border-top: 0px;\n" +
                "        }\n" +
                "\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div style=\"width: 794px;height: 1123px; font-weight: 700 \">\n" +
                "    <div style=\"height: 40px; width: 600px\"></div>\n" +
                "    <!--第一份-->\n" +
                "    <div style=\"height: 320px;width: 750px;margin-right: auto;margin-left: auto\">\n" +
                "        <div>\n" +
                "            <div style=\"text-align: center;font-size: 18px;width: 750px;font-weight: 700\">全国道路货运车辆公共监督与服务平台</div>\n" +
                "        </div>\n" +
                "        <div>\n" +
                "            <div style=\"text-align: center;font-size: 19px\">入网证明</div>\n" +
                "            <div style=\"text-align: right;width: 720px\">编号:" + number + "</div>\n" +
                "        </div>\n" +
                "        <div style=\"text-align: center\">\n" +
                "            <div style=\"width: 10px;float: right;font-size: 10px\">\n" +
                "                <div style=\"height: 30px\"></div>\n" +
                "                第一联\n" +
                "                <div style=\"height: 15px\"></div>\n" +
                "                存根联\n" +
                "            </div>\n" +
                "            <table style=\"width: 730px;float: left; border-bottom: 0px; border: black;font-size: 12px;border-collapse: collapse\"\n" +
                "                   class=\"one-t\"\n" +
                "                   border=\"1px solid black\"\n" +
                "                   cellspacing=\"0\" cellpadding=\"0\">\n" +
                "                <tr>\n" +
                "                    <td rowspan=\"3\" style=\"width: 20px\">车辆信息</td>\n" +
                "                    <td width=\"132px\" height=\"50px\">车属单位</td>\n" +
                "                    <td colspan=\"4\">" + depname + "</td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                    <td>车架号码<br/>(VIN)</td>\n" +
                "                    <td width=\"200px\">" + framenumber + "</td>\n" +
                "                    <td rowspan=\"2\" width=\"100px\">车辆牌照</td>\n" +
                "                    <td width=\"100px\">牌号</td>\n" +
                "                    <td>" + carno + "</td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                    <td>车辆类别</td>\n" +
                "                    <td>大型汽车</td>\n" +
                "                    <td>颜色</td>\n" +
                "                    <td>黄色</td>\n" +
                "                </tr>\n" +
                "            </table>\n" +
                "            <table style=\"width: 730px;float: left; border-bottom: 1px; border: black;font-size: 12px;border-collapse: collapse\"\n" +
                "                   class=\"one-t remove\"\n" +
                "                   border=\"1px solid black\"\n" +
                "                   cellspacing=\"0\" cellpadding=\"0\">\n" +
                "                <tr>\n" +
                "                    <td rowspan=\"3\" width=\"153px\" align=\"left\" valign=\"top\" style=\"height: 80px\">操作人签字:</td>\n" +
                "                    <td rowspan=\"3\" width=\"200px\" align=\"left\" valign=\"top\">领取人签字:</td>\n" +
                "                    <td width=\"100px\" style=\"height: 30px\">\n" +
                "                        联网联控状态\n" +
                "                    </td>\n" +
                "                    <td>正常</td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                    <td colspan=\"2\" rowspan=\"2\">" + whereFrom + "</td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                </tr>\n" +
                "            </table>\n" +
                "        </div>\n" +
                "        <div style=\"width: 730px\">\n" +
                "            <div style=\"width :620px;float: left;font-size: 11px\">\n" +
                "                备注:泰赛电子有限公司监制。地址：泰山区大白峪汽车城运丰大酒店泰赛北斗2楼219室。联系电话：0538-8337775 未加\n" +
                "                盖本公司防伪公章，均为仿造本公司的假入网证明！举报核实有奖。伪造开局本公司入网证明，给您带来设备无法\n" +
                "                正常使用，造成经济纠纷，严重侵害您的权力！\n" +
                "            </div>\n" +
                "            <div style=\"font-size: 11px;float: right\">以上信息 手写无效</div>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "    <div>\n" +
                "        <div style=\"float: left\"><img style=\"width: 15px;height: 15px;\"\n" +
                "                                      src=\"http://shiji.app12345.cn/picture/jiandao.png\"/></div>\n" +
                "        <div style=\"float: right;width: 777px\">\n" +
                "            <hr style=\"border : 1px dashed black;\"/>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "    <!--二-->\n" +
                "    <div style=\"height: 40px; width: 600px\"></div>\n" +
                "    <div style=\"height: 320px;width: 750px;margin-right: auto;margin-left: auto;color: #216897\">\n" +
                "        <div>\n" +
                "            <div style=\"text-align: center;font-size: 18px;width: 750px;font-weight: 700\">全国道路货运车辆公共监督与服务平台</div>\n" +
                "        </div>\n" +
                "        <div>\n" +
                "            <div style=\"text-align: center;font-size: 19px\">入网证明</div>\n" +
                "            <div style=\"text-align: right;width: 720px\">编号:" + number + "</div>\n" +
                "        </div>\n" +
                "        <div style=\"text-align: center\">\n" +
                "            <div style=\"width: 10px;float: right;font-size: 10px\">\n" +
                "                <div style=\"height: 30px\"></div>\n" +
                "                第二联\n" +
                "                <div style=\"height: 15px\"></div>\n" +
                "                企业留存\n" +
                "            </div>\n" +
                "            <table style=\"width: 730px;float: left; border-bottom: 1px; border: #216897;font-size: 12px;border-collapse: collapse\"\n" +
                "                   class=\"two-t\"\n" +
                "                   border=\"1px solid #216897\"\n" +
                "                   cellspacing=\"0\" cellpadding=\"0\">\n" +
                "                <tr>\n" +
                "                    <td rowspan=\"3\" style=\"width: 20px\">车辆信息</td>\n" +
                "                    <td width=\"132px\" height=\"50px\">车属单位</td>\n" +
                "                    <td colspan=\"4\">" + depname + "</td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                    <td>车架号码<br/>(VIN)</td>\n" +
                "                    <td width=\"200px\">" + framenumber + "</td>\n" +
                "                    <td rowspan=\"2\" width=\"100px\">车辆牌照</td>\n" +
                "                    <td width=\"100px\">牌号</td>\n" +
                "                    <td>" + carno + "</td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                    <td>车辆类别</td>\n" +
                "                    <td>大型汽车</td>\n" +
                "                    <td>颜色</td>\n" +
                "                    <td>黄色</td>\n" +
                "                </tr>\n" +
                "            </table>\n" +
                "            <table style=\"width: 730px;float: left; border-bottom: 1px; border: #216897;font-size: 12px;border-collapse: collapse\"\n" +
                "                   class=\"two-t remove\"\n" +
                "                   border=\"1px solid #216897\"\n" +
                "                   cellspacing=\"0\" cellpadding=\"0\">\n" +
                "                <tr>\n" +
                "                    <td rowspan=\"3\" width=\"153px\" align=\"left\" valign=\"top\" style=\"height: 80px\">操作人签字:</td>\n" +
                "                    <td rowspan=\"3\" width=\"200px\" align=\"left\" valign=\"top\">领取人签字:</td>\n" +
                "                    <td width=\"100px\" style=\"height: 30px\">\n" +
                "                        联网联控状态\n" +
                "                    </td>\n" +
                "                    <td>正常</td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                    <td colspan=\"2\" rowspan=\"2\">" + whereFrom + "</td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                </tr>\n" +
                "            </table>\n" +
                "        </div>\n" +
                "        <div style=\"width: 730px\">\n" +
                "            <div style=\"width :620px;float: left;font-size: 11px\">\n" +
                "                备注:泰赛电子有限公司监制。地址：泰山区大白峪汽车城运丰大酒店泰赛北斗2楼219室。联系电话：0538-8337775 未加\n" +
                "                盖本公司防伪公章，均为仿造本公司的假入网证明！举报核实有奖。伪造开局本公司入网证明，给您带来设备无法\n" +
                "                正常使用，造成经济纠纷，严重侵害您的权力！\n" +
                "            </div>\n" +
                "            <div style=\"font-size: 11px;float: right\">以上信息 手写无效</div>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "    <div></div>\n" +
                "    <div>\n" +
                "        <div style=\"float: left\"><img style=\"width: 15px;height: 15px;\"\n" +
                "                                      src=\"http://shiji.app12345.cn/picture/jiandao.png\"/></div>\n" +
                "        <div style=\"float: right;width: 777px\">\n" +
                "            <hr style=\"border : 1px dashed black;\"/>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "    <!--三-->\n" +
                "    <div style=\"height: 40px; width: 600px\"></div>\n" +
                "    <div style=\"height: 320px;width: 750px;margin-right: auto;margin-left: auto;color: #A64E00\">\n" +
                "        <div>\n" +
                "            <div style=\"text-align: center;font-size: 18px;width: 750px;font-weight: 700\">全国道路货运车辆公共监督与服务平台</div>\n" +
                "        </div>\n" +
                "        <div>\n" +
                "            <div style=\"text-align: center;font-size: 19px\">入网证明</div>\n" +
                "            <div style=\"text-align: right;width: 720px\">编号:" + number + "</div>\n" +
                "        </div>\n" +
                "        <div style=\"text-align: center\">\n" +
                "            <div style=\"width: 10px;float: right;font-size: 10px\">\n" +
                "                <div style=\"height: 30px\"></div>\n" +
                "                第三联\n" +
                "                <div style=\"height: 15px\"></div>\n" +
                "                主管单位留存\n" +
                "            </div>\n" +
                "            <table style=\"width: 730px;float: left; border-bottom: 1px; border: #A64E00;font-size: 12px;border-collapse: collapse\"\n" +
                "                   class=\"three-t\"\n" +
                "                   border=\"1px solid #A64E00\"\n" +
                "                   cellspacing=\"0\" cellpadding=\"0\">\n" +
                "                <tr>\n" +
                "                    <td rowspan=\"3\" style=\"width: 20px\">车辆信息</td>\n" +
                "                    <td width=\"132px\" height=\"50px\">车属单位</td>\n" +
                "                    <td colspan=\"4\">" + depname + "</td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                    <td>车架号码<br/>(VIN)</td>\n" +
                "                    <td width=\"200px\">" + framenumber + "</td>\n" +
                "                    <td rowspan=\"2\" width=\"100px\">车辆牌照</td>\n" +
                "                    <td width=\"100px\">牌号</td>\n" +
                "                    <td>" + carno + "</td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                    <td>车辆类别</td>\n" +
                "                    <td>大型汽车</td>\n" +
                "                    <td>颜色</td>\n" +
                "                    <td>黄色</td>\n" +
                "                </tr>\n" +
                "            </table>\n" +
                "            <table style=\"width: 730px;float: left; border-bottom: 1px; border: #A64E00;font-size: 12px;border-collapse: collapse\"\n" +
                "                   class=\"three-t remove\"\n" +
                "                   border=\"1px solid #A64E00\"\n" +
                "                   cellspacing=\"0\" cellpadding=\"0\">\n" +
                "                <tr>\n" +
                "                    <td rowspan=\"3\" width=\"153px\" align=\"left\" valign=\"top\" style=\"height: 80px\">操作人签字:</td>\n" +
                "                    <td rowspan=\"3\" width=\"200px\" align=\"left\" valign=\"top\">领取人签字:</td>\n" +
                "                    <td width=\"100px\" style=\"height: 30px\">\n" +
                "                        联网联控状态\n" +
                "                    </td>\n" +
                "                    <td>正常</td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                    <td colspan=\"2\" rowspan=\"2\">" + whereFrom + "</td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                </tr>\n" +
                "            </table>\n" +
                "        </div>\n" +
                "        <div style=\"width: 730px\">\n" +
                "            <div style=\"width :620px;float: left;font-size: 11px\">\n" +
                "                备注:泰赛电子有限公司监制。地址：泰山区大白峪汽车城运丰大酒店泰赛北斗2楼219室。联系电话：0538-8337775 未加\n" +
                "                盖本公司防伪公章，均为仿造本公司的假入网证明！举报核实有奖。伪造开局本公司入网证明，给您带来设备无法\n" +
                "                正常使用，造成经济纠纷，严重侵害您的权力！\n" +
                "            </div>\n" +
                "            <div style=\"font-size: 11px;float: right\">以上信息 手写无效</div>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>";
        return htmlStr;
    }

    private String getNow() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        return simpleDateFormat.format(date);
    }


}
