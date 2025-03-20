package hrachov.prod.siteparser.service;

import hrachov.prod.siteparser.model.Product;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public interface ParsingService {
    List<Product> getProductsFromHtml(String nameProduct);
    List<Product> getProductsFromDatabase();
}
