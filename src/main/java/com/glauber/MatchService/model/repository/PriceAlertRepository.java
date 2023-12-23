package com.glauber.MatchService.model.repository;

import com.glauber.MatchService.model.entity.PriceAlert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceAlertRepository extends JpaRepository<PriceAlert, Long> {
}
