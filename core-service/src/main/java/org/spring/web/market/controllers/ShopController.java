package org.spring.web.market.controllers;

import lombok.RequiredArgsConstructor;
import org.spring.web.market.entities.Order;
import org.spring.web.market.entities.Product;
import org.spring.web.market.integrations.CartServiceIntegration;
import org.spring.web.market.repositories.specifications.ProductSpecs;
import org.spring.web.market.services.OrderService;
import org.spring.web.market.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@RequestMapping("/shop")
@RequiredArgsConstructor
public class ShopController {
    private final CartServiceIntegration cartServiceIntegration;
    private static final int INITIAL_PAGE = 0;
    private int PAGE_SIZE = 10;

    //    private MailService mailService;
    //private UserService userService;
    private OrderService orderService;
    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

//    @Autowired
//    public void setUserService(UserService userService) {
//        this.userService = userService;
//    }

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

//    @Autowired
//    public void setDeliverAddressService(DeliveryAddressService deliverAddressService) {
//        this.deliverAddressService = deliverAddressService;
//    }

//    @Autowired
//    public void setMailService(MailService mailService) {
//        this.mailService = mailService;
//    }

    @GetMapping
    public String shopPage(Model model,
                           @RequestParam(value = "page") Optional<Integer> page,
                           @RequestParam(value = "word", required = false) String word,
                           @RequestParam(value = "min", required = false) Double min,
                           @RequestParam(value = "max", required = false) Double max,
                           @RequestParam(value = "lines", required = false) Integer lines
    ) {
        final int currentPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

        Specification<Product> spec = Specification.where(null);
        StringBuilder filters = new StringBuilder();
        if (word != null) {
            spec = spec.and(ProductSpecs.titleContains(word));
            filters.append("&word=").append(word);
        }
        if (min != null) {
            spec = spec.and(ProductSpecs.priceGreaterThanOrEq(min));
            filters.append("&min=").append(min);
        }
        if (max != null) {
            spec = spec.and(ProductSpecs.priceLesserThanOrEq(max));
            filters.append("&max=").append(max);
        }
        if (lines == null) {
            lines = PAGE_SIZE;
        } else if (PAGE_SIZE != lines) {
            PAGE_SIZE = lines;
        }

        Page<Product> products = productService.getProductsWithPagingAndFiltering(
                currentPage, PAGE_SIZE, spec);

        model.addAttribute("products", products.getContent());
        model.addAttribute("page", currentPage);
        model.addAttribute("totalPage", products.getTotalPages());
        model.addAttribute("filters", filters.toString());
        model.addAttribute("min", min);
        model.addAttribute("max", max);
        model.addAttribute("word", word);
        model.addAttribute("lines", lines);

        return "shop-page";
    }

    @GetMapping("/cart/add/{id}")
    public void addProductToCart(@PathVariable("id") Long id, Principal principal) {
        cartServiceIntegration.addToCart(id, principal.getName());
    }

    @PostMapping("/order/confirm")
    public String orderConfirm(Model model, @ModelAttribute(name = "order") Order orderFromFrontend, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        Order order = orderService.makeOrder(
                cartServiceIntegration.getCart(principal.getName()),
                principal.getName());
        order.setDeliveryAddress(orderFromFrontend.getDeliveryAddress());
        order.setDeliveryDate(LocalDateTime.now().plusDays(7));
        order.setDeliveryPrice(BigDecimal.ZERO);
        order = orderService.saveOrder(order);
        model.addAttribute("order", order);
        cartServiceIntegration.clearCart(principal.getName());
        return "order-filler";
    }

    @GetMapping("/order/result/{id}")
    public String orderConfirm(Model model, @PathVariable(name = "id") Long id, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        // todo ждем до оплаты, проверка безопасности и проблема с повторной отправкой письма сделать одноразовый вход
//        User user = userService.findByUserName(principal.getName());
        Order confirmedOrder = orderService.findById(id);
//        if (!user.getId().equals(confirmedOrder.getUser().getId())) {
//            return "redirect:/";
//        }
//        mailService.sendOrderMail(confirmedOrder);
        model.addAttribute("order", confirmedOrder);
        return "order-result";
    }
}
