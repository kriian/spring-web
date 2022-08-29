package com.geekbrains.spring.web.services;

import com.geekbrains.spring.web.dto.Cart;
import com.geekbrains.spring.web.entities.Product;
import com.geekbrains.spring.web.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductsService productsService;
    private final CacheManager cacheManager;
    private Cart cart;

    @Value("${spring.cache.user.name}")
    private String CACHE_CART;

    @Cacheable(value = "Cart", key = "#cartName")
    public Cart getCurrentCart(String cartName) {
        cart = cacheManager.getCache(CACHE_CART).get(cartName, Cart.class);
        if (!Optional.ofNullable(cart).isPresent()) {
            cart = new Cart(cartName, cacheManager);
            cacheManager.getCache(CACHE_CART).put(cartName, cart);
        }
        return cart;
    }

    @CachePut(value = "Cart", key = "#cartName")
    public Cart addProductByIdToCart(Long id, String cartName) {
        Cart cart = getCurrentCart(cartName);
        if (!cart.addProductCount(id)) {
            Product product = productsService.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Не удалось найти продукт"));
            cart.addProduct(product);
        }
        return cart;
    }

    @CachePut(value = "Cart", key = "#cartName")
    public Cart clear(String cartName) {
        Cart cart = getCurrentCart(cartName);
        cart.clear();
        return cart;
    }

    public void removeProductById(Long id) {
        cart.removeProduct(id);
    }

    @CachePut(value = "Cart", key = "#cartName")
    public Cart decreaseProductInCartById(Long id, String cartName) {
        Cart cart = getCurrentCart(cartName);
        cart.decreaseProduct(id);
        return cart;
    }

}
