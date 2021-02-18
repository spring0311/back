package cc.mrbird.febs.system.mapper;

import cc.mrbird.febs.system.entity.Car;
import cc.mrbird.febs.system.entity.Status;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author MrBird
 */
public interface CarMapper extends BaseMapper<Car> {

    /**
     * 查找车辆详细信息
     *
     * @param page 分页对象
     * @param car 用户对象，用于传递查询条件
     * @return Ipage
     */
    <T> IPage<Car> findUserDetailPage(Page<T> page, @Param("car") Car car);

    long countUserDetail(@Param("car") Car car);

    void updatebohui(String carId);

    List<Map<String, String>>  countstatus(String  countValue);

    long statuscountdetail();
    void updatetongguo(String carId);
}
