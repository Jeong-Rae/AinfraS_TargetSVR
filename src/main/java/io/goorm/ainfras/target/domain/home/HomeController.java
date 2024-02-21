package io.goorm.ainfras.target.domain.home;

import io.goorm.ainfras.target.domain.Item.domain.Item;
import io.goorm.ainfras.target.domain.Item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final ItemService itemService;
    // 메인 화면
    @GetMapping(value = {"index", "/", "home"})
    public String home(Model model) {
        List<Item> items = itemService.findItemList();

        model.addAttribute("items", items);

        return "index";
    }
}
