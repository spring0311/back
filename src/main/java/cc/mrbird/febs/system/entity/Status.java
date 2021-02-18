package cc.mrbird.febs.system.entity;


import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

public class Status implements Serializable {



    /**
     * 次数
     */
    @TableField(exist = false)
    private String count;

    /**
     * status
     */
    @TableField(exist = false)
    private String status;



}
