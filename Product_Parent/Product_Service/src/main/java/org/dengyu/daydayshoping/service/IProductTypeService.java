package org.dengyu.daydayshoping.service;

import com.baomidou.mybatisplus.service.IService;
import org.dengyu.daydayshoping.domain.ProductType;
import org.dengyu.daydayshoping.utils.ProductTree;

import java.util.List;

/**
 * <p>
 * 商品目录 服务类
 * </p>
 *
 * @author dengyu
 * @since 2019-01-14
 */
public interface IProductTypeService extends IService<ProductType> {
    List<ProductType> getTreeDataLoop(Long parentId);
    List<ProductType> treeData();
    List<ProductTree> treeSelect();
}
