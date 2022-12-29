package org.spring.web.market.services;

import org.spring.web.market.api.dto.CartDTO;
import org.spring.web.market.api.dto.CartItemDTO;
import org.spring.web.market.entities.Order;
import org.spring.web.market.entities.OrderItem;
import org.spring.web.market.entities.User;
import org.spring.web.market.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    private OrderRepository orderRepository;

    private OrderStatusService orderStatusService;

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Autowired
    public void setOrderStatusService(OrderStatusService orderStatusService) {
        this.orderStatusService = orderStatusService;
    }

    @Transactional
    public Order makeOrder(CartDTO cart, User user) {
        var order = new Order();
        order.setId(0L);
        order.setUser(user);
        order.setStatus(orderStatusService.getStatusById(1L));
        order.setPrice(cart.getTotalCost());
        var orderItems = new ArrayList<OrderItem>();
        for (CartItemDTO cartItemDto : cart.getItems()) {
            var oi = new OrderItem();
            oi.setOrder(order);
            oi.setId(cartItemDto.getId());
            oi.setProduct(cartItemDto.getProduct());
            oi.setQuantity(cartItemDto.getQuantity());
            oi.setItemPrice(cartItemDto.getItemPrice());
            oi.setTotalPrice(cartItemDto.getTotalPrice());
        }
        order.setOrderItems(orderItems);
        return order;
    }

    public List<Order> getAllOrders() {
        return (List<Order>) orderRepository.findAll();
    }

    public Order findById(Long id) {
        return orderRepository.findById(id).get();
    }

    public Order saveOrder(Order order) {
        Order orderOut = orderRepository.save(order);
        orderOut.setConfirmed(true);
        return orderOut;
    }

    public Order changeOrderStatus(Order order, Long statusId) {
        order.setStatus(orderStatusService.getStatusById(statusId));
        return saveOrder(order);
    }
}
