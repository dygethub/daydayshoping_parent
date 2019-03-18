import org.dengyu.daydayshoping.ProductApplication;
import org.dengyu.daydayshoping.domain.Brand;
import org.dengyu.daydayshoping.domain.Product;
import org.dengyu.daydayshoping.query.BrandQuery;
import org.dengyu.daydayshoping.query.ProductQuery;
import org.dengyu.daydayshoping.service.IProductService;
import org.dengyu.daydayshoping.service.impl.BrandServiceImpl;
import org.dengyu.daydayshoping.util.PageList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProductApplication.class )
public class TestProduct {
    @Autowired
    private BrandServiceImpl brandService;
    @Autowired
    private IProductService productService;
    @Test
    public void testA(){
       /* PageList<Brand> list = brandService.json(new BrandQuery());
        for (Brand brand : list.getRows()) {
            System.out.println(brand);
        }
        System.out.println(list.getTotal());*/
    }
    @Test
    public void testB(){
        PageList<Brand> list = brandService.selectPageList(new BrandQuery());
        System.out.println(list.getTotal());
        for (Brand brand : list.getRows()) {
            System.out.println(brand);
        }

    }
    @Test
    public void testC(){
        PageList<Product> list = productService.selectPageList(new ProductQuery());
        System.out.println(list.getTotal());
        for (Product brand : list.getRows()) {
            System.out.println(brand);
        }

    }
    @Test
    public void testD(){
        List<Map<String, Object>> skus = productService.skus(57L);
        for (Map<String, Object> map : skus) {
            System.out.println(map);
        }

    }
}
