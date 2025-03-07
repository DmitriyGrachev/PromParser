package hrachov.prod.siteparser.service;

import hrachov.prod.siteparser.model.Product;

import java.util.List;

public interface Parsing {
    public List<Product> getProductsFromHtml(String nameProduct);
}
