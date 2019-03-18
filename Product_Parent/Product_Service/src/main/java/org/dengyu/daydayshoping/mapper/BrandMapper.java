package org.dengyu.daydayshoping.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.dengyu.daydayshoping.domain.Brand;
import org.dengyu.daydayshoping.query.BrandQuery;

import java.util.List;

/**
 * <p>
 * 品牌信息 Mapper 接口
 * </p>
 *
 * @author dengyu
 * @since 2019-01-14
 */
public interface BrandMapper extends BaseMapper<Brand> {
    List<Brand> selectPageList(Page<Brand> page, BrandQuery query);
}
