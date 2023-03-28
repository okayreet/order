package com.catalogue.order;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/v1/order")
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllOrders() {
        log.info("request all orders available");
        return new ResponseEntity<>(orderService.getAllOrders(), HttpStatus.OK);
    }

    @GetMapping("/{order_id}")
    public ResponseEntity<?> getOrderById(@PathVariable Long order_id) {
        log.info("request order by ID: {}", order_id);
        return new ResponseEntity<>(orderService.getOrderById(order_id), HttpStatus.OK);
    }

    @PostMapping
    public void setOrder(@RequestBody Order order){
        log.info("set order : {}", order);
        orderService.setOrder(order);
    }
}