package com.example.petstore.controllers.user;

import com.example.petstore.enumm.Status;
import com.example.petstore.models.Cart;
import com.example.petstore.models.Order;
import com.example.petstore.models.Product;
import com.example.petstore.repositories.CartRepository;
import com.example.petstore.repositories.OrderRepository;
import com.example.petstore.security.PersonDetails;
import com.example.petstore.services.ProductService;
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
  public UserController(OrderRepository orderRepository, CartRepository cartRepository, ProductService productService) {
    this.orderRepository = orderRepository;
    this.cartRepository = cartRepository;
    this.productService = productService;
  }

  @GetMapping("/index")
  public String index(Model model)
  {

    // Получаем объект аутентификации => с помощью Spring
    // SecurityContextHolder обращаемся к контексту и на нем вызываем метод аутентификации.
    // Из потока для текущего пользователя получаем объект, который был положен в сессию после аутентификации
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
    String role = personDetails.getPerson().getRole();

    if(role.equals("ROLE_ADMIN")) { return "redirect:/admin"; }
    model.addAttribute("products", productService.getAllProduct());
    return "user/index";
  }

  @GetMapping("/cart")
  public String cart(Model model)
  {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
    Long id_person = personDetails.getPerson().getId();

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
    Product product = productService.getProductId(id);
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
    Long id_person = personDetails.getPerson().getId();

    Cart cart = new Cart(id_person, product.getId());
    cartRepository.save(cart);
    return "redirect:/cart";
  }

  @GetMapping("/cart/delete/{id}")
  public String deleteProductCart(Model model, @PathVariable("id") Long id){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
    Long id_person = personDetails.getPerson().getId();

    cartRepository.deleteCartByProductId(id);
    return "redirect:/cart";
  }

  @GetMapping("/order/create")
  public String order()
  {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
    Long id_person = personDetails.getPerson().getId();

    List<Cart> cartList = cartRepository.findByPersonId(id_person);
    List<Product> productsList = new ArrayList<>();

    // Получаем продукты из корзины по id
    for (Cart cart: cartList) {
      productsList.add(productService.getProductId(cart.getProductId()));
    }

    float sum = 0;
    for (Product product: productsList){
      sum += product.getPrice();
    }

    String uuid = UUID.randomUUID().toString();

    for (Product product: productsList){
      Order newOrder = new Order(uuid, product, personDetails.getPerson(), 1, product.getPrice(), Status.Получен);
      orderRepository.save(newOrder);
      cartRepository.deleteCartByProductId(product.getId());
    }
    return "redirect:/orders";
  }

  @GetMapping("/orders")
  public String ordersUser(Model model){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
    List<Order> orderList = orderRepository.findByPerson(personDetails.getPerson());

    model.addAttribute("orders", orderList);
    return "/user/orderList";
  }
}