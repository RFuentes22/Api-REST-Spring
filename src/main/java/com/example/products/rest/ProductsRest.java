package com.example.products.rest;

import com.example.products.dao.ProductDAO;
import com.example.products.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController //REST service
@RequestMapping("products")
public class ProductsRest {

    @Autowired
    private ProductDAO productDAO;

    //@GetMapping //toma como base RequestMapping | localhost:8080
    @RequestMapping(value = "hello", method = RequestMethod.GET)
    public String hello() {
        return "Hello World";
    }

    @GetMapping
    public ResponseEntity<List<Product>> getProducts() {
        List<Product> productsList = productDAO.findAll();
        return ResponseEntity.ok(productsList);
    }

    @RequestMapping(value = "{productId}") // products/{productId}
    public ResponseEntity<Product> getProductById(@PathVariable("productId") Long productId) {
        Optional<Product> product = productDAO.findById(productId);
        //return ((product.isPresent()) ? ResponseEntity.ok(product.get()) : ResponseEntity.noContent().build());
        return (product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build()));
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product newProduct = productDAO.save(product);
        return ResponseEntity.ok(newProduct);
    }

    @DeleteMapping(value = "{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("productId") Long productId) {
        productDAO.deleteById(productId);
        return ResponseEntity.ok(null);
    }

    @PutMapping
    public ResponseEntity<Product> updateProduct(@RequestBody Product product) {
        Optional<Product> optionalProduct = productDAO.findById(product.getId());
        if (optionalProduct.isPresent()) {
            Product updateProduct = optionalProduct.get();
            updateProduct.setName(product.getName());
            productDAO.save(updateProduct);
            return ResponseEntity.ok(updateProduct);
        }
        else
            return ResponseEntity.notFound().build();
    }
}
