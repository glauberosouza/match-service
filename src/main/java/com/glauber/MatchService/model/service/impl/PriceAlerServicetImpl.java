package com.glauber.MatchService.model.service.impl;

import com.glauber.MatchService.model.entity.PriceAlert;
import com.glauber.MatchService.model.repository.PriceAlertRepository;
import com.glauber.MatchService.model.service.PriceAlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class PriceAlerServicetImpl implements PriceAlertService {
    @Autowired
    private PriceAlertRepository priceAlertRepository;

    @Override
    public PriceAlert findById(Long id) {
        var priceAlertById = priceAlertRepository.findById(id);
        if (!priceAlertById.isPresent()) {
            throw new NoSuchElementException("Não foi localizado um alerta com o id: " +
                    id + " informado!");
        }
        return priceAlertById.get();
    }

    @Override
    public void delete(Long id) {
        var priceAlertById = priceAlertRepository.findById(id);
        if (!priceAlertById.isPresent()) {
            throw new NoSuchElementException("Não foi localizado um alerta com o id: " +
                    id + " informado!");
        }
        var priceAlert = priceAlertById.get();
        priceAlertRepository.delete(priceAlert);
    }
}
