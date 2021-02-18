package cc.mrbird.febs.system.service;

import cc.mrbird.febs.common.entity.FebsConstant;
import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.system.entity.User;
import cc.mrbird.febs.system.entity.Users;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.scheduling.annotation.Async;

/**
 * @author MrBird
 */
public interface IUsersService extends IService<Users> {
     /**
     * 查找用户详细信息
     *
     * @param request request
     * @param users    用户对象，用于传递查询条件
     * @return IPage
     */
    IPage<Users> findUserDetailList(Users users, QueryRequest request);
}
