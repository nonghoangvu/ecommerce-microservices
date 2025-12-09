package vn.microservice.controller.request;

import lombok.Getter;
import lombok.Setter;

/**
 * Product Request
 */
@Getter
@Setter
public class ProductRequest {
    /* Product document ID */
    private String id;

    /* Product name */
    private String name;

    /* Description */
    private String description;

    /* Price's product */
    private double price;
}
