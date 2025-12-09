package vn.microservice.service;

import vn.microservice.controller.request.ProductRequest;
import vn.microservice.controller.request.ProductRequestCondition;
import vn.microservice.controller.response.ProductResponse;

import java.util.List;

/**
 * Product service interface
 */
public interface ProductService {

    List<ProductResponse> getAllProduct(ProductRequestCondition condition);

    ProductResponse getProductByProductId(String productId);

    String saveProduct(ProductRequest request);

    void deleteProduct(String productId);
}
