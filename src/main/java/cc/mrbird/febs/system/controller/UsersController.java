package cc.mrbird.febs.system.controller;

import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.entity.FebsResponse;
import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.system.entity.User;
import cc.mrbird.febs.system.entity.Users;
import cc.mrbird.febs.system.service.IUserService;
import cc.mrbird.febs.system.service.IUsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
/**
 * @author MrBird
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("userslist")
public class UsersController   extends BaseController {
    private final IUsersService usersService;
    @GetMapping("list")
    @RequiresPermissions("userslist:view")
    public FebsResponse userList(Users users, QueryRequest request) {
        Map<String, Object> dataTable = getDataTable(this.usersService.findUserDetailList(users, request));
        return new FebsResponse().success().data(dataTable);
    }

}
