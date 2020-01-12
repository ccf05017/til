package com.poppo.springboot.jpa.demo.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ItemRepositoryTests {

    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void howJpaCanKnowIsThisEntityNewOrNot() {

        // 줄줄이 따라가다보면 JpaRepository의 save까지 들어간다.
        // 해당 부분에 중단점 찍고 디버거 돌려보자
        // 가서 id가 null이나 0인지 확인하고 맞다면 새 객체로 취급
        // => 내가 ID를 직접 만드는 경우에는 정상작동하지 않는다는 얘기(데이터가 전부 null로 들어가는 이상한 상황 만날지도)
        // 편하게 하려면 생성일, 생성자 등의 auditing 기능을 활용해서 isNew 메서드를 직접 구현해라
        itemRepository.save(Item.builder().id("A").build());
    }
}