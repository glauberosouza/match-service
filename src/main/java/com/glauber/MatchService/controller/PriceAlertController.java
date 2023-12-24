package com.glauber.MatchService.controller;

import com.glauber.MatchService.controller.response.PriceAlertResponse;
import com.glauber.MatchService.model.entity.PriceAlert;
import com.glauber.MatchService.model.service.PriceAlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/match/alerts")
public class PriceAlertController {
    private final PriceAlertService priceAlertService;

    @Autowired
    public PriceAlertController(PriceAlertService priceAlertService) {
        this.priceAlertService = priceAlertService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PriceAlertResponse> findAlertById(@PathVariable Long id) {
        var priceAlertById = priceAlertService.findById(id);
        var productResponse = PriceAlert.toPriceAlertResponse(priceAlertById);
        return ResponseEntity.status(HttpStatus.OK).body(productResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        priceAlertService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
