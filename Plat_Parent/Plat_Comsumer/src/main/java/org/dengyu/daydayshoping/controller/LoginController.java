package org.dengyu.daydayshoping.controller;

import org.dengyu.daydayshopping.domain.Employee;
import org.dengyu.daydayshopping.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class LoginController {
    @Autowired
    private RestTemplate restTemplate;
    public static final String URL="http://DAYDAYSHOPING-PLAT/login";
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public AjaxResult login(@RequestBody Employee employee){

        return restTemplate.postForObject(URL, employee, AjaxResult.class);
    }
}
