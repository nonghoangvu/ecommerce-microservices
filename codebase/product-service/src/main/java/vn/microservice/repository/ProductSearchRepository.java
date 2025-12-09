package vn.microservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import vn.microservice.model.ProductDocument;

@Repository
public interface ProductSearchRepository extends ElasticsearchRepository<ProductDocument, String> {
    @Query("""
            {
              "match": {
                "name": {
                  "query": "?0"
                }
              }
            }
            """)
    Page<ProductDocument> searchByName(String keyword, Pageable pageable);
}
