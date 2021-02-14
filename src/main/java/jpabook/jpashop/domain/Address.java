package jpabook.jpashop.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Getter
@Embeddable
public class Address { // Address는 JPA의 내장타입이기 때문에 Embeddable을 붙인다!

    private String city;

    private String street;

    private String zipcode;

    protected Address() {
    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
