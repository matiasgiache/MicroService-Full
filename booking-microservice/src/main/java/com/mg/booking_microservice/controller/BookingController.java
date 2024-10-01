package com.mg.booking_microservice.controller;

import com.mg.booking_microservice.DTO.OrderDTO;
import com.mg.booking_microservice.entity.Order;
import com.mg.booking_microservice.repository.OrderRepository;
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

    @PostMapping("/order")
    public String saveOrder(@RequestBody OrderDTO orderDTO){

        Order order= new Order();

        order.setOrderNo(UUID.randomUUID().toString());
        order.setOrderItems(orderDTO.getOrderItems());

        orderRepository.save(order);

        return "Order Saved";
    }
}
