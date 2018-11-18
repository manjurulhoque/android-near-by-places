package com.manjurulhoque.mynearbyplaces.models;

import java.io.Serializable;

public class Viewport implements Serializable {

    private Southwest southwest;

    private Northeast northeast;

    public Southwest getSouthwest() {
        return southwest;
    }

    public void setSouthwest(Southwest southwest) {
        this.southwest = southwest;
    }

    public Northeast getNortheast() {
        return northeast;
    }

    public void setNortheast(Northeast northeast) {
        this.northeast = northeast;
    }

    @Override
    public String toString() {
        return "ClassPojo [southwest = " + southwest + ", northeast = " + northeast + "]";
    }
}
