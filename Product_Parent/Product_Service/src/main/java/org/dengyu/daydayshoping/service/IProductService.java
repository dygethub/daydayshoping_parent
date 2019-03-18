package org.dengyu.daydayshoping.service;

import com.baomidou.mybatisplus.service.IService;
import org.dengyu.daydayshoping.domain.Product;
import org.dengyu.daydayshoping.domain.Specification;
import org.dengyu.daydayshoping.query.ProductQuery;
import org.dengyu.daydayshoping.util.PageList;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品 服务类
 * </p>
 *
 * @author dengyu
 * @since 2019-01-14
 */
public interface IProductService extends IService<Product> {

    void addViewProperties(Long productId, List<Specification> specifications);
    PageList<Product> selectPageList(ProductQuery query);

    void addSkus(Map<String, Object> params);

    List<Map<String,Object>> skus(Long  productId);
}
