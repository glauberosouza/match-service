package com.glauber.MatchService.templates.productTemplate;

import java.math.BigDecimal;
import java.util.Objects;

public class ProductRequest {
    private String name;
    private String link;
    private BigDecimal price;

    public ProductRequest() {

    }

    public ProductRequest(String name, String link, BigDecimal price) {
        this.name = name;
        this.link = link;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductRequest that = (ProductRequest) o;
        return Objects.equals(name, that.name) && Objects.equals(link, that.link) && Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, link, price);
    }

    @Override
    public String toString() {
        return "ProductRequest{" +
                "name='" + name + '\'' +
                ", link='" + link + '\'' +
                ", price=" + price +
                '}';
    }
}
