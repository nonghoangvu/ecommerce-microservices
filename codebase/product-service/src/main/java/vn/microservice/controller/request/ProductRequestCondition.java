package vn.microservice.controller.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequestCondition {
    /* Keyword */
    private String keyword;

    /* Page No (Default 0) */
    private int page;

    /* Page size (Default 10) */
    private int size = 10;
}
