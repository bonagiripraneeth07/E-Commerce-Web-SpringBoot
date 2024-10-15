package com.example.ecomboot.Controllers;

import com.example.ecomboot.model.Product;
import com.example.ecomboot.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin

public class ProductController {
    @Autowired
private ProductService service;
    @GetMapping("/products")
    public List<Product> getProducts(){
          return  service.getAllProducts();
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable  int id){
         Product product = service.getProduct(id);
         if(product!=null){
             return new  ResponseEntity<>(product, HttpStatus.OK);
         }else{
          return   new  ResponseEntity<>(HttpStatus.NOT_FOUND);
         }

    }
    @GetMapping("/product/{id}/image")
    public ResponseEntity<byte[]> getImageByProductId(@PathVariable int id ){
        Product product = service.getProduct(id);
        if(product!=null){
            return new  ResponseEntity<>(product.getImageData(), HttpStatus.OK);
        }else{
            return   new  ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart Product product , @RequestPart MultipartFile imageFile){
        try {
            Product savedProduct = service.addProduct(product,imageFile);
            return new ResponseEntity<>(product, HttpStatus.CREATED);
        } catch (IOException e) {
            return  new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/product/{id}")
    public ResponseEntity<String> UpdateProduct(@RequestPart Product product , @RequestPart MultipartFile imageFile){
        try {
            Product UpdateProduct = service.UpdateProduct(product,imageFile);
            return new ResponseEntity<>("Update Success", HttpStatus.CREATED);
        } catch (IOException e) {
            return  new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> DeleteProduct(@PathVariable int id){
        Product product = service.getProduct(id);

        if (product!=null){
            service.deleteProduct(id);
            return new  ResponseEntity<>("Deletion Successfull", HttpStatus.OK);
        }else{
            return new  ResponseEntity<>("Somthing went Wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/products/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword) {
        List<Product> products = service.searchProducts(keyword);
        System.out.println("searching with :" + keyword);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
