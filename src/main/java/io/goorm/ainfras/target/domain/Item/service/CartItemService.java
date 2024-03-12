package io.goorm.ainfras.target.domain.Item.service;

import io.goorm.ainfras.target.domain.Item.converter.ItemConverter;
import io.goorm.ainfras.target.domain.Item.domain.CartItem;
import io.goorm.ainfras.target.domain.Item.domain.Item;
import io.goorm.ainfras.target.domain.Item.dto.AddCartItemRequest;
import io.goorm.ainfras.target.domain.Item.dto.ItemDTO;
import io.goorm.ainfras.target.domain.Item.repository.CartItemRepository;
import io.goorm.ainfras.target.domain.User.domain.User;
import io.goorm.ainfras.target.global.interceptor.LogMonitoring;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartItemService {
    private final CartItemRepository cartItemRepository;
    private final ItemService itemService;
    private final Logger LOGGER = LoggerFactory.getLogger(CartItemService.class);
    private final ItemConverter itemConverter;

    @Transactional
    @LogMonitoring
    public List<CartItem> getCartItemsByUserId(Long userId) {
        List<CartItem> cartItems = cartItemRepository.findByUserId(userId);
        LOGGER.info("[getCartItemsByUserId] Message: 장바구니 조회");
        return cartItems;
    }

    @Transactional
    @LogMonitoring
    public List<ItemDTO> getItemsInCartByUser(User user) {
        List<CartItem> cartItems = cartItemRepository.findByUserId(user.getId());
        LOGGER.info("[getItemsInCartByUser] Message: 장바구니 조회");
        return cartItems.stream()
                .map(cartItem -> itemConverter.convert(cartItem.getItem()))
                .toList();
    }

    @Transactional
    @LogMonitoring
    public CartItem saveCartItem(User user, AddCartItemRequest request) {
        Item item = itemService.getItemById(request.itemId());
        CartItem cartItem = CartItem.builder()
                .item(item)
                .user(user)
                .build();

        cartItem = cartItemRepository.save(cartItem);
        return cartItem;
    }

    @LogMonitoring
    public int calcTotalPrice(List<ItemDTO> items) {
        return items.stream()
                .mapToInt(ItemDTO::price)
                .sum();
    }

    @LogMonitoring
    public void clearCart(User user) {
        cartItemRepository.deleteByUserId(user.getId());
    }
}
