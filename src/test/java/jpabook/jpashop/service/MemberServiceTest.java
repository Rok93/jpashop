package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepositoryOld;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepositoryOld memberRepositoryOld;

    @Test
    void 회원가입() {
        //given
        Member member = new Member();
        member.setName("kim");
        member.setAddress(new Address("city","street","zipcod"));

        //when
        Long savedId = memberService.join(member);

        //then
        assertThat(memberRepositoryOld.findOne(savedId)).isEqualTo(member);
    }

    @Test
    void 중복_회원_예외() {
        //given
        Member member1 = new Member();
        member1.setName("kim1");
        Member member2 = new Member();
        member2.setName("kim1");

        //when //then
        memberService.join(member1);
        assertThatIllegalStateException()
                .isThrownBy(() -> memberService.join(member2));
    }
}
