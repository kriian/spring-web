package com.geekbrains.spring.web.controllers;

import com.geekbrains.spring.web.aop.LogAspectSumWorkAllServiceMethods;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/statistic")
public class AspectController {

    private final LogAspectSumWorkAllServiceMethods logAspectServiceSumWorkAllMethods;

    @GetMapping
    public Map<String, Long> getTimeExecutionClassesServices() {
        return logAspectServiceSumWorkAllMethods.getClassNamesAndTheirWorkingHours();
    }
}