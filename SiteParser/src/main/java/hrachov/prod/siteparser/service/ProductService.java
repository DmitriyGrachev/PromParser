package hrachov.prod.siteparser.service;

import hrachov.prod.siteparser.model.Product;
import hrachov.prod.siteparser.repository.ProductRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public void deleteAllProducts() {
        productRepository.deleteAll();
    }
}
