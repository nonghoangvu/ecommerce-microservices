package vn.microservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import vn.microservice.model.User;

import java.util.Optional;

/**
 * User repository
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    /**
     * Find user by username
     * @param username username
     * @return User
     */
    User findByUsername(String username);

    /**
     * Find user by email
     * @param email email
     * @return Optional<User>
     */
    Optional<User> findByEmail(String email);

    /**
     * Check exits by username
     * @param username username
     * @return true/false
     */
    boolean existsByUsername(String username);

    /**
     * Check exists by email
     * @param email email
     * @return true/false
     */
    boolean existsByEmail(String email);

    /**
     * Check exists by phone
     * @param phone phone
     * @return true/false
     */
    boolean existsByPhone(String phone);
}
