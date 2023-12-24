package com.glauber.MatchService.model.service.impl;

import com.glauber.MatchService.model.entity.Product;
import com.glauber.MatchService.model.repository.ProductRepository;
import com.glauber.MatchService.model.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product findById(Long id) {
        var productById = productRepository.findById(id);
        if (!productById.isPresent()) {
            throw new NoSuchElementException("Não foi encontrado o produto com o id: "
                    + id + " informado!");
        }
        return productById.get();
    }

    @Override
    public void delete(Long id) {
        var productById = productRepository.findById(id);
        if (!productById.isPresent()) {
            throw new NoSuchElementException("Não foi encontrado o produto com o id: "
                    + id + " informado!");
        }
        var product = productById.get();
        productRepository.delete(product);
    }
}
