package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @NotEmpty
    private String name;

    @Embedded
    private Address address; // 내장 타입을 쓸 때는, @Embeddable or @Embedded 둘 중 하나만 있어도 되지만, 보통 둘 다 써준다!!

    @OneToMany(mappedBy = "member") //Order 테이블에있는 member필드에 의해 맵핑 된 것이다! (나는 맵핑한 것이 아닌 맵핑 된 거울이다!)
    private List<Order> orders = new ArrayList<>();
}
