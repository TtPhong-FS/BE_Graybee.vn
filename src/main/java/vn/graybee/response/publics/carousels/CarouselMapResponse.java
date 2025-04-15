package vn.graybee.response.publics.carousels;

import vn.graybee.response.publics.products.ProductBasicResponse;

import java.util.List;

public class CarouselMapResponse {

    private String category;

    private List<ProductBasicResponse> products;

    public CarouselMapResponse() {
    }

    public CarouselMapResponse(String category, List<ProductBasicResponse> products) {
        this.category = category;
        this.products = products;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<ProductBasicResponse> getProducts() {
        return products;
    }

    public void setProducts(List<ProductBasicResponse> products) {
        this.products = products;
    }

}
