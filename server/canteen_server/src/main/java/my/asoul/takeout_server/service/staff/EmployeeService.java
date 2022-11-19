package my.asoul.takeout_server.service.staff;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import my.asoul.takeout.model.base.BaseResponse;
import my.asoul.takeout.model.staff.Employee;
import my.asoul.takeout.model.staff.EmployeeVO;

/**
 * @author 4512
 * @date 2022/9/13 22:27
 */
public interface EmployeeService extends IService<Employee>{


    BaseResponse<String> saveEmployee(Employee employee);

    /**
     * 分页
     *
     * @param currentPage 当前页
     * @param pageSize    内容数目
     * @return Page
     */
    BaseResponse<Page<Employee>> page(long canteenId,int currentPage, int pageSize, String name);

    /**
     * 获取员工信息
     *
     * @param id 员工id
     * @return
     */
    BaseResponse<EmployeeVO> queryById(Long id);
}
