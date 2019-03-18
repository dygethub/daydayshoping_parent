package org.dengyu.daydayshoping.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.dengyu.daydayshoping.domain.Product;
import org.dengyu.daydayshoping.domain.Specification;
import org.dengyu.daydayshoping.mapper.ProductMapper;
import org.dengyu.daydayshoping.mapper.SpecificationMapper;
import org.dengyu.daydayshoping.service.ISpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 商品属性 服务实现类
 * </p>
 *
 * @author dengyu
 * @since 2019-01-20
 */
@Service
public class SpecificationServiceImpl extends ServiceImpl<SpecificationMapper, Specification> implements ISpecificationService {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private SpecificationMapper specificationMapper;

    @Override
    public List<Specification> skusProperties(Long productId) {
        List<Specification> specifications=null;
        Product product = productMapper.selectById(productId);
        if(product.getSkuTemplate()!=null){
             specifications = JSONArray.parseArray(product.getSkuTemplate(), Specification.class);
            return specifications;
        }
        EntityWrapper<Specification> wrapper = new EntityWrapper<>();
        wrapper.eq("product_type_id", product.getProductTypeId());
        wrapper.eq("is_sku", 1);
        specifications = specificationMapper.selectList(wrapper);
        return specifications;
    }
}
