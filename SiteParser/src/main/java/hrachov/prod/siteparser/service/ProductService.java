package hrachov.prod.siteparser.service;

import hrachov.prod.siteparser.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    public void deleteAllProducts();

    public Page<Product> getProducts(int page, int size, String sortField, String sortDirection);
}
