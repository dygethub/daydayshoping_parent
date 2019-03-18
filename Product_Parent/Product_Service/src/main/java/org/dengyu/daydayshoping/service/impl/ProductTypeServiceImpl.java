package org.dengyu.daydayshoping.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.dengyu.daydayshoping.controller.JedisController;
import org.dengyu.daydayshoping.domain.ProductType;
import org.dengyu.daydayshoping.mapper.ProductTypeMapper;
import org.dengyu.daydayshoping.service.IProductTypeService;
import org.dengyu.daydayshoping.utils.ProductTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品目录 服务实现类
 * </p>
 *
 * @author dengyu
 * @since 2019-01-14
 */
@Service
public class ProductTypeServiceImpl extends ServiceImpl<ProductTypeMapper, ProductType> implements IProductTypeService {
@Autowired
private ProductTypeMapper productTypeMapper;
@Autowired
private JedisController jedisController;
private static final String PRODUCT_TYPE_TREE="product_Type_Tree";

    public ProductTypeServiceImpl() {
    }

    @Override
    public boolean insert(ProductType entity) {
        boolean insertResult = super.insert(entity);
        jedisController.set(PRODUCT_TYPE_TREE,"");
        return insertResult;
    }

    @Override
    public boolean updateById(ProductType entity) {
        jedisController.set(PRODUCT_TYPE_TREE,"");
        return super.updateById(entity);
    }


    @Override
    public boolean deleteById(Serializable id) {
        jedisController.set(PRODUCT_TYPE_TREE,"");
        return super.deleteById(id);
    }

    @Override
    public List<ProductType> getTreeDataLoop(Long parentId) {
        //得到所有产品类型
        List<ProductType> productTypes = productTypeMapper.selectList(null);
       //将所有的产品类型存在map中
        Map<Long,ProductType> map=new HashMap<>();
        //存储指定父类类型和其对应的所有子类型
        List<ProductType> parents=new ArrayList<>();
        for (ProductType productType : productTypes) {
            map.put(productType.getId(), productType);
        }
        for (ProductType productType : productTypes) {
            //如果是父类型，就加入到list集合中，否者添加到父类型的子类型下
            if(productType.getPid().longValue()==parentId){
                parents.add(productType);
            }else {
                //得到父类型对象,再得到父类的子类对象集合，将该子类设置进去。
                if(productType.getPid()!=null){
                    map.get(productType.getPid()).getChildrens().add(productType);
                }
            }
        }
        return parents;
    }

    @Override
    public List<ProductType> treeData() {
        List<ProductType> dataLoop=null;
        String productTypeTree = jedisController.get(PRODUCT_TYPE_TREE);
        if(StringUtils.isNotBlank(productTypeTree)){
            System.out.println("缓存数据--------------------------");
            dataLoop = JSONArray.parseArray(productTypeTree, ProductType.class);
        }else {
            dataLoop = getTreeDataLoop(0L);
            jedisController.set(PRODUCT_TYPE_TREE, JSONArray.toJSONString(dataLoop));
            System.out.println("数据库数据------------------------");
        }
        return dataLoop;
    }

    @Override
    public List<ProductTree> treeSelect() {
        List<ProductTree> returnTrees = new ArrayList<>();
        List<ProductTree> Trees = new ArrayList<>();
        Map<Long,ProductTree> maps=new HashMap<>();
        List<ProductType> productTypes = productTypeMapper.selectList(null);
        for (ProductType productType : productTypes) {
            ProductTree tree = new ProductTree(productType.getName(),productType.getId(),productType.getPid());
            Trees.add(tree);
        }
        for ( ProductTree productTree : Trees) {
            maps.put(productTree.getValue(), productTree);
        }
        for ( ProductTree productTree : Trees) {
            if(productTree.getPid().longValue()==0L){
                returnTrees.add(productTree);
            }else {
                maps.get(productTree.getPid()).getChildren().add(productTree);
            }
        }
        return returnTrees;
    }
}
