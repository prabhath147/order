package com.order.service.service.implementation;

import com.order.service.dto.CartDto;
import com.order.service.dto.ItemDto;
import com.order.service.exception.ResourceException;
import com.order.service.model.Cart;
import com.order.service.model.Item;
import com.order.service.repository.CartRepository;
import com.order.service.repository.ProductRepository;
import com.order.service.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class CartServiceImplementation implements CartService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ModelMapper modelMapper;


    @Override
    public CartDto getCart(Long userId) {
        return modelMapper.map(getIfExistsElseCreate(userId), CartDto.class);
    }

    @Override
    public CartDto addToCart(Long userId, ItemDto itemDto) {
        Cart cart = getIfExistsElseCreate(userId);
        cart.add(modelMapper.map(itemDto, Item.class));
        return updateCart(cart);
    }

    @Override
    public CartDto checkout(Long userId, CartDto cartDto) {
        return updateCart(modelMapper.map(cartDto, Cart.class));
    }

    @Override
    public CartDto addToCart(Long userId, List<ItemDto> itemDtoList) {
        List<Item> itemList = new ArrayList<>();
        for (ItemDto itemDto : itemDtoList) itemList.add(modelMapper.map(itemDto, Item.class));
        Cart cart = getIfExistsElseCreate(userId);
        cart.add(itemList);
        return updateCart(cart);
    }

    @Override
    public CartDto removeFromCart(Long userId, ItemDto itemDto) {
        Cart cart = getIfExistsElseCreate(userId);
        cart.removeItem(modelMapper.map(itemDto, Item.class));
        return updateCart(cart);
    }

    @Override
    public CartDto emptyCart(Long userId) {
        Cart cart = getIfExistsElseCreate(userId);
        cart.emptyCart();
        return updateCart(cart);

    }

    @Override
    public Cart getIfExistsElseCreate(Long userId) {
        Optional<Cart> optionalCart = cartRepository.findById(userId);
        if (optionalCart.isEmpty()) {
            Cart cart = new Cart(userId);
            try {
                return cartRepository.save(cart);
            } catch (Exception e){
                throw new ResourceException("Cart", "cart", cart, ResourceException.ErrorType.CREATED, e);
            }
        } else {
            return optionalCart.get();
        }
    }

    private CartDto updateCart(Cart cart) {
        try {
            cart.doCalc();
            return modelMapper.map(cartRepository.save(cart), CartDto.class);
        } catch (Exception e) {
            throw new ResourceException("Cart", "cart", cart, ResourceException.ErrorType.UPDATED, e);
        }
    }

}
