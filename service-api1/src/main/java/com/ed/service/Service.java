package com.ed.service;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("provider")
public interface Service {
}
