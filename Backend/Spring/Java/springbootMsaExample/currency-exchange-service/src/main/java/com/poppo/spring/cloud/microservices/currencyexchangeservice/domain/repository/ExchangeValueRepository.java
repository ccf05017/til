package com.poppo.spring.cloud.microservices.currencyexchangeservice.domain.repository;

import com.poppo.spring.cloud.microservices.currencyexchangeservice.domain.ExchangeValue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeValueRepository extends JpaRepository<ExchangeValue, Long> {

    ExchangeValue findByFromAndTo(String from, String to);
}
