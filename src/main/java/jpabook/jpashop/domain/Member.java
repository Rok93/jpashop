package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

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

    @JsonIgnore
    @OneToMany(mappedBy = "member") //Order 테이블에있는 member필드에 의해 맵핑 된 것이다! (나는 맵핑한 것이 아닌 맵핑 된 거울이다!)
    private List<Order> orders = new ArrayList<>();
}
