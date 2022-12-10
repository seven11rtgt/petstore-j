package com.example.petstore.controllers.admin;

import com.example.petstore.models.Image;
import com.example.petstore.models.Product;
import com.example.petstore.repositories.CategoryRepository;
import com.example.petstore.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
//@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
public class AdminController {
  @Value("${upload.path}")
  private String uploadPath;

  private final ProductService productService;

  private final CategoryRepository categoryRepository;

  @Autowired
  public AdminController(ProductService productService, CategoryRepository categoryRepository) {
    this.productService = productService;
    this.categoryRepository = categoryRepository;
  }

  // @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
  @GetMapping("")
  public String admin(Model model){
    model.addAttribute("products", productService.getAllProduct());
    return "admin/admin";
  }

  // http:8080/localhost/admin/product/add
  // Отображаем страницу с возможностью добавления товаров
  @GetMapping("/product/add")
  public String addProduct(Model model){
    model.addAttribute("product", new Product());
    model.addAttribute("category", categoryRepository.findAll());
    return "product/addProduct";
  }

  // Добавляем продукт в БД через сервис-> репозиторий
  @PostMapping("/product/add")
  public String addProduct(
    @ModelAttribute("product") @Valid Product product,
    BindingResult bindingResult,
    @RequestParam("file_one") MultipartFile file_one,
    @RequestParam("file_two") MultipartFile file_two,
    @RequestParam("file_three") MultipartFile file_three,
    @RequestParam("file_four") MultipartFile file_four,
    @RequestParam("file_five") MultipartFile file_five)
          throws IOException {

      if(bindingResult.hasErrors())
      {
        return "product/addProduct";
      }

      List<MultipartFile> productImages = new ArrayList<MultipartFile>()
      { MultipartFile file_one;
        MultipartFile file_two;
        MultipartFile file_three;
        MultipartFile file_four;
        MultipartFile file_five;
      };

      for (MultipartFile file: productImages)
      {
        if(file == null) continue;

        File uploadDir = new File(uploadPath);
        if(!uploadDir.exists()){ uploadDir.mkdir(); }

        String uuidFile = UUID.randomUUID().toString();
        String resultFileName = uuidFile + "." + file.getOriginalFilename();

        file.transferTo(new File(uploadPath + "/" + resultFileName));
        Image image = new Image();
        image.setProduct(product);
        image.setFileName(resultFileName);
        product.addImageToProduct(image);
      }

      productService.saveProduct(product);
      return "redirect:/admin";
  }

  // Удаляем товар
  @GetMapping("/product/delete/{id}")
  public String deleteProduct(@PathVariable("id") Long id){
    productService.deleteProduct(id);
    return "redirect:/admin";
  }

  // Отображаем страницу с возможностью редактирования товаров
  @GetMapping("/product/edit/{id}")
  public String editProduct(Model model, @PathVariable("id") Long id){
    model.addAttribute("product", productService.getProductId(id));
    model.addAttribute("category", categoryRepository.findAll());
    return "product/editProduct";
  }

  // Обновляем данные о товаре в БД
  @PostMapping("/product/edit/{id}")
  public String editProduct(
          @ModelAttribute("product") @Valid Product product,
          BindingResult bindingResult,
          @PathVariable("id") Long id){

      if(bindingResult.hasErrors()) { return "product/editProduct"; }
      productService.updateProduct(id, product);
      return "redirect:/admin";
  }
}