package cc.mrbird.febs.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.wuwenze.poi.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author bianJingWei
 * @since 2021-02-19
 */
@Data
@TableName("t_number")
@Excel("编号表")
public class TNumber implements Serializable {

     @TableId(value = "number", type = IdType.NONE)
     private String number;


}
