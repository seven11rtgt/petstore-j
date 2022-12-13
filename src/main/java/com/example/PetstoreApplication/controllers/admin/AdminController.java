package com.example.PetstoreApplication.controllers.admin;

import com.example.PetstoreApplication.models.Image;
import com.example.PetstoreApplication.models.Product;
import com.example.PetstoreApplication.repositories.CategoryRepository;
import com.example.PetstoreApplication.services.OrderService;
import com.example.PetstoreApplication.services.PersonService;
import com.example.PetstoreApplication.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
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
  private final PersonService personService;
  private final OrderService orderService;

  @Autowired
  public AdminController(ProductService productService,
                         CategoryRepository categoryRepository,
                         PersonService personService,
                         OrderService orderService) {
    this.productService = productService;
    this.categoryRepository = categoryRepository;
    this.personService = personService;
    this.orderService = orderService;
  }

  @GetMapping("")
  public String admin(Model model){
    model.addAttribute("products", productService.getAllProduct());
    return "admin/admin";
  }

  @GetMapping("/users")
  public String getAllUsers(Model model){
    model.addAttribute("userList", personService.getAllUsers());
    return "user/userList";
  }

  // Изменяем роль пользователя
  @GetMapping("/role/edit/{id}")
  public String editUserRole(Model model, @PathVariable("id") String id){
    Long userId = Long.parseLong(id, 10);
    System.out.println("str id " + id);
    System.out.println("long id " + userId);

    String currentRole = personService.findById(userId).getRole();

    switch (currentRole)
    {
      case "ROLE_USER" -> personService.setRole(personService.findById(userId), "ROLE_ADMIN");

      case "ROLE_ADMIN" -> personService.setRole(personService.findById(userId),
              "ROLE_USER");
    }

    model.addAttribute("userList", personService.getAllUsersSorted());
    return "redirect:/admin/users";
  }

  @GetMapping("/products")
  public String getAllProductsAdmin(Model model,
                                    @RequestHeader(value = HttpHeaders.REFERER, required = false) final String referrer)
  {
    model.addAttribute("products", productService.getAllProduct());
    model.addAttribute("previousUrl", referrer);
    return "/product/productListAdmin";
  }

  // http:/localhost:8080/admin/product/add
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
    @RequestParam("files") MultipartFile[] files)
          throws IOException {

      if(bindingResult.hasErrors()) return "product/addProduct";

      for (MultipartFile file: files)
      {
        File uploadDir = new File(uploadPath);
        if(!uploadDir.exists()){ uploadDir.mkdir(); }

        String uuidFileName = UUID.randomUUID().toString();

        File newFile = new File(uploadPath + "/" + uuidFileName);
        file.transferTo(newFile);
        Image image = new Image();

        image.setFileName(uuidFileName);
        image.setProduct(product);
        product.addImageToProduct(image);
      }

      productService.saveProduct(product);
      return "redirect:/admin";
  }

  // Удаляем товар
  @GetMapping("/product/delete/{id}")
  public String deleteProduct(@PathVariable("id") Long id){
    productService.deleteProduct(id);
    return "redirect:/admin/products";
  }

  // Отображаем страницу с возможностью редактирования товаров
  @GetMapping("/product/edit/{id}")
  public String editProduct(Model model,
                            @PathVariable("id") Long id){
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
      return "redirect:/admin/products";
  }

  // Отображаем страницу с возможностью редактирования заказов
  @GetMapping("/orders")
  public String editProduct(Model model){
    model.addAttribute("orders", orderService.getAllOrders());

    List<String> options = new ArrayList<>();
    options.add("Создан");
    options.add("Обработан");
    options.add("Отправлен");
    options.add("Получен");
    model.addAttribute("options", options);

    return "order/orderListAdmin";
  }
}