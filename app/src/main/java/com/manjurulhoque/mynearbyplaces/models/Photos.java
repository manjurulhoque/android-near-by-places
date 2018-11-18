package com.manjurulhoque.mynearbyplaces.models;

import java.io.Serializable;

public class Photos implements Serializable {

    private String photo_reference;

    private String height;

    private String[] html_attributions;

    private String width;

    public String getPhoto_reference() {
        return photo_reference;
    }

    public void setPhoto_reference(String photo_reference) {
        this.photo_reference = photo_reference;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String[] getHtml_attributions() {
        return html_attributions;
    }

    public void setHtml_attributions(String[] html_attributions) {
        this.html_attributions = html_attributions;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    @Override
    public String toString() {
        return "ClassPojo [photo_reference = " + photo_reference + ", height = " + height + ", html_attributions = " + html_attributions + ", width = " + width + "]";
    }
}
