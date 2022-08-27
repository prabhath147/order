package com.order.service.service;

import java.time.LocalDate;
import java.util.List;

import com.order.service.dto.ItemDto;
import com.order.service.dto.OrdersDto;
import com.order.service.dto.RepeatOrderDto;
import com.order.service.dto.UserOptInDto;


public interface RepeatOrderService {
    RepeatOrderDto createOptIn(RepeatOrderDto repeatOrderRequest);
    
    RepeatOrderDto updateOptIn(RepeatOrderDto repeatOrderRequest);
    
    List<ItemDto> getItemsByProductId(Long productId,Integer pageNumber, Integer pageSize);
        
    List<UserOptInDto> getOptInToSendNotification(LocalDate date);
    
    OrdersDto getOptInIdAndPlaceOrder(Long id);
    
}
