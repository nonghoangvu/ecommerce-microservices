package vn.microservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import vn.microservice.model.Order;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {
}
