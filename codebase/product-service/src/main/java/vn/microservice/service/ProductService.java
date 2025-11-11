package vn.microservice.service;

/**
 * Product service interface
 */
public interface ProductService {
    String getAllProduct();
    String getProductByProductId(String productId);
    String saveProduct(String productId);
    void deleteProduct(String productId);
}
