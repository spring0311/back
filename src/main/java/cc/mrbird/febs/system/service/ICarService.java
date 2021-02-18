package cc.mrbird.febs.system.service;

import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.system.entity.Car;
import cc.mrbird.febs.system.entity.Status;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author MrBird
 */
public interface ICarService extends IService<Car> {


    /**
     * 查找车辆详细信息
     *
     * @param request request
     * @param car    用户对象，用于传递查询条件
     * @return IPage
     */
    IPage<Car> findUserDetailList(Car car, QueryRequest request);

    void updatebohui(String carId);

    void updateCar(Car car);

    List<Map<String, String>>  countstatus(String countValue);

    void updatetongguo(String carId);

    Long statuscount();
}
