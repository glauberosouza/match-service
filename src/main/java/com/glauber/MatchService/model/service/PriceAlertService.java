package com.glauber.MatchService.model.service;

import com.glauber.MatchService.model.entity.PriceAlert;

public interface PriceAlertService {
    PriceAlert findById(Long id);
    void delete(Long id);
}
