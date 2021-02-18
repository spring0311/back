package cc.mrbird.febs.system.service.impl;

import cc.mrbird.febs.common.entity.FebsConstant;
import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.system.entity.*;
import cc.mrbird.febs.system.mapper.CarMapper;
import cc.mrbird.febs.system.service.ICarService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author MrBird
 */
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class CarServiceImpl extends ServiceImpl<CarMapper, Car> implements ICarService {

    @Override
    public IPage<Car> findUserDetailList(Car car, QueryRequest request) {

        Page<Car> page = new Page<>(request.getPageNum(), request.getPageSize());
        page.setSearchCount(false);
        page.setTotal(baseMapper.countUserDetail(car));
        SortUtil.handlePageSort(request, page, "carId", FebsConstant.ORDER_ASC, false);
        return this.baseMapper.findUserDetailPage(page, car);
    }

    @Override
    public void updatebohui(String carId) {
         this.baseMapper.updatebohui(carId);
    }
    @Override
    public void updatetongguo(String carId) {
        this.baseMapper.updatetongguo(carId);
    }

    @Override
    public Long statuscount() {
        return this.baseMapper.statuscountdetail();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCar(Car car) {
        car.setModifyTime(new Date());
        updateById(car);
    }

    @Override
    public List<Map<String, String>>   countstatus(String countValue) {
        return this.baseMapper.countstatus(countValue);
    }


}
