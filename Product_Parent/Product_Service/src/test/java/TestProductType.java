import org.dengyu.daydayshoping.ProductApplication;
import org.dengyu.daydayshoping.domain.ProductType;
import org.dengyu.daydayshoping.service.impl.ProductTypeServiceImpl;
import org.dengyu.daydayshoping.utils.ProductTree;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProductApplication.class )
public class TestProductType {
    @Autowired
    private ProductTypeServiceImpl productTypeService;
    @Test
    public void testA(){
        List<ProductType> dataLoop = productTypeService.getTreeDataLoop(0L);
    }
    @Test
    public void testB(){
        List<ProductTree> productTrees = productTypeService.treeSelect();
        for (ProductTree productTree : productTrees) {
            System.out.println(productTree);
        }
    }

}
