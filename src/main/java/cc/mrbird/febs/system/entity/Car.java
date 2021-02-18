package cc.mrbird.febs.system.entity;

import cc.mrbird.febs.common.annotation.Desensitization;
import cc.mrbird.febs.common.annotation.IsMobile;
import cc.mrbird.febs.common.converter.TimeConverter;
import cc.mrbird.febs.common.entity.DesensitizationType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * @author MrBird
 */
@Data
@TableName("t_car")
@Excel("车辆信息表")
public class Car implements Serializable {
    /**
     * ID
     */
    @TableId(value = "carid", type = IdType.AUTO)
    private Long carId;

    /**
     * 车牌号
     */
    @TableField("carno")
    private String carno;

    /**
     * 所属单位
     */
    @TableField("depname")
    private String depname;

    /**
     * 车架号码
     */
    @TableField("framenumber")
    private String framenumber;

    /**
     * 车辆类型
     */
    @TableField("cartype")
    private String cartype;

    /**
     * 车辆颜色
     */
    @TableField("colour")
    private String colour;

    /**
     * 审核状态
     */
    @TableField("status")
    private String status;

    /**
     *
     */
    @TableField("page1")
    private String page1;
    /**
     *
     */
    @TableField("page2")
    private String page2;
    /**
     *
     */
    @TableField("page3")
    private String page3;
    /**
     *
     */
    @TableField("page4")
    private String page4;


    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    @ExcelField(value = "创建时间", writeConverter = TimeConverter.class)
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField("MODIFY_TIME")
    @ExcelField(value = "修改时间", writeConverter = TimeConverter.class)
    private Date modifyTime;

    /**
     * 修改时间
     */
    @TableField("audittime")
    @ExcelField(value = "修改时间", writeConverter = TimeConverter.class)
    private Date audittime;


    /**
     *车主姓名
     */
    @TableField("czname")
    private String czname;

    /**
     *手机号
     */
    @TableField("phone")
    private String phone;

    /**
     *
     */
    @TableField("openid")
    private String openid;

    /**
     *
     */
    private String templateadress;

}
