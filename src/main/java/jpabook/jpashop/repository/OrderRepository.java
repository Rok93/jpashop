package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager entityManager;

    public void save(Order order) {
        entityManager.persist(order);
    }

    public Order findOne(Long id) {
        return entityManager.find(Order.class, id);
    }

    // 동적쿼리를 가진다. orderSearch의 status와 name이 없는 경우에 대해 어떻게 작성할 것인가!?
    public List<Order> findAll(OrderSearch orderSearch) {
        return entityManager.createQuery("select o from Order o join o.member m" +
                " where o.status = :status " +
                " and m.name like :name", Order.class)
                .setParameter("status", orderSearch.getOrderStatus())
                .setParameter("name", orderSearch.getMemberName())
//                .setFirstResult() // paging 도 이런 방식으로 가능하다!!
                .setMaxResults(1_000)
                .getResultList();
    }

    /**
     * JPA Criteria <-- 표준 스펙이긴하지만... 유지보수성이 너무 떨어져서 별로다 (읽기 어려움!)
     * 권장 방법은 아님 (by. 영한님)
     * JPA Criteria가 JPQL을 작성할 수 있게 도와주는 것이다!
     * 동적쿼리를 작성할 때, 메리트가 있으나 가독성이 zero에 가깝다는 치명적인 단점이 있다(무슨 코드가 나올지 감이 안잡힌다!!).
     * QueryDsl 이라는 갓갓 라이브러리가 있다!!! (추후 강의로 접하자... ㅠㅠ)
     */
    public List<Order> findAllByCriteria(OrderSearch orderSearch) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaBuilderQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> o = criteriaBuilderQuery.from(Order.class);
        Join<Object, Object> m = o.join("member", JoinType.INNER);

        List<Predicate> criteria = new ArrayList<>();

        //주문 상태 검색
        if (orderSearch.getOrderStatus() != null) {
            Predicate status = criteriaBuilder.equal(o.get("status"), orderSearch.getOrderStatus());
            criteria.add(status);
        }

        //회원 이름 검색
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            Predicate name = criteriaBuilder.like(m.<String>get("name"), orderSearch.getMemberName() + "%");
            criteria.add(name);
        }

        criteriaBuilderQuery.where(criteria.toArray(new Predicate[criteria.size()]));
        TypedQuery<Order> query = entityManager.createQuery(criteriaBuilderQuery).setMaxResults(1_000);
        return query.getResultList();
    }

//    public List<Order> findAll(OrderSearch orderSearch) {
//    }

}
