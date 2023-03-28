package com.catalogue.order;

import java.util.Date;
import java.util.List;

import org.springframework.boot.autoconfigure.info.ProjectInfoProperties.Build;
import org.springframework.stereotype.Service;

import com.catalogue.order.rabbitmq.RabbitMQMessageProducer;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final RabbitMQMessageProducer rabbitMQMessageProducer;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long order_id) {
        return orderRepository.findById(order_id).get();
    }

    public void setOrder(Order order) {

        Order completedOrder = Order.builder()
                .customerId(order.getCustomerId())
                .productId(order.getProductId())
                .orderedAt(new Date())
                .price(order.getPrice())
                .quantity(order.getQuantity())
                .totalPayment(order.getPrice() * order.getQuantity())
                .build();

        orderRepository.saveAndFlush(completedOrder);
        rabbitMQMessageProducer.publish(completedOrder, "orderTopicExchange", "orderRoutingKey");
    }
}