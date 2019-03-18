package org.dengyu.daydayshoping.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.dengyu.daydayshoping.domain.Product;
import org.dengyu.daydayshoping.domain.ProductExt;
import org.dengyu.daydayshoping.domain.Sku;
import org.dengyu.daydayshoping.domain.Specification;
import org.dengyu.daydayshoping.mapper.ProductExtMapper;
import org.dengyu.daydayshoping.mapper.ProductMapper;
import org.dengyu.daydayshoping.mapper.SkuMapper;
import org.dengyu.daydayshoping.mapper.SpecificationMapper;
import org.dengyu.daydayshoping.query.ProductQuery;
import org.dengyu.daydayshoping.service.IProductService;
import org.dengyu.daydayshoping.util.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 商品 服务实现类
 * </p>
 *
 * @author dengyu
 * @since 2019-01-14
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {
@Autowired
private ProductMapper productMapper;
@Autowired
private ProductExtMapper productExtMapper;
@Autowired
private SkuMapper skuMapper;
@Autowired
private SpecificationMapper specificationMapper;
    @Override
    public void addViewProperties(Long productId, List<Specification> specifications) {
        Product product = productMapper.selectById(productId);
        String viewProperties = JSONArray.toJSONString(specifications);
        product.setViewProperties(viewProperties);
        productMapper.updateById(product);

    }

    @Override
    public boolean insert(Product entity) {
        entity.setCreateTime(new Date().getTime());
        boolean result = super.insert(entity);
        ProductExt productExt = entity.getProductExt();
        if(productExt!=null){
            productExt.setProductId(entity.getId());
            productExtMapper.insert(productExt);
        }
        return result;
    }

    @Override
    public boolean updateById(Product entity) {
        entity.setUpdateTime(new Date().getTime());
        boolean result = super.updateById(entity);
        EntityWrapper<ProductExt> wrapper = new EntityWrapper<>();
        wrapper.eq("productId", entity.getId());
        ProductExt productExt = productExtMapper.selectList(wrapper).get(0);
        ProductExt temp = entity.getProductExt();
        if(productExt!=null){
            if(temp!=null){
                productExt.setDescription(temp.getDescription());
                productExt.setRichContent(temp.getRichContent());
                productExtMapper.updateById(productExt);
            }
        }else {
            temp.setProductId(entity.getId());
            productExtMapper.insert(temp);
        }
        return true;
    }

    @Override
    public PageList<Product> selectPageList(ProductQuery query) {

        Page<Product> page = new Page<>(query.getPage(),query.getRows());
        List<Product> data =  productMapper.loadPageData(page,query);
        long total = page.getTotal();
        return new PageList<>(total,data);
    }

    @Override
    public void addSkus(Map<String, Object> params) {
        /*获得产品ID*/
        Long productId = Long.parseLong(params.get("productId").toString()) ;
        /*获得skuProperties,转换成String字符串，存在product表的 sku_template字段当中*/
        List<Map<String,Object>> skuProperties = (List<Map<String,Object>>) params.get("skuProperties");
        List<Map<String,Object>> skuDatas = (List<Map<String,Object>>) params.get("skuDatas");
        Product product = productMapper.selectById(productId);
        product.setSkuTemplate(JSONArray.toJSONString(skuProperties));
        productMapper.updateById(product);
        /*删除sku属性*/
        EntityWrapper<Sku> wrapper = new EntityWrapper<>();
        wrapper.eq("productId", productId);
        skuMapper.delete(wrapper);
        for (Map<String, Object> skuData : skuDatas) {
            Sku sku = new Sku();
            sku.setProductId(productId);
            //装sku属性
            Map<String, Object> tempMap = new LinkedHashMap<>();
            //用来装构造好的sku键值对
            List<Map<String,Object>> arryList = new ArrayList<>();
            for (String key : skuData.keySet()) {
                if("price".equals(key)){
                    sku.setPrice(Integer.parseInt(skuData.get(key).toString() ));
                } else if("stock".equals(key)){
                    sku.setStock(Integer.parseInt(skuData.get(key).toString() ));
                }else if("state".equals(key)){
                    sku.setState(Boolean.parseBoolean(skuData.get(key).toString()));
                }else{
                    /*获取到属性对应的Id值*/
                    tempMap.put(key, skuData.get(key));
                }
            }
            //tempMap中数据的格式[{“身高”：179，“体重”：130}]
            //将 tempMap中的值封装成 [{id:1,key:“身高”，value:179},{id:5,key:“体重”，value:130}]
            /*1、获取到对应名字的Id*/
            /*2、将对应名字和值转换成上面的数据格式*/
            for (String skuValue : tempMap.keySet()) {
                HashMap<String, Object> temp = new HashMap<>();
                Long specId = getSpecIdByNameAndProTpId(skuValue, product.getProductTypeId());
                temp.put("id", specId);
                temp.put("key", skuValue);
                temp.put("value", tempMap.get(skuValue));
                arryList.add(temp);
            }
            /*arryList转换成Json的String存放在skuValue字段中*/
            String skuValues = JSONArray.toJSONString(arryList);
            sku.setSkuValues(skuValues);
            StringBuilder sb = new StringBuilder();
            for (Map<String, Object> map : arryList) {
                Long specId = Long.parseLong(map.get("id").toString());
                String name = map.get("value").toString();
                Integer index = getIndex(skuProperties, specId, name);
                sb.append(index).append("_");
            }
            sku.setIndexs(sb.toString().substring(0,sb.toString().lastIndexOf("_")));
            skuMapper.insert(sku);
        }

    }

    @Override
    public  List<Map<String,Object>> skus(Long productId) {
        List<Map<String,Object>> skuDatas = new ArrayList<>();

        EntityWrapper<Sku> wrapper = new EntityWrapper<>();
        wrapper.eq("productId", productId);
        List<Sku> skus = skuMapper.selectList(wrapper);
        for (Sku sku : skus) {
            LinkedHashMap<String, Object> skuMap = new LinkedHashMap<>();
            List<Map> maps = JSONArray.parseArray(sku.getSkuValues(), Map.class);
            for (Map map : maps) {
                skuMap.put(String.valueOf(map.get("key")), map.get("value"));
            }
            skuMap.put("price", sku.getPrice());
            skuMap.put("stock", sku.getStock());
            skuMap.put("state", sku.getState());
            skuDatas.add(skuMap);
        }
        return skuDatas;
    }

    /**
     * 通过产品类型和属性名字来唯一确定一个属性，返回属性Id
     * @param name
     * @param productTypeId
     * @return
     */
    private Long getSpecIdByNameAndProTpId(String name, long productTypeId) {
        Long specId=null;
        EntityWrapper<Specification> wrapper = new EntityWrapper<>();
        wrapper.eq("product_type_id", productTypeId);
        wrapper.eq("name", name);
        List<Specification> specifications = specificationMapper.selectList(wrapper);
        if(specifications!=null){
            specId=specifications.get(0).getId();
        }
        return specId;
    }

    /**
     * 得到每一条skuValue的索引。
     * @param skuProperties
     * @param proId
     * @param value
     * @return
     */
    public Integer getIndex(List<Map<String,Object>> skuProperties, Long proId, String value){
        Integer index=0;
        for (Map<String, Object> skuProperty : skuProperties) {
            Long temId = Long.parseLong(skuProperty.get("id").toString());
            if(temId.longValue()==proId.longValue()){
                List<String> skuValues = (List<String>) skuProperty.get("skuValues");
                for (String skuValue : skuValues) {
                    //如果同一个skuProperty 出现同样的值，该判断条件就不对
                    if(skuValue.equals(value)){
                       return index;
                    }
                    index++;
                }
            }
        }
        return index;
    }

}
