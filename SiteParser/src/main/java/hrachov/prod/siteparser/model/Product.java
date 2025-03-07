package hrachov.prod.siteparser.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="Назва")
    String title;

    @Column(name="Ціна")
    String price;

    @Column(name="Ссилка")
    String reference;

    public Product(String title, String price, String reference) {
        this.title = title;
        this.price = price;
        this.reference = reference;
    }

}
