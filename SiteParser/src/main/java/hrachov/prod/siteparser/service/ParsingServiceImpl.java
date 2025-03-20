package hrachov.prod.siteparser.service;

import hrachov.prod.siteparser.model.Product;
import hrachov.prod.siteparser.repository.ProductRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParsingServiceImpl implements ParsingService{
    @Autowired
    ProductRepository productRepository;

    public List<Product> getProductsFromHtml(String nameProduct) {
        //String searchQuery = "гитара";
        List<Product> productsList = new ArrayList<>();

        String baseUrl = "https://prom.ua/search?search_term=" + nameProduct;
        String currentUrl = baseUrl;
        boolean hasNextPage = true;
        int pageCount = 0; // Count pages
        int maxPages = 10; // Pages limit

        while (hasNextPage && pageCount < maxPages) {
            try {
                System.out.println("Парсим страницю " + (pageCount + 1) + ": " + currentUrl);

                // Load page
                Document doc = Jsoup.connect(currentUrl)
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
                        .timeout(30000)
                        .get();

                // Search products
                Elements products = doc.select("[data-qaid=product_block]");

                if (products.isEmpty()) {
                    System.out.println("Товари не знайдемо. Закінчюємо парсинг");
                    break;
                }

                // Collect products
                for (Element product : products) {
                    String title = product.select("[data-qaid=product_name]").text();
                    String price = product.select("[data-qaid=product_price]").text().replace('₴',' ').trim();
                    String link = "https://prom.ua" + product.select("[data-qaid=product_link]").attr("href");

                    productsList.add(new Product(title,price,link));

                    System.out.println("Назва: " + title);
                    System.out.println("Ціна: " + price);
                    System.out.println("Ссилка: " + link);
                    System.out.println("----------------------------------");
                }

                // increment
                pageCount++;

                // Next page find
                Element nextPageElement = doc.selectFirst("[data-qaid=next_page]");
                if (nextPageElement != null && pageCount < maxPages) {
                    String nextPageUrl = "https://prom.ua" + nextPageElement.attr("href");
                    currentUrl = nextPageUrl;
                } else {
                    hasNextPage = false; //Finish if end
                }

            } catch (Exception e) {
                System.out.println("Помилка: " + e.getMessage());
                hasNextPage = false;
            }
        }
        System.out.println("Парсинг завершено. Кількість запаршених страниць: " + pageCount);

        return productsList;
    }
    public List<Product> getProductsFromDatabase() {
        return productRepository.findAll();
    }

}
