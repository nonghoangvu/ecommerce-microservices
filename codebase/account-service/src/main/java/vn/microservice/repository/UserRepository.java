package vn.microservice.repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import vn.microservice.common.Constant;
import vn.microservice.dto.request.AccountConditionRequest;
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
     * @return Optional<User>
     */
    Optional<User> findByUsername(String username);

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

    /**
     * Find all user by condition
     *
     * @param condition condition
     * @return Specification<User>
     */
    default Specification<User> findAllUserByCondition(AccountConditionRequest condition) {
        return (Root<User> root,CriteriaQuery<?> query,CriteriaBuilder cb) -> {
            Predicate predicate = cb.conjunction();

            if(StringUtils.hasLength(condition.getKeyword())) {
                String keyword = String.format(Constant.LIKE_KEYWORD, condition.getKeyword().trim().toLowerCase());

                Predicate searchPredicate = cb.or(
                        cb.like(cb.lower(root.get("username")), keyword),
                        cb.like(cb.lower(root.get("email")), keyword),
                        cb.like(cb.lower(root.get("phone")), keyword)
                );
                predicate = cb.and(predicate, searchPredicate);
            }

            return predicate;
        };
    }
}
