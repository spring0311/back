package cc.mrbird.febs.system.controller;

import cc.mrbird.febs.common.annotation.ControllerEndpoint;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.entity.FebsResponse;
import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.system.entity.Car;
import cc.mrbird.febs.system.entity.Status;
import cc.mrbird.febs.system.service.ICarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author MrBird
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("car")
public class CarController extends BaseController {

    private final ICarService carService;

    @GetMapping("list")
    @RequiresPermissions("recordsgenerated:view")
    public FebsResponse userList(Car car, QueryRequest request) {
        Map<String, Object> dataTable = getDataTable(this.carService.findUserDetailList(car, request));
        return new FebsResponse().success().data(dataTable);
    }
    @GetMapping("statuscount")
    public FebsResponse statuscount(Car car, QueryRequest request) {
        Long    counts=this.carService.statuscount();
        return new FebsResponse().success().data(counts);
    }
    @GetMapping("countstatus")
    public FebsResponse countstatus(String  countValue) {
        List<Map<String, String>>  count =this.carService.countstatus(countValue);
        return new FebsResponse().success().data(count);
    }
    /**
     * 下載圖片
     */
    @RequestMapping("downloadPictures/{carId}")
    public FebsResponse downloadPictures(Car car){
        Car ccar =this.carService.getById(car.getCarId());
        String picturename=ccar.getTemplateadress();
        return new FebsResponse().success().data(ccar);
    }

    @GetMapping("bohui/{carId}")
    @ControllerEndpoint(operation = "驳回数据", exceptionMessage = "驳回数据失败")
    public FebsResponse bohui(@NotBlank(message = "{required}") @PathVariable String carId) {
        this.carService.updatebohui(carId);
        return new FebsResponse().success();
    }
    @GetMapping("tongguo/{carId}")
    @ControllerEndpoint(operation = "审批数据", exceptionMessage = "审批数据失败")
    public FebsResponse tongguo(@NotBlank(message = "{required}") @PathVariable String carId) {
        this.carService.updatetongguo(carId);
        return new FebsResponse().success();
    }

    @PostMapping("update")
    @ControllerEndpoint(operation = "修改数据", exceptionMessage = "修改数据失败")
    public FebsResponse updateUser(@Valid Car car) {
        if (car.getCarId() == null) {
            throw new FebsException("ID为空");
        }
        this.carService.updateCar(car);
        return new FebsResponse().success();
    }

}
