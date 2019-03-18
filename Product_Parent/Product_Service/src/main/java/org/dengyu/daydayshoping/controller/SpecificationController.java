package org.dengyu.daydayshoping.controller;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.commons.lang.StringUtils;
import org.dengyu.daydayshoping.domain.Product;
import org.dengyu.daydayshoping.domain.Specification;
import org.dengyu.daydayshoping.query.SpecificationQuery;
import org.dengyu.daydayshoping.service.IProductService;
import org.dengyu.daydayshoping.service.ISpecificationService;
import org.dengyu.daydayshoping.util.AjaxResult;
import org.dengyu.daydayshoping.util.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/specification")
public class SpecificationController {
    @Autowired
    public ISpecificationService specificationService;
    @Autowired
    private IProductService productService;

    /**
    * 保存和修改公用的
    * @param specification  传递的实体
    * @return Ajaxresult转换结果
    */
    @RequestMapping(value="/save",method= RequestMethod.POST)
    public AjaxResult save(@RequestBody Specification specification){
        try {
            if(specification.getId()!=null){
                specificationService.updateById(specification);
            }else{
                specificationService.insert(specification);
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
            specificationService.deleteById(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！"+e.getMessage());
        }
    }

    //获取用户
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Specification get(@PathVariable("id") Long id)
    {
        return specificationService.selectById(id);
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<Specification> list(){

        return specificationService.selectList(null);
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @RequestMapping(value = "/json",method = RequestMethod.POST)
    public PageList<Specification> json(@RequestBody SpecificationQuery query)
    {
        Page<Specification> page = new Page<Specification>(query.getPage(),query.getRows());
            page = specificationService.selectPage(page);
            return new PageList<Specification>(page.getTotal(),page.getRecords());
    }

    @RequestMapping(value = "/product/{productId}",method = RequestMethod.GET)
    public List<Specification> queryViewProperties(@PathVariable("productId") Long productId){
        Product product = productService.selectById(productId);
        String viewProperties = product.getViewProperties();
        //商品已经设置显示属性,直接从product表中获取(里面就有属性,又有数据)
        if (StringUtils.isNotBlank(viewProperties)){
            return JSONArray.parseArray(viewProperties, Specification.class);
        }else{
            EntityWrapper<Specification> wrapper = new EntityWrapper<>();
            wrapper.eq("product_type_id", product.getProductTypeId());
            wrapper.eq("is_sku", 0);
            return  specificationService.selectList(wrapper);
        }

    }
    @RequestMapping(value = "/skusProperties/{productId}",method = RequestMethod.GET)
    public List<Specification> skusProperties(@PathVariable("productId") Long productId){
       return specificationService.skusProperties(productId);
    }
}
