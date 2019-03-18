package org.dengyu.daydayshoping.service;

import com.baomidou.mybatisplus.service.IService;
import org.dengyu.daydayshoping.domain.Brand;
import org.dengyu.daydayshoping.domain.ProductType;
import org.dengyu.daydayshoping.query.BrandQuery;
import org.dengyu.daydayshoping.util.PageList;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 品牌信息 服务类
 * </p>
 *
 * @author dengyu
 * @since 2019-01-14
 */
public interface IBrandService extends IService<Brand> {
    PageList<Brand> selectPageList(BrandQuery query);
    List<ProductType> getAllProductType(String keyword);
    List<ProductType> treeData();
    Object[] backViewProductType(Long productId);
    void delLogoPath(Long brandId);
    void batchRemoveBrand(Map<String,Object> ids);
}
