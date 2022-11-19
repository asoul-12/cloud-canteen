package my.asoul.takeout_server.controller.Administor;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import my.asoul.takeout.model.base.BaseResponse;
import my.asoul.takeout.model.staff.Employee;
import my.asoul.takeout.model.staff.EmployeeVO;
import my.asoul.takeout_server.service.staff.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * @author 4512
 * @date 2022/9/13 22:30
 */
@Slf4j
@RestController
@RequestMapping("/staff")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * 根据员工account获取员工信息
     *
     * @param id 员工id
     * @return 员工
     */
    @GetMapping("/{id}")
    public BaseResponse<EmployeeVO> query(@PathVariable("id") Long id) {
        return employeeService.queryById(id);
    }

    /**
     * 条件分页查询
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/{canteenId}/{currentPage}/{pageSize}/{name}")
    public BaseResponse<Page<Employee>> pageByQuery(
            @PathVariable("canteenId") long canteenId,
            @PathVariable("currentPage") int page,
            @PathVariable("pageSize") int pageSize,
            @PathVariable("name") String name) {
        return employeeService.page(canteenId,page, pageSize, name);
    }

    /**
     * 获取分页信息
     *
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/{canteenId}/{currentPage}/{pageSize}")
    public BaseResponse<Page<Employee>> page(
            @PathVariable("canteenId") long canteenId,
            @PathVariable("currentPage") int page,
            @PathVariable("pageSize") int pageSize) {
        return employeeService.page(canteenId, page, pageSize, null);
    }

    /**
     * 添加员工
     *
     * @param employee
     * @return
     */
    @PostMapping
    public BaseResponse<String> save(@RequestBody @Validated Employee employee) {
        return employeeService.saveEmployee(employee);
    }

    /**
     * 修改员工信息
     *
     * @param employee
     * @return
     */
    @PutMapping
    public BaseResponse<String> update(@RequestBody @Validated Employee employee) {
        return employeeService.updateById(employee) ? BaseResponse.success("success") : BaseResponse.error("Modification failed");
    }


    /**
     * 批量删除
     *
     * @param ids staffId
     * @return
     */
    @PutMapping("/batch-delete")
    public BaseResponse<String> batchDelete(@RequestBody ArrayList<Long> ids) {
        return employeeService.removeBatchByIds(ids) ? BaseResponse.success(null) : BaseResponse.error("delete fail");
    }

}
