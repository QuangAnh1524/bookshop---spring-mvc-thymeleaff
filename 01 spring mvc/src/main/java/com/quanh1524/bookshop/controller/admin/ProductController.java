package com.quanh1524.bookshop.controller.admin;

import com.quanh1524.bookshop.domain.Product;
import com.quanh1524.bookshop.service.ProductService;
import com.quanh1524.bookshop.service.UploadService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller

public class ProductController {
    private final UploadService uploadService;
    private final ProductService productService;

    public ProductController(UploadService uploadService, ProductService productService) {
        this.uploadService = uploadService;
        this.productService = productService;
    }

    @GetMapping("/admin/product")
    public String getProductDashboard(Model model) {
        List<Product> products = this.productService.getAllProducts();
        model.addAttribute("productsFromView", products);
        return "admin/dashboard/product/showProduct";
    }

    @GetMapping("/admin/product/create")
    public String createProductPage(Model model) {
        Product product = new Product();
        model.addAttribute("createForm", product);
        return "admin/dashboard/product/createProduct";
    }

    @PostMapping("/admin/product/create")
    public String handleCreateProduct(@ModelAttribute("createForm") @Valid Product product, BindingResult bindingResult,
                                      @RequestParam("file") MultipartFile file) throws Exception {
        List<FieldError> errors = bindingResult.getFieldErrors();
        for (FieldError error : errors) {
            System.out.println(error.getField() + " - " + error.getDefaultMessage());
        }
        if (bindingResult.hasErrors()) {
            return "admin/dashboard/product/createProduct";
        }
        //upload image
        String image = this.uploadService.handleSaveFile(file, "product");
        product.setImage(image);

        this.productService.createProduct(product);
        return "redirect:/admin/product";
    }

    @GetMapping("/admin/product/{id}")
    public String detailProduct(Model model, @PathVariable long id) {
        Product product = this.productService.getProductById(id);
        model.addAttribute("productDetails", product);
        return "admin/dashboard/product/detailProduct";
    }

    @GetMapping("/admin/product/update/{id}")
    public String updateProductPage(Model model, @PathVariable long id) {
        Product product = this.productService.getProductById(id);
        model.addAttribute("updateForm", product);
        return "admin/dashboard/product/updateProduct";
    }

    @PostMapping("/admin/product/update")
    public String updateProduct(@ModelAttribute("updateForm") Product product, @RequestParam("file") MultipartFile file)
                                throws IOException {
        if (!file.isEmpty()) {
            String fileName = this.uploadService.handleSaveFile(file, "product");
            product.setImage(fileName);
        } else {
            Product existingProduct = this.productService.getProductById(product.getId());
            product.setImage(existingProduct.getImage());
        }
        this.productService.saveProductServie(product);
        return "redirect:/admin/product";
    }

    @GetMapping("/admin/product/delete/{id}")
    public String getDeleteProductPage(Model model, @PathVariable long id) {
        Product deleteProduct = this.productService.getProductById(id);
        model.addAttribute("deleteForm", deleteProduct);
        return "admin/dashboard/product/deleteProduct";
    }

    @PostMapping("/admin/product/delete")
    public String deleteProduct(@ModelAttribute("deleteForm") Product product) {
        this.productService.deleteProductById(product.getId());
        return "redirect:/admin/product";
    }

}
