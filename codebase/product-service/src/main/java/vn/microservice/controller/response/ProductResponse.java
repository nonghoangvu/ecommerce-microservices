package vn.microservice.controller.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductResponse {
    private String id;
    private String name;
    private String description;
    private double price;
}
