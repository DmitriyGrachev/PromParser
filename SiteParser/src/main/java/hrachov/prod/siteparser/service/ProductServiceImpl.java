package hrachov.prod.siteparser.service;

import hrachov.prod.siteparser.model.Product;
import hrachov.prod.siteparser.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    ProductRepository productRepository;

    public void deleteAllProducts() {
        productRepository.deleteAll();
    }

    public Page<Product> getProducts(int page, int size, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortField).ascending() : Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        return productRepository.findAll(pageable);
    }
}
