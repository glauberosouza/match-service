package com.glauber.MatchService.templates.priceAlertTemplate;

import java.util.Objects;

public class PriceAlertRequest {
    private String name;
    private String productName;
    private double priceRange;
    private String email;

    public PriceAlertRequest(String name, String productName, double priceRange, String email) {
        this.name = name;
        this.productName = productName;
        this.priceRange = priceRange;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(double priceRange) {
        this.priceRange = priceRange;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PriceAlertRequest that = (PriceAlertRequest) o;
        return Double.compare(that.priceRange, priceRange) == 0 && Objects.equals(name, that.name) && Objects.equals(productName, that.productName) && Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, productName, priceRange, email);
    }

    @Override
    public String toString() {
        return "PriceAlertRequest{" +
                "name='" + name + '\'' +
                ", productName='" + productName + '\'' +
                ", priceRange=" + priceRange +
                ", email='" + email + '\'' +
                '}';
    }
}
