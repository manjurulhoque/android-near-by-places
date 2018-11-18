package com.manjurulhoque.mynearbyplaces;

import com.manjurulhoque.mynearbyplaces.remotes.GoogleApiService;
import com.manjurulhoque.mynearbyplaces.remotes.RetrofitBuilder;

public class Common {
    private static final String BASE_URL = "https://maps.googleapis.com/";

    public static GoogleApiService getGoogleApiService() {
        return RetrofitBuilder.builder().create(GoogleApiService.class);
    }
}
