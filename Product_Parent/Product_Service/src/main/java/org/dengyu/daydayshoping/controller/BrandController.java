package org.dengyu.daydayshoping.controller;

import org.dengyu.daydayshoping.domain.Brand;
import org.dengyu.daydayshoping.domain.ProductType;
import org.dengyu.daydayshoping.query.BrandQuery;
import org.dengyu.daydayshoping.service.IBrandService;
import org.dengyu.daydayshoping.util.AjaxResult;
import org.dengyu.daydayshoping.util.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/brand")
public class BrandController {
    @Autowired
    public IBrandService brandService;

    /**
    * 保存和修改公用的
    * @param brand  传递的实体
    * @return Ajaxresult转换结果
    */
    @RequestMapping(value="/save",method= RequestMethod.POST)
    public AjaxResult save(@RequestBody Brand brand){
        try {
            if(brand.getId()!=null){
                brandService.updateById(brand);
            }else{
                brandService.insert(brand);
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
    public AjaxResult delete(@PathVariable("id") Long id){
        try {
            brandService.deleteById(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("删除对象失败！"+e.getMessage());
        }
    }

    //获取用户
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Brand get(@PathVariable("id") Long id)
    {
        return brandService.selectById(id);
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<Brand> list(){

        return brandService.selectList(null);
    }
    @RequestMapping(value = "/allProductType/{keyword}",method = RequestMethod.POST)
    public List<ProductType> getAllProductType(@PathVariable("keyword") String keyword){
        return brandService.getAllProductType(keyword);
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @RequestMapping(value = "/json",method = RequestMethod.POST)
    public PageList<Brand> json(@RequestBody BrandQuery query)
    {
       /* Page<Brand> page = new Page<Brand>(query.getPage(),query.getRows());
            page = brandService.selectPage(page);
            return new PageList<Brand>(page.getTotal(),page.getRecords());*/
       return brandService.selectPageList(query);
    }
    @RequestMapping(value = "/backView/{productTypeId}",method = RequestMethod.GET)
    public Object[] backViewProductType(@PathVariable("productTypeId") Long productTypeId){
        return brandService.backViewProductType(productTypeId);
    }
    @RequestMapping(value = "/delLogoPath/{brandId}",method = RequestMethod.GET)
    public void delLogoPath(@PathVariable("brandId") Long brandId){
         brandService.delLogoPath(brandId);
    }
    @RequestMapping(value = "/batchRemoveBrand",method = RequestMethod.POST)
    public AjaxResult batchRemoveBrand(@RequestBody Map<String,Object> ids){
        AjaxResult ajaxResult = AjaxResult.me();
        try {
            brandService.batchRemoveBrand(ids);
        } catch (Exception e) {
            ajaxResult.setSuccess(false).setMessage(e.getMessage());
            e.printStackTrace();
        }
        return ajaxResult;
    }
}