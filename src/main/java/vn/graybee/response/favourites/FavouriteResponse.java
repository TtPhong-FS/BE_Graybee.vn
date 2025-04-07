package vn.graybee.response.favourites;

import java.util.Collections;
import java.util.List;

public class FavouriteResponse {

    List<ProductFavourite> favourites;

    public FavouriteResponse() {
    }

    public FavouriteResponse(List<ProductFavourite> favourites) {
        this.favourites = favourites;
    }

    public List<ProductFavourite> getFavourites() {
        return favourites != null ? favourites : Collections.emptyList();
    }

    public void setFavourites(List<ProductFavourite> favourites) {
        this.favourites = favourites;
    }

}
