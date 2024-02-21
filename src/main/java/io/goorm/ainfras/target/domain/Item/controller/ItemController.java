package io.goorm.ainfras.target.domain.Item.controller;

import io.goorm.ainfras.target.domain.Item.dto.RegisterItemResponse;
import io.goorm.ainfras.target.domain.Item.dto.RegisterItemsRequest;
import io.goorm.ainfras.target.domain.Item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @PostMapping("api/items")
    public ResponseEntity<RegisterItemResponse> registerItems(@RequestBody RegisterItemsRequest request) {
        RegisterItemResponse response = itemService.saveItems(request);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
