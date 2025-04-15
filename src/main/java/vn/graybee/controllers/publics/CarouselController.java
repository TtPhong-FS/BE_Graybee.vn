package vn.graybee.controllers.publics;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.messages.MessageResponse;
import vn.graybee.response.publics.products.ProductBasicResponse;
import vn.graybee.services.carousels.CarouselItemService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/public/carousel")
public class CarouselController {

    private final CarouselItemService carouselItemService;

    public CarouselController(CarouselItemService carouselItemService) {
        this.carouselItemService = carouselItemService;
    }

    @GetMapping
    public ResponseEntity<MessageResponse<List<ProductBasicResponse>>> getProductsForCarousel(
            @RequestParam(value = "type", defaultValue = "best_seller") String type,
            @RequestParam(value = "category") String category,
            @RequestParam(value = "size", defaultValue = "20") int size,
            @RequestParam(value = "sortBy", defaultValue = "position") String sortBy,
            @RequestParam(value = "order", defaultValue = "asc") String order
    ) {
        return ResponseEntity.ok(carouselItemService.getCarouselProducts(type, category, size, sortBy, order));
    }

    @GetMapping("/menu")
    public ResponseEntity<BasicMessageResponse<List<String>>> getCategoryForCarousel() {
        return ResponseEntity.ok(carouselItemService.getCategoryExistInCarouselGroup());
    }

}
