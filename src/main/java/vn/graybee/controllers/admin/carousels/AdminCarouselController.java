package vn.graybee.controllers.admin.carousels;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.messages.MessageResponse;
import vn.graybee.models.carousels.CarouselGroup;
import vn.graybee.requests.carousels.CarouselGroupRequest;
import vn.graybee.requests.carousels.CarouselItemCreateRequest;
import vn.graybee.response.admin.carousels.CarouselActiveResponse;
import vn.graybee.response.admin.carousels.CarouselItemResponse;
import vn.graybee.response.admin.carousels.CarouselPosition;
import vn.graybee.services.carousels.CarouselGroupService;
import vn.graybee.services.carousels.CarouselItemService;
import vn.graybee.services.users.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/carousel")
public class AdminCarouselController {

    private final CarouselItemService carouselItemService;

    private final CarouselGroupService carouselGroupService;

    private final UserService userService;

    public AdminCarouselController(CarouselItemService carouselItemService, CarouselGroupService carouselGroupService, UserService userService) {
        this.carouselItemService = carouselItemService;
        this.carouselGroupService = carouselGroupService;
        this.userService = userService;
    }

//    Carousel Group

    @PostMapping("/group")
    public ResponseEntity<BasicMessageResponse<CarouselGroup>> create(
            @RequestBody @Valid CarouselGroupRequest request
    ) {

        return ResponseEntity.ok(carouselGroupService.create(request));
    }

    @PutMapping("/group")
    public ResponseEntity<BasicMessageResponse<CarouselGroup>> update(
            @RequestBody @Valid CarouselGroupRequest request,
            @RequestParam("id") Integer id
    ) {
        return ResponseEntity.ok(carouselGroupService.update(request, id));
    }

    @GetMapping("/groups")
    public ResponseEntity<BasicMessageResponse<List<CarouselGroup>>> getAllForDashboard(
            @RequestParam(value = "type", required = false) String type
    ) {
        return ResponseEntity.ok(carouselGroupService.getAllForDashboard());
    }

    @GetMapping("/group")
    public ResponseEntity<BasicMessageResponse<CarouselGroup>> getById(
            @RequestParam(value = "id") Integer id
    ) {

        return ResponseEntity.ok(carouselGroupService.getById(id));
    }

//    Carousel Item

    @PostMapping("/item")
    public ResponseEntity<BasicMessageResponse<CarouselItemResponse>> create(
            @RequestBody @Valid CarouselItemCreateRequest request
    ) {

        return ResponseEntity.ok(carouselItemService.create(request));
    }

    @PutMapping("/item")
    public ResponseEntity<BasicMessageResponse<CarouselItemResponse>> update(
            @RequestBody @Valid CarouselItemCreateRequest request,
            @RequestParam("id") Integer id
    ) {
        return ResponseEntity.ok(carouselItemService.update(request, id));
    }

    @PutMapping("/item/active")
    public ResponseEntity<BasicMessageResponse<CarouselActiveResponse>> updateActive(
            @RequestParam("active") boolean active,
            @RequestParam("id") Integer id
    ) {
        return ResponseEntity.ok(carouselItemService.updateActive(id, active));
    }


    @PutMapping("/item/position")
    public ResponseEntity<BasicMessageResponse<CarouselPosition>> updatePosition(
            @RequestParam("position") int position,
            @RequestParam("id") Integer id
    ) {

        return ResponseEntity.ok(carouselItemService.updatePosition(id, position));
    }

    @GetMapping("/items")
    public ResponseEntity<MessageResponse<List<CarouselItemResponse>>> getCarouselForDashboard(
            @RequestParam(value = "type", defaultValue = "best_seller") String type,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size,
            @RequestParam(value = "sortBy", defaultValue = "position") String sortBy,
            @RequestParam(value = "order", defaultValue = "asc") String order
    ) {
        return ResponseEntity.ok(carouselItemService.getCarouselForDashboard(type, page, size, sortBy, order));
    }

}
