package com.example.PetstoreApplication.controllers.user;

import com.example.PetstoreApplication.models.Cart;
import com.example.PetstoreApplication.models.Order;
import com.example.PetstoreApplication.models.Product;
import com.example.PetstoreApplication.repositories.CartRepository;
import com.example.PetstoreApplication.repositories.OrderRepository;
import com.example.PetstoreApplication.security.PersonDetails;
import com.example.PetstoreApplication.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class UserController {
  private final OrderRepository orderRepository;
  private final CartRepository cartRepository;
  private final ProductService productService;
  @Autowired
  public UserController(OrderRepository orderRepository, CartRepository cartRepository, ProductService productService) {
    this.orderRepository = orderRepository;
    this.cartRepository = cartRepository;
    this.productService = productService;
  }

  @GetMapping("/index")
  public String index(Model model)
  {
    model.addAttribute("products", productService.getAllProduct());
    return "index";
  }

  @GetMapping("/user")
  public String admin(Model model){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
    String role = personDetails.person().getRole();

    if(role.equals("ROLE_ADMIN")) { return "redirect:/admin"; }

    model.addAttribute("products", productService.getAllProduct());
    model.addAttribute("found_product", productService.getAllProduct());
    return "user/user";
  }

  @GetMapping("/cart")
  public String cart(Model model)
  {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
    Long id_person = personDetails.person().getId();

    List<Cart> cartList = cartRepository.findByPersonId(id_person);
    List<Product> productsList = new ArrayList<>();

    for (Cart cart: cartList) {
      productsList.add(productService.getProductId(cart.getProductId()));
    }

    float sum = 0;
    for (Product product: productsList) {
      sum += product.getPrice();
    }
    model.addAttribute("sum", sum);
    model.addAttribute("cart_product", productsList);
    return "user/cart";
  }

  @GetMapping("/cart/add/{id}")
  public String addProductInCart(@PathVariable("id") Long id, Model model)
  {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
    Long id_person = personDetails.person().getId();

    Product product = productService.getProductId(id);

    Cart cart = new Cart(id_person, product.getId());
    cartRepository.save(cart);
    return "redirect:/cart";
  }

  @GetMapping("/cart/delete/{id}")
  public String deleteProductCart(Model model, @PathVariable("id") Long id){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
    Long id_person = personDetails.person().getId();

    cartRepository.deleteCartByProductId(id);
    return "redirect:/cart";
  }

  @GetMapping("/order/create")
  public String order()
  {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
    Long id_person = personDetails.person().getId();

    List<Cart> cartList = cartRepository.findByPersonId(id_person);
    List<Product> productsList = new ArrayList<>();

    // Получаем продукты из корзины по id
    for (Cart cart: cartList) {
      productsList.add(productService.getProductId(cart.getProductId()));
    }

    float totalSum = 0;
    for (Product product: productsList){
      totalSum += product.getPrice();
    }

    String uuid = UUID.randomUUID().toString();

    for (Product product: productsList){
      Order newOrder = new Order(uuid, product, personDetails.person(), 1,
              product.getPrice());
      orderRepository.save(newOrder);
      cartRepository.deleteCartByProductId(product.getId());
    }
    return "redirect:/orders";
  }

  @GetMapping("/orders")
  public String ordersUser(Model model){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
    List<Order> orderList =
            orderRepository.findByPersonId(personDetails.person().getId());

    model.addAttribute("orders", orderList);
    return "order/orderList";
  }
}