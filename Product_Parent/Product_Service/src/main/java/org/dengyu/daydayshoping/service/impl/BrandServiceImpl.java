package org.dengyu.daydayshoping.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.dengyu.daydayshoping.domain.Brand;
import org.dengyu.daydayshoping.domain.ProductType;
import org.dengyu.daydayshoping.mapper.BrandMapper;
import org.dengyu.daydayshoping.mapper.ProductTypeMapper;
import org.dengyu.daydayshoping.query.BrandQuery;
import org.dengyu.daydayshoping.service.IBrandService;
import org.dengyu.daydayshoping.util.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 品牌信息 服务实现类
 * </p>
 *
 * @author dengyu
 * @since 2019-01-14
 */
@Service
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand> implements IBrandService {
@Autowired
private BrandMapper brandMapper;
@Autowired
private ProductTypeMapper productTypeMapper;


    @Override
    public PageList<Brand> selectPageList(BrandQuery query) {
        Page<Brand> page = new Page<>(query.getPage(),query.getRows());
        for (Brand brand : page.getRecords()) {
            System.out.println(brand);
        }
        //什么时候传入分页条件的？
        List<Brand> rows = brandMapper.selectPageList(page,query);
        for (Brand brand : page.getRecords()) {
            System.out.println(brand);
        }
        long total = page.getTotal();
        return new PageList<Brand>(total,rows);
    }

    @Override
    public List<ProductType> getAllProductType(String keyword) {
        EntityWrapper<ProductType> wrapper = new EntityWrapper<>();
        wrapper.like("name", keyword);
        return productTypeMapper.selectList(wrapper);
    }

    @Override
    public List<ProductType> treeData() {
        return null;
    }

    @Override
    public Object[] backViewProductType(Long productId) {
         List<Long> typeIds=new ArrayList<>();
        getPid(productId,typeIds);
        Collections.reverse(typeIds);
        typeIds.remove(0);
        return typeIds.toArray();
    }

    @Override
    public void delLogoPath(Long brandId) {
        Brand brand = brandMapper.selectById(brandId);
        System.out.println(brand);
        brand.setLogo("");
        brandMapper.updateById(brand);
        Brand brand2 = brandMapper.selectById(brandId);
        System.out.println(brand2);
    }

    @Override
    public void batchRemoveBrand(Map<String, Object> ids) {
        String str=(String)ids.get("ids");
        String[] split = str.split(",");
        ArrayList<Long> brandIds = new ArrayList<>();
        for (String s : split) {
            brandIds.add(Long.parseLong(s));
        }
        brandMapper.deleteBatchIds(brandIds);
    }

    private void getPid(Long productId,List<Long> typeIds) {
        typeIds.add(productId);
        ProductType productType = productTypeMapper.selectById(productId);
        if(productType!=null&&productType.getPid()!=null){
            getPid(productType.getPid(),typeIds);
        }
    }


}
