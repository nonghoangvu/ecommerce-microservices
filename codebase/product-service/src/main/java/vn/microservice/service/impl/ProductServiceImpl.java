package vn.microservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import vn.microservice.controller.request.ProductRequest;
import vn.microservice.controller.request.ProductRequestCondition;
import vn.microservice.controller.response.ProductResponse;
import vn.microservice.exception.ResourceNotFoundException;
import vn.microservice.model.ProductDocument;
import vn.microservice.repository.ProductSearchRepository;
import vn.microservice.service.ProductService;

import java.util.ArrayList;
import java.util.List;

/**
 * Product service implement
 */
@Service
@Slf4j(topic = "PRODUCT-SERVICE")
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    /* Product Search Repository */
    private final ProductSearchRepository productSearchRepository;

    /**
     * Get all list product
     *
     * @param condition condition to search
     * @return list product
     */
    @Override
    public List<ProductResponse> getAllProduct(ProductRequestCondition condition) {
        log.info("Get list product");

        Pageable pageable = PageRequest.of(condition.getPage(), condition.getSize());
        List<ProductResponse> data = new ArrayList<>();

        if (StringUtils.hasLength(condition.getKeyword())) {
            productSearchRepository.searchByName(condition.getKeyword(), pageable).getContent().forEach(i -> data.add(convertToResponse(i)));
        } else {
            productSearchRepository.findAll(pageable).getContent().forEach(i -> data.add(convertToResponse(i)));
        }
        return data;
    }

    /**
     * Get product by productId
     *
     * @param productId productId
     * @return ProductResponse
     */
    @Override
    public ProductResponse getProductByProductId(String productId) {
        return convertToResponse(getProductById(productId));
    }

    /**
     * Save product
     *
     * @param request Product request body
     * @return productId
     */
    @Override
    public String saveProduct(ProductRequest request) {
        ProductDocument document;
        if (StringUtils.hasLength(request.getId())) {
            document = productSearchRepository.findById(request.getId()).orElse(new ProductDocument());
        } else {
            document = new ProductDocument();
        }
        document.setName(request.getName());
        document.setDescription(request.getDescription());
        document.setPrice(request.getPrice());

        document = productSearchRepository.save(document);
        log.info("Product has been saved!");
        return document.getId();
    }

    /**
     * Delete product by productId
     *
     * @param productId productId
     */
    @Override
    public void deleteProduct(String productId) {
        productSearchRepository.delete(getProductById(productId));
        log.info("Product has been deleted!");
    }

    /**
     * Get product document by product ID
     *
     * @param productId product id
     * @throws ResourceNotFoundException product not found
     * @return ProductDocument
     */
    private ProductDocument getProductById(String productId) {
        return productSearchRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found!"));
    }

    /**
     * Convert to response
     * @param document productDocument
     * @return Response
     */
    private ProductResponse convertToResponse(ProductDocument document) {
        return ProductResponse.builder()
                .id(document.getId())
                .name(document.getName())
                .description(document.getDescription())
                .price(document.getPrice())
                .build();
    }
}
