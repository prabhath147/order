package com.order.service.controller;

import com.order.service.dto.RepeatOrderDto;

import com.order.service.model.RepeatOrder;
import com.order.service.service.RepeatOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

import javax.validation.Valid;

@RestController
@RequestMapping("/order")
public class RepeatOrderController {

    @Autowired
    private RepeatOrderService repeatOrderService;

    @PostMapping("/optin")
    public ResponseEntity<RepeatOrderDto> createOptIn(@Valid @RequestBody RepeatOrderDto repeatOrderRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(repeatOrderService.createOptIn(repeatOrderRequest));
    }
    
    @PutMapping("/update-optin")
    public ResponseEntity<RepeatOrderDto> UpdateOptIn(@Valid @RequestBody RepeatOrderDto repeatOrderRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(repeatOrderService.updateOptIn(repeatOrderRequest));
    }
    
    @PostMapping(value = "optin/{id}")
    public ResponseEntity<?> placeOrder(@PathVariable("id") Long id){
        try{
        	//repeatOrderService.findAllLeastPriceProducts(date);
        	return new ResponseEntity<>(repeatOrderService.getOptInIdAndPlaceOrder(id), HttpStatus.OK);
            
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }


}
