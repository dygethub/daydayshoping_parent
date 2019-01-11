package org.dengyu.daydayshopping.controller;

import org.dengyu.daydayshopping.domain.Employee;
import org.dengyu.daydayshopping.util.AjaxResult;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {
    @RequestMapping(value = "/login",method= RequestMethod.POST)
    public AjaxResult login(@RequestBody Employee employee){
        if("admin".equals(employee.getUsername())&&"admin".equals(employee.getPassword())){
            return AjaxResult.getAjaxResult().setResultObject(employee);
        }
        return AjaxResult.getAjaxResult().setSuccess(false).setMsg("用户名错误");
    }
}
