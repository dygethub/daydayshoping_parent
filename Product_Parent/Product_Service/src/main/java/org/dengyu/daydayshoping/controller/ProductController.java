package org.dengyu.daydayshoping.controller;

import org.dengyu.daydayshoping.domain.Product;
import org.dengyu.daydayshoping.domain.Specification;
import org.dengyu.daydayshoping.query.ProductQuery;
import org.dengyu.daydayshoping.service.IProductService;
import org.dengyu.daydayshoping.util.AjaxResult;
import org.dengyu.daydayshoping.util.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    public IProductService productService;

    /**
    * 保存和修改公用的
    * @param product  传递的实体
    * @return Ajaxresult转换结果
    */
    @RequestMapping(value="/save",method= RequestMethod.POST)
    public AjaxResult save(@RequestBody Product product){
        try {
            if(product.getId()!=null){
                productService.updateById(product);
            }else{
                productService.insert(product);
            }
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMessage("保存对象失败！"+e.getMessage());
        }
    }

    /**
    * 删除对象信息
    * @param id
    * @return
    */
    @RequestMapping(value="/delete/{id}",method=RequestMethod.DELETE)
    public AjaxResult delete(@PathVariable("id") Integer id){
        try {
            productService.deleteById(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！"+e.getMessage());
        }
    }

    //获取用户
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Product get(@RequestParam(value="id",required=true) Long id)
    {
        return productService.selectById(id);
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<Product> list(){

        return productService.selectList(null);
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @RequestMapping(value = "/json",method = RequestMethod.POST)
    public PageList<Product> json(@RequestBody ProductQuery query)
    {
       /* Page<Product> page = new Page<Product>(query.getPage(),query.getRows());
            page = productService.selectPage(page);*/
        PageList<Product> pageList = productService.selectPageList(query);
        return pageList;
    }

    @RequestMapping(value = "/addViewProperties",method = RequestMethod.POST)
    public AjaxResult addViewProperties(@RequestBody Map<String,Object> params)
    {
        AjaxResult ajaxResult = AjaxResult.me();
        try {
            String tempId = String.valueOf(params.get("productId"));
            Long productId = Long.parseLong(tempId);
            List<Specification> specifications = (ArrayList<Specification>) params.get("specifications");
            productService.addViewProperties(productId,specifications);
        } catch (Exception e) {
            ajaxResult.setSuccess(false).setMessage(e.getMessage());
            e.printStackTrace();
        }
        return ajaxResult;
    }
    @RequestMapping(value = "/addSkus",method = RequestMethod.POST)
    public AjaxResult addSkus(@RequestBody Map<String,Object> params)
    {
        AjaxResult ajaxResult = AjaxResult.me();
        try {
            productService.addSkus(params);
        } catch (Exception e) {
            ajaxResult.setSuccess(false).setMessage(e.getMessage());
            e.printStackTrace();
        }
        return ajaxResult;
    }
    @RequestMapping(value = "/skus/{productId}",method = RequestMethod.GET)
    public List<Map<String,Object>> skus(@PathVariable("productId") Long productId)
    {
        List<Map<String, Object>> skus = productService.skus(productId);
        return skus;
    }
}
