package com.poppo.springboot.jpa.demo.domain.repository;

import com.poppo.springboot.jpa.demo.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;

// @Repository<- 이것도 생략 가능
public interface TeamRepository extends JpaRepository<Team, Long> {
}
