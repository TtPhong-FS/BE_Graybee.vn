package vn.graybee.serviceImps.products.detail;

import vn.graybee.response.publics.products.DetailTemplateResponse;
import vn.graybee.services.products.detail.DetailFetcher;

import java.util.Map;


public class DetailFetcherRegistry {

    private final Map<String, DetailFetcher> fetchers;

    public DetailFetcherRegistry(Map<String, DetailFetcher> fetchers) {
        this.fetchers = fetchers;
    }

    public DetailTemplateResponse getDetail(String category, Long productId) {
        DetailFetcher fetcher = fetchers.get(category.toLowerCase());

        return fetcher.fetchDetail(productId);
    }

}
