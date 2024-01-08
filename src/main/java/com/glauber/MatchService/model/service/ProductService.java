package com.glauber.MatchService.model.service;

import com.glauber.MatchService.model.entity.Product;

public interface ProductService {
    Product findById(Long id);
    void delete(Long id);
}
