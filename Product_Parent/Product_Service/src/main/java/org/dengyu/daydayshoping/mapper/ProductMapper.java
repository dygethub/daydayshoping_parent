package org.dengyu.daydayshoping.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import org.dengyu.daydayshoping.domain.Product;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.dengyu.daydayshoping.query.ProductQuery;

import java.util.List;

/**
 * <p>
 * 商品 Mapper 接口
 * </p>
 *
 * @author dengyu
 * @since 2019-01-14
 */
public interface ProductMapper extends BaseMapper<Product> {

    List<Product> loadPageData(Page<Product> page, ProductQuery query);
}
