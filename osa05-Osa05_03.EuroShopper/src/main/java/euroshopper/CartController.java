package euroshopper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CartController {

    @Autowired
    private ShoppingCart ShoppingCart;

    @Autowired
    private ItemRepository itemRepository;
    
    @GetMapping("/cart")
    public String cart(Model model) {
        model.addAttribute("items", ShoppingCart.getItems());
        return "cart";
    }

    @PostMapping("/cart/items/{id}")
    public String add(@PathVariable Long id) {
        Item item = itemRepository.getOne(id);
        ShoppingCart.addToCart(item);
        return "redirect:/cart";
    }
}
