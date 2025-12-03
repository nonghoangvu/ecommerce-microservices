package vn.microservice.dto.response.base;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * Page Response
 * @param <T>
 */
@Getter
@Setter
@Builder
public class PageResponse<T extends Serializable> implements Serializable {

    /* Page number */
    private int pageNumber;

    /* Page size */
    private int pageSize;

    /* Total page */
    private long totalPages;

    /* Total element */
    private long totalElements;

    /* Data */
    private List<T> data;
}
