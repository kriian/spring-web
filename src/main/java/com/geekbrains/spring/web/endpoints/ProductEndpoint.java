package com.geekbrains.spring.web.endpoints;

import com.geekbrains.spring.web.services.ProductsService;
import com.geekbrains.spring.web.soap.soap.GetAllProductsRequest;
import com.geekbrains.spring.web.soap.soap.GetAllProductsResponse;
import com.geekbrains.spring.web.soap.soap.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.xml.datatype.DatatypeConfigurationException;

@Endpoint
@RequiredArgsConstructor
public class ProductEndpoint {

    private static final String NAMESPACE_URI = "http://example.com/api/products";

    private final ProductsService productsService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllProductsRequest")
    @ResponsePayload
    public GetAllProductsResponse getProducts(@RequestPayload GetAllProductsRequest request) {
        GetAllProductsResponse response = new GetAllProductsResponse();
        productsService.findAll().forEach(p -> {
            Product product = new Product() {{
                setId(p.getId());
                setTitle(p.getTitle());
                setPrice(p.getPrice());
            }};
            response.getProducts().add(product);
        });
        return response;
    }


}
