package io.goorm.ainfras.target.domain.Item.service;

import io.goorm.ainfras.target.domain.Item.converter.ItemConverter;
import io.goorm.ainfras.target.domain.Item.domain.Item;
import io.goorm.ainfras.target.domain.Item.dto.ItemDTO;
import io.goorm.ainfras.target.domain.Item.dto.RegisterItemResponse;
import io.goorm.ainfras.target.domain.Item.dto.RegisterItemsRequest;
import io.goorm.ainfras.target.domain.Item.repository.ItemRepository;
import io.goorm.ainfras.target.global.interceptor.LogMonitoring;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(ItemService.class);
    private final ItemConverter itemConverter;

    @Transactional
    @LogMonitoring
    public void saveItem(ItemDTO itemDTO) {
        Item item = itemConverter.convert(itemDTO);
        item.setSeller("unknown");
        item = itemRepository.save(item);
        item.generateCode();
        itemRepository.save(item);
    }

    @Transactional
    @LogMonitoring
    public void saveItem(ItemDTO itemDTO, String seller) {
        Item item = itemConverter.convert(itemDTO);
        item.setSeller(seller);
        item = itemRepository.save(item);
        item.generateCode();
        itemRepository.save(item);
    }

    @Transactional
    @LogMonitoring
    public RegisterItemResponse saveItems(RegisterItemsRequest request) {
        int cnt = 0;

        for (ItemDTO itemDTO : request.items()) {
            if (request.seller().isBlank()) {
                this.saveItem(itemDTO);
                cnt++;
                continue;
            }
            this.saveItem(itemDTO, request.seller());
            cnt++;
        }

        RegisterItemResponse response = new RegisterItemResponse(cnt);

        return response;
    }

    @Transactional
    @LogMonitoring
    public List<Item> findItemList() {
        List<Item> items = itemRepository.findAll();

        return items;
    }

    @LogMonitoring
    public Item getItemById(Long itemId) {
        return itemRepository.getReferenceById(itemId);
    }
}
