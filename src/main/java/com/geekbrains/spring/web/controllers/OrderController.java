package com.geekbrains.spring.web.controllers;

import com.geekbrains.spring.web.converters.OrderConvertor;
import com.geekbrains.spring.web.dto.OrderDetailsDto;
import com.geekbrains.spring.web.dto.OrderDto;
import com.geekbrains.spring.web.entities.User;
import com.geekbrains.spring.web.services.OrderService;
import com.geekbrains.spring.web.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final UserService userService;
    private final OrderService orderService;
    private final OrderConvertor orderConvertor;

    @PostMapping("/{cartName}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(Principal principal, @RequestBody OrderDetailsDto orderDetailsDto, @PathVariable String cartName) {
        User user = userService.findByUsername(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        orderService.createOrder(user, orderDetailsDto, cartName);
    }

    @GetMapping
    public List<OrderDto> getCurrentOrder(Principal principal) {
        return orderService.findOrderByUsername(principal.getName()).stream()
                .map(orderConvertor::entityToDto).collect(Collectors.toList());
    }

}
