package my.asoul.takeout_server.service.staff.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import my.asoul.takeout.model.base.BaseResponse;
import my.asoul.takeout.model.staff.Employee;
import my.asoul.takeout.model.staff.EmployeeVO;
import my.asoul.takeout_server.mapper.staff.EmployeeMapper;
import my.asoul.takeout_server.service.staff.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * @author 4512
 * @date 2022/9/13 22:28
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public BaseResponse<String> saveEmployee(Employee employee) {
        return employeeMapper.insert(employee) == 1 ? BaseResponse.success("success") : BaseResponse.error("Add failed");

    }

    @Override
    @Cacheable(value = "employee", key = "#canteenId+#currentPage+#pageSize")
    public BaseResponse<Page<Employee>> page(long canteenId, int currentPage, int pageSize, String name) {
        Page<Employee> page = new Page<>(currentPage, pageSize);
        LambdaQueryWrapper<Employee> lqw = new LambdaQueryWrapper<>();
        lqw.eq(true,Employee::getCanteenId,canteenId);
        if (name != null) {
            lqw.like(StringUtils.hasLength(name), Employee::getName, name);
            lqw.or(true);
            lqw.like(StringUtils.hasLength(name), Employee::getPhone, name);
            lqw.or(true);
            lqw.like(StringUtils.hasLength(name), Employee::getIdNumber, name);
        }
        return BaseResponse.success(employeeMapper.selectPage(page, lqw));
    }

    @Override
    public BaseResponse<EmployeeVO> queryById(Long id) {
        Employee employee = employeeMapper.selectById(id);
        EmployeeVO employeeVO = Convert.convert(EmployeeVO.class, employee);
        return Objects.isNull(employee) ? BaseResponse.error("user is null") : BaseResponse.success(employeeVO);
    }
}
