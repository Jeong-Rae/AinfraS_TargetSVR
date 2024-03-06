package io.goorm.ainfras.target.domain.Item.controller;

import io.goorm.ainfras.target.domain.Item.domain.CartItem;
import io.goorm.ainfras.target.domain.Item.dto.AddCartItemRequest;
import io.goorm.ainfras.target.domain.Item.dto.ItemDTO;
import io.goorm.ainfras.target.domain.Item.dto.RegisterItemResponse;
import io.goorm.ainfras.target.domain.Item.dto.RegisterItemsRequest;
import io.goorm.ainfras.target.domain.Item.service.CartItemService;
import io.goorm.ainfras.target.domain.Item.service.ItemService;
import io.goorm.ainfras.target.domain.User.domain.User;
import io.goorm.ainfras.target.global.interceptor.LogMonitoring;
import io.goorm.ainfras.target.global.util.BasicResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;
    private final CartItemService cartItemService;

    @PostMapping("api/items")
    @ResponseBody
    @LogMonitoring
    public ResponseEntity<RegisterItemResponse> registerItems(@RequestBody RegisterItemsRequest request) {
        RegisterItemResponse response = itemService.saveItems(request);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("shopping-cart")
    public String shoppingCart(@AuthenticationPrincipal User user, Model model) {
        System.out.println("cart email: "+user.getEmail());
        List<ItemDTO> items = cartItemService.getItemsInCartByUser(user);
        int totalPrice = cartItemService.calcTotalPrice(items);

        model.addAttribute("items", items);
        model.addAttribute("totalPrice", totalPrice);

        return "shopping-cart";
    }

    @PostMapping("api/cart/items")
    @ResponseBody
    @LogMonitoring
    public ResponseEntity<BasicResponse> addItemInShoppingCart(@AuthenticationPrincipal User user,
                                                               @RequestBody AddCartItemRequest request) {
        CartItem cartItem = cartItemService.saveCartItem(user, request);
        BasicResponse response = new BasicResponse("SUCCESS", null);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/checkout")
    @ResponseBody
    @LogMonitoring
    public ResponseEntity<BasicResponse> checkoutItem(@AuthenticationPrincipal User user) {
        cartItemService.clearCart(user);
        BasicResponse response = new BasicResponse("SUCCESS", null);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


}
