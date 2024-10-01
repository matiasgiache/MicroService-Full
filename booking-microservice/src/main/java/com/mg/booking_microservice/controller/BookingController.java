package com.mg.booking_microservice.controller;

import com.mg.booking_microservice.DTO.OrderDTO;
import com.mg.booking_microservice.client.StockClient;
import com.mg.booking_microservice.entity.Order;
import com.mg.booking_microservice.repository.OrderRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/booking")
public class BookingController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private StockClient stockClient;

    @PostMapping("/order")
    @CircuitBreaker(fallbackMethod = "fallbackToStockService", name = "stock-microservice")
    @Retry(name = "stock-microservice")
    public String saveOrder(@RequestBody OrderDTO orderDTO){

        boolean inStock = orderDTO.getOrderItems().stream()
                .allMatch(orderItem ->
                        stockClient.stockAvailable(orderItem.getCode()));

        if (inStock){

            Order order= new Order();

            order.setOrderNo(UUID.randomUUID().toString());
            order.setOrderItems(orderDTO.getOrderItems());

            orderRepository.save(order);

            return "Order Saved";
        }

        return "Order cannot be saved";

    }

    private String fallbackToStockService(OrderDTO orderDTO, Throwable throwable){
        return "Something went wrong, try later";
    }
}
