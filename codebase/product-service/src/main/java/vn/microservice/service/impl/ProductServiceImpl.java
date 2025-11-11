package vn.microservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.microservice.service.ProductService;

/**
 * Product service implement
 */
@Service
@Slf4j(topic = "PRODUCT-SERVICE")
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    /**
     * Get all list product
     *
     * @return list product
     */
    @Override
    public String getAllProduct() {
        log.info("Get list product");
        return "List product";
    }

    /**
     * Get product by productId
     *
     * @param productId productId
     * @return Product
     */
    @Override
    public String getProductByProductId(String productId) {
        return "Product id=" + productId;
    }

    /**
     * Save product
     *
     * @param productId productId
     * @return productId
     */
    @Override
    public String saveProduct(String productId) {
        log.info("Product has been saved!");
        return "productId";
    }

    /**
     * Delete product by productId
     *
     * @param productId productId
     */
    @Override
    public void deleteProduct(String productId) {
        log.info("Product has been deleted!");
    }
}
