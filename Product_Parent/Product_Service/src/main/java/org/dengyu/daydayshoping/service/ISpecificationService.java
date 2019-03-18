package org.dengyu.daydayshoping.service;

import com.baomidou.mybatisplus.service.IService;
import org.dengyu.daydayshoping.domain.Specification;

import java.util.List;

/**
 * <p>
 * 商品属性 服务类
 * </p>
 *
 * @author dengyu
 * @since 2019-01-20
 */
public interface ISpecificationService extends IService<Specification> {
    List<Specification> skusProperties(Long productId);
}
