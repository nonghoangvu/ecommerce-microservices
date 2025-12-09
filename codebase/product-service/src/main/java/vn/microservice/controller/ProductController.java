package vn.microservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vn.microservice.config.MessageUtils;
import vn.microservice.controller.request.ProductRequest;
import vn.microservice.controller.request.ProductRequestCondition;
import vn.microservice.controller.response.ApiResponse;
import vn.microservice.service.ProductService;

/**
 * Product controller
 */
@RestController
@Slf4j(topic = "PRODUCT-CONTROLLER")
@RequiredArgsConstructor
public class ProductController {

    /* Product Service */
    private final ProductService productService;

    /* I18N */
    private final MessageUtils message;

    /**
     * Get all product
     *
     * @return ApiResponse
     */
    @GetMapping("/list")
    public ApiResponse getAllProduct(ProductRequestCondition condition) {
        log.info("Get all product");
        return ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .message(message.getMessage("message.readDataSuccess"))
                .data(productService.getAllProduct(condition))
                .build();
    }

    /**
     * Get product by productId
     *
     * @param productId product id
     * @return ApiResponse
     */
    @GetMapping("/{productId}")
    public ApiResponse getProductById(@PathVariable("productId") String productId) {
        log.info("Get product by productId {}", productId);
        return ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .message(message.getMessage("message.success"))
                .data(productService.getProductByProductId(productId))
                .build();
    }

    /**
     * Save product
     *
     * @param request Product request body
     * @return ApiResponse
     */
    @PostMapping("/save")
    public ApiResponse saveProduct(@RequestBody ProductRequest request) {
        log.info("Save product");
        return ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .message(message.getMessage("message.success"))
                .data(productService.saveProduct(request))
                .build();
    }

    /**
     * Delete product
     *
     * @param productId product id
     * @return ApiResponse
     */
    @DeleteMapping("/delete/{productId}")
    public ApiResponse deleteProduct(@PathVariable("productId") String productId) {
        log.info("Delete product");
        productService.deleteProduct(productId);
        return ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .message(message.getMessage("message.deleteSuccess"))
                .build();
    }
}
