package com.order.service.service.implementation;


import com.order.service.dto.AddressDto;
import com.order.service.dto.ItemDto;
import com.order.service.dto.ItemInOptInDto;
import com.order.service.dto.OrderDetailsDto;
import com.order.service.dto.OrdersDto;
import com.order.service.dto.PageableResponse;
import com.order.service.dto.ProductDto;
import com.order.service.dto.RepeatOrderDto;
import com.order.service.dto.RepeatOrderItemDto;
import com.order.service.dto.UserOptInDto;
import com.order.service.exception.ResourceException;
import com.order.service.model.RepeatOrder;
import com.order.service.model.RepeatOrderItem;
import com.order.service.repository.AddressRepository;
import com.order.service.repository.RepeatOrderRepository;
import com.order.service.service.OrdersService;
import com.order.service.service.RepeatOrderService;


import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class RepeatOrderServiceImplementation implements RepeatOrderService {

    @Autowired
    private RepeatOrderRepository repeatOrderRepository;

    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    RestTemplate restTemplate;
    
    @Autowired
    OrdersService ordersService;


    @Override
    public RepeatOrderDto createOptIn(RepeatOrderDto repeatOrderDto) {

    	RepeatOrder repeatOrder = modelMapper.map(repeatOrderDto, RepeatOrder.class);
    	repeatOrder.setDeliveryDate(LocalDate.now().plusDays(5));
        RepeatOrderDto response=modelMapper.map(repeatOrderRepository.save(repeatOrder), RepeatOrderDto.class);

        return response;
    }

	@Override
	public RepeatOrderDto updateOptIn(RepeatOrderDto repeatOrderDto) {
		RepeatOrder newRepeatOrder=modelMapper.map(repeatOrderDto, RepeatOrder.class);
		Optional<RepeatOrder> repeatOrder=repeatOrderRepository.findById(newRepeatOrder.getId());		
		if(repeatOrder.isEmpty()) {
			throw new ResourceException("ReapeatOrder", "repeatOrder", repeatOrderDto, ResourceException.ErrorType.FOUND);
		}
		try{
			return modelMapper.map(repeatOrderRepository.save(newRepeatOrder), RepeatOrderDto.class);
		}
		catch(Exception e) {
            throw new ResourceException("RepeatOrder", "repeatOrder", repeatOrderDto, ResourceException.ErrorType.CREATED, e);
		}
		
	}

	@Override
	public List<ItemDto> getItemsByProductId(Long productId,Integer pageNumber, Integer pageSize) {
		HttpHeaders headers = new HttpHeaders();
	      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	      headers.setBasicAuth("user", "e70dc61e-25cb-414a-b195-d83c70775e72");
	      HttpEntity <String> entity = new HttpEntity<String>(headers);	      
	      PageableResponse<ItemInOptInDto> res= restTemplate.exchange("http://localhost:8082/pharmacy/item/get-item-by-productid/"+productId+"?pageNumber="+pageNumber+"&pageSize="+pageSize, HttpMethod.GET, entity, new ParameterizedTypeReference<PageableResponse<ItemInOptInDto>>() {}).getBody(); 
	      List<ItemDto> itemDtoList=new ArrayList<>();
	      for(ItemInOptInDto obj:res.getData()) {
	    	  ItemDto item=new ItemDto();
	    	  item.setItemIdFk(obj.getItemId());
	    	  item.setPrice(obj.getPrice());
	    	  item.setQuantity(obj.getItemQuantity());
	    	  itemDtoList.add(item);
	      }
		return itemDtoList;
	}
	
	@Override
	public OrdersDto getOptInIdAndPlaceOrder(Long id) {
		OrdersDto order=new OrdersDto();
		OrdersDto orderResult=new OrdersDto();
		//List<UserOptInDto> userItemsList= new ArrayList<>();
		Set<ItemDto> itemDtoSet=new HashSet<>();
		Optional<RepeatOrder> repeatOrders= repeatOrderRepository.findById(id);
		if(repeatOrders.isEmpty()) {
			throw new ResourceException("ReapeatOrder", "repeatOrder", repeatOrders, ResourceException.ErrorType.FOUND);
		}
		RepeatOrderDto repeatOrderDto=modelMapper.map(repeatOrders.get(), RepeatOrderDto.class);
		//UserOptInDto userItem=new UserOptInDto();	
		Double price=0D;
		for(RepeatOrderItemDto rItem:repeatOrderDto.getRepeatOrderItems()) {	
				List<ItemDto> itemList=getItemsByProductId(rItem.getProduct().getProductIdFk(),0,10);
				if(itemList.size()>0) {
					for(ItemDto _item:itemList) {
						if(_item.getQuantity()>=rItem.getProduct().getQuantity()) {
							price=price+_item.getPrice()*rItem.getProduct().getQuantity();
							itemDtoSet.add(_item);	
							break;
						}
					}			
				}
		}
		if(price>0) {
			order.setItems(itemDtoSet);
			order.setOrderAddress(setAddress(repeatOrderDto));
			order.setPrice(price);
			order.setUserId(repeatOrderDto.getUserId());
			order.setQuantity(Long.valueOf(itemDtoSet.size()));
			orderResult=ordersService.createOrder(order);
		}
		if(orderResult!=null) {
			repeatOrderDto.setDeliveryDate(LocalDate.now().plusDays(repeatOrderDto.getIntervalInDays()));
			updateOptIn(repeatOrderDto);
		}
		return orderResult;
	}
	
	public AddressDto setAddress(RepeatOrderDto repeatOrderDto) {
		AddressDto address=new AddressDto();
		address.setCity(repeatOrderDto.getAddress().getCity());
		address.setCountry(repeatOrderDto.getAddress().getCountry());
		address.setPinCode(repeatOrderDto.getAddress().getPinCode());
		address.setState(repeatOrderDto.getAddress().getState());
		address.setStreet(repeatOrderDto.getAddress().getStreet());
		return address;
	}

	

	@Override
	public List<UserOptInDto> getOptInToSendNotification(LocalDate date) {
		LocalDate notificationDate=date.minusDays(3);
		List<UserOptInDto> userItemsList= new ArrayList<>();
		HashMap<Long,Double> map=new HashMap<>();
		
		List<RepeatOrderDto> repeatOrderDtoList=new ArrayList<>();
		List<RepeatOrder> allRepeatOrders= repeatOrderRepository.findAllByDeliveryDate(notificationDate);
		for(RepeatOrder i:allRepeatOrders) {
			repeatOrderDtoList.add(modelMapper.map(i, RepeatOrderDto.class));
		}
		for(RepeatOrderDto repeatOrder: repeatOrderDtoList) {
			UserOptInDto userItem=new UserOptInDto();	
			Double price=0D;
			for(RepeatOrderItemDto rItem:repeatOrder.getRepeatOrderItems()) {
				if(map.containsKey(rItem.getProduct().getProductIdFk())) {
					price+=map.get(rItem.getProduct().getProductIdFk())*rItem.getProduct().getQuantity();
				}
				else {
					ItemDto _item=getItemByProductId(rItem.getProduct().getProductIdFk());
					
					if(_item.getItemIdFk()!=null) {
						price=price+_item.getPrice()*rItem.getProduct().getQuantity();
						
						map.put(rItem.getProduct().getProductIdFk(), _item.getPrice());
					}
				}		
			}
			if(price>0) {
				userItem.setOptInId(repeatOrder.getId());
				userItem.setUserId(repeatOrder.getUserId());
				userItem.setTotalPrice(price);
				userItemsList.add(userItem);
			}		
		}
		for(UserOptInDto udto:userItemsList) {
			System.out.println(udto);
		}
		return userItemsList;
	}
	
	public ItemDto getItemByProductId(Long productId) {
		HttpHeaders headers = new HttpHeaders();
	      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	      headers.setBasicAuth("user", "e70dc61e-25cb-414a-b195-d83c70775e72");
	      HttpEntity <String> entity = new HttpEntity<String>(headers);	      
	      PageableResponse<ItemInOptInDto> res= restTemplate.exchange("http://localhost:8082/pharmacy/item/get-item-by-productid/"+productId+"?pageNumber="+0+"&pageSize="+1, HttpMethod.GET, entity, new ParameterizedTypeReference<PageableResponse<ItemInOptInDto>>() {}).getBody(); 
	      ItemDto item=new ItemDto();
	      for(ItemInOptInDto obj:res.getData()) {
	    	  
	    	  item.setItemIdFk(obj.getItemId());
	    	  item.setPrice(obj.getPrice());
	    	  item.setQuantity(obj.getItemQuantity());
	    	  	      }
		return item;
	}

	


}
