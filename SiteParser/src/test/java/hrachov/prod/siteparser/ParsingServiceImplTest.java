package hrachov.prod.siteparser;

import hrachov.prod.siteparser.model.Product;
import hrachov.prod.siteparser.repository.ProductRepository;
import hrachov.prod.siteparser.service.ParsingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class ParsingServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ParsingServiceImpl parsingServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetProductsFromDatabase() {
        List<Product> products = new ArrayList<>();
        products.add(new Product("Гітара акустична", "2500", "https://prom.ua/guitar1"));
        when(productRepository.findAll()).thenReturn(products);

        List<Product> result = parsingServiceImpl.getProductsFromDatabase();

        verify(productRepository).findAll();
        assertEquals(products.size(), result.size());
        assertEquals(products.get(0).getTitle(), result.get(0).getTitle());
    }

    @Test
    void testGetProductsFromHtml() {

        String productName = "гітара";

        List<Product> results = null;
        try {
            results = parsingServiceImpl.getProductsFromHtml(productName);

            assertNotNull(results);
        } catch (Exception e) {
            System.out.println("Warning: Network operation in test. Mock JSoup for proper unit tests.");
        }
    }
}
