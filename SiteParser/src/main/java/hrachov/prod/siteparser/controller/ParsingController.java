package hrachov.prod.siteparser.controller;

import hrachov.prod.siteparser.model.Product;
import hrachov.prod.siteparser.repository.ProductRepository;
import hrachov.prod.siteparser.service.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    private ParsingService parsingServiceImpl;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ExcelService excelService;

    @Autowired
    private ProductService productServiceImpl;

    // Главная страница с формой
    @GetMapping("/")
    public String index() {
        return "index";
    }

    // Обработчик запроса на парсинг
    @PostMapping("/parse")
    public String parseProducts(@RequestParam String productName, Model model) {
        productServiceImpl.deleteAllProducts();
        List<Product> products = parsingServiceImpl.getProductsFromHtml(productName);
        productRepository.saveAll(products);
        return "redirect:/products";
    }

    @GetMapping("/products")
    public String showProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "title") String sort,
            @RequestParam(defaultValue = "asc") String dir,
            Model model) {

        Page<Product> productPage = productServiceImpl.getProducts(page, size, sort, dir);

        model.addAttribute("products", productPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("totalItems", productPage.getTotalElements());
        model.addAttribute("sortField", sort);
        model.addAttribute("sortDir", dir);
        model.addAttribute("reverseSortDir", dir.equals("asc") ? "desc" : "asc");

        return "products";
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