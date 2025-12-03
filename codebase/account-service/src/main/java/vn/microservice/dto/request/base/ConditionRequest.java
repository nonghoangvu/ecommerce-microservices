package vn.microservice.dto.request.base;

import lombok.*;
import vn.microservice.common.Constant;

/**
 * Condition Request
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConditionRequest {

    /* Keyword */
    private String keyword;

    /* Page No */
    private int page = Constant.DEFAULT_PAGE_NO;

    /* Page Size */
    private int size = Constant.DEFAULT_PAGE_SIZE;

    /* Sort */
    private String sort;
}
