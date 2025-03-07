package hrachov.prod.siteparser.controller;

import hrachov.prod.siteparser.model.Product;
import hrachov.prod.siteparser.repository.ProductRepository;
import hrachov.prod.siteparser.service.ExcelService;
import hrachov.prod.siteparser.service.ParsingService;
import hrachov.prod.siteparser.service.ProductService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

@Controller
public class ParsingController {
    @Autowired
    private ParsingService parsingService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ExcelService excelService;
    @Autowired
    private ProductService productService;

    // Главная страница с формой
    @GetMapping("/")
    public String index() {
        return "index";
    }

    // Обработчик запроса на парсинг
    @PostMapping("/parse")
    public String parseProducts(@RequestParam String productName, Model model) {
        productService.deleteAllProducts();
        List<Product> products = parsingService.getProductsFromHtml(productName);
        productRepository.saveAll(products);
        model.addAttribute("products", products);
        return "products";
    }
    @GetMapping("/products")
    public String showProducts(@RequestParam(name = "sort", required = false) String sort, Model model) {
        List<Product> products = productRepository.findAll();

        model.addAttribute("products", products);
        return "products";  // передаем список продуктов в шаблон
    }

    // Скачать Excel-файл
    @GetMapping("/download-excel")
    @ResponseBody
    public void downloadExcel(HttpServletResponse response) throws IOException {
        List<Product> products = productRepository.findAll();
        String filePath = "products.xlsx";
        excelService.exportToExcel(products, filePath);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=products.xlsx");
        response.getOutputStream().write(java.nio.file.Files.readAllBytes(java.nio.file.Path.of(filePath)));
        response.getOutputStream().flush();
    }
}
