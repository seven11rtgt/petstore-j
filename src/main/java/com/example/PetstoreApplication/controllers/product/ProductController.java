package com.example.PetstoreApplication.controllers.product;

import com.example.PetstoreApplication.repositories.ProductRepository;
import com.example.PetstoreApplication.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/product")
public class ProductController {
  private final ProductRepository productRepository;
  private final ProductService productService;
  @Autowired
  public ProductController(ProductRepository productRepository, ProductService productService)
  {
    this.productRepository = productRepository;
    this.productService = productService;
  }

  @GetMapping("/products")
  public String getAllProducts(Model model)
  {
    model.addAttribute("products", productService.getAllProduct());
    return "/product/productList";
  }

  @GetMapping("/info/{id}")
  public String productInfo(@PathVariable("id") Long id,
                            Model model,
                            @RequestHeader(value = HttpHeaders.REFERER, required = false) final String referrer)
  {
    model.addAttribute("product", productService.getProductId(id));
    model.addAttribute("previousUrl", referrer);
    return "product/infoProduct";
  }

  @PostMapping("/search")
  public String productSearch(
    @RequestParam("search") String search,
    @RequestParam("ot") String ot,
    @RequestParam("do") String Do,
    @RequestParam(value = "price", required = false, defaultValue = "") String price,
    @RequestParam(value = "category", required = false, defaultValue = "") String category,
    Model model)
  {
    if(!ot.isEmpty() & !Do.isEmpty())
    {
      if(!price.isEmpty())
      {
        if(price.equals("sorted_by_ascending_price"))
        {
          if(!category.isEmpty())
          {
            if(category.equals("food"))
            {
              model.addAttribute("found_product",
                      productRepository.findByTitleAndCategoryOrderByPriceAsc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 1));
            }
            else if(category.equals("toys")){
              model.addAttribute("found_product", productRepository.findByTitleAndCategoryOrderByPriceAsc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 2));
            }
            else if(category.equals("cosmetics")){
              model.addAttribute("found_product", productRepository.findByTitleAndCategoryOrderByPriceAsc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 3));
            }
          }
        }
        else if (price.equals("sorted_by_descending_price"))
        {
          if(!category.isEmpty())
          {
            if(category.equals("food")){
              model.addAttribute("found_product", productRepository.findByTitleAndCategoryOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 1));
            }
            else if(category.equals("toys")){
              model.addAttribute("found_product", productRepository.findByTitleAndCategoryOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 2));
            }
            else if(category.equals("cosmetics")){
              model.addAttribute("found_product", productRepository.findByTitleAndCategoryOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 3));
            }
          }
        }
      }
      else {
        model.addAttribute("found_product", productRepository.findByTitleAndPriceGreaterThanEqualAndPriceLessThanEqual(search, Float.parseFloat(ot), Float.parseFloat(Do)));
      }
    }
    else {
      model.addAttribute("found_product", productRepository.findByTitleContainingIgnoreCase(search));
    }

    model.addAttribute("value_search", search);
    model.addAttribute("value_price_ot", ot);
    model.addAttribute("value_price_do", Do);
    model.addAttribute("products", productService.getAllProduct());

    return "product/productList";
  }


}