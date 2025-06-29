package vn.graybee.home;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.common.utils.CodeGenerator;
import vn.graybee.common.utils.MessageBuilder;
import vn.graybee.modules.catalog.dto.response.SidebarDto;
import vn.graybee.modules.catalog.service.CategoryService;
import vn.graybee.modules.product.dto.response.CategoryWithProducts;
import vn.graybee.modules.product.service.ProductService;

import java.time.Duration;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("${api.publicApi.home}")
public class HomeController {

    private final CategoryService categoryService;

    private final ProductService productService;

    @GetMapping("/sidebar")
    public ResponseEntity<BasicMessageResponse<List<SidebarDto>>> getSidebar() {
        return ResponseEntity.ok(
                MessageBuilder.ok(categoryService.getSidebar(), null)
        );
    }

    @GetMapping("/session")
    public ResponseEntity<BasicMessageResponse<String>> setSession(HttpServletResponse response) {
        String sessionId = CodeGenerator.generateCode(20, CodeGenerator.ALPHANUMERIC.toLowerCase());

        ResponseCookie cookie = ResponseCookie.from("sessionId", sessionId)
                .httpOnly(false)
                .secure(true)
                .sameSite("None")
                .path("/")
                .maxAge(Duration.ofDays(7))
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return ResponseEntity.ok(
                MessageBuilder.ok(sessionId, null)
        );
    }


//
//    @GetMapping("/carousel")
//    public ResponseEntity<BasicMessageResponse<List<ProductBasicResponse>>> findAllProductBasics() {
//        return ResponseEntity.ok(productService.());
//    }


    @GetMapping("/carousel/{category}")
    public ResponseEntity<BasicMessageResponse<CategoryWithProducts>> getProductByCategory(
            @PathVariable String category
    ) {
        CategoryWithProducts categoryWithProducts = productService.carouselTopTenProductBestSellByCategory(category);

        return ResponseEntity.ok(
                MessageBuilder.ok(categoryWithProducts, null)
        );
    }


}
