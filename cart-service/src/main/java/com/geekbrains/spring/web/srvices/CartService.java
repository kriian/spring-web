package com.geekbrains.spring.web.srvices;

import com.geekbrains.spring.web.dto.Cart;
import com.geekbrains.spring.web.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CacheManager cacheManager;
    private final RestTemplate restTemplate;
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
            ProductDto product =
                    restTemplate.getForObject("http://localhost:8189/web-market-core/api/v1/products/" + id, ProductDto.class);
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

    @CachePut(value = "Cart", key = "#cartName")
    public Cart removeProductById(Long id, String cartName) {
        Cart cart = getCurrentCart(cartName);
        cart.removeProduct(id);
        return cart;
    }

    @CachePut(value = "Cart", key = "#cartName")
    public Cart decreaseProductInCartById(Long id, String cartName) {
        Cart cart = getCurrentCart(cartName);
        cart.decreaseProduct(id);
        return cart;
    }

}
