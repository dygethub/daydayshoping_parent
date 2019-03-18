import org.dengyu.daydayshoping.ProductApplication;
import org.dengyu.daydayshoping.domain.ProductType;
import org.dengyu.daydayshoping.service.impl.BrandServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProductApplication.class )
public class TestBrand {
@Autowired
private BrandServiceImpl brandService;
    @Test
    public void testA(){
        List<ProductType> types = brandService.getAllProductType("西");
        for (ProductType type : types) {
            System.out.println(type);
        }
    }
    @Test
    public void testB(){
        Object[] objects = brandService.backViewProductType(184L);
        for (Object object : objects) {
            System.out.println(object);
        }
    }

}
