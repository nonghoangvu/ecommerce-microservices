package vn.microservice.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Setter
@Getter
@Document(indexName = "products")
public class ProductDocument {
    @Id
    private String id;

    private String name;
    private String description;
    private double price;
}
