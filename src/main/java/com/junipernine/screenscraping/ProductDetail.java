package com.junipernine.screenscraping;

/**
 * Created on 19/12/2015.
 */
class ProductDetail {
    private String title;
    private String unit_price;
    private String size = "unknown";
    private String description = "unknown";
    private boolean complete = true;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(String unit_price) {
        this.unit_price = unit_price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }


    public void setSizeInBytes(int sizeInBytes) {
        this.size = String.format("%.2f", ((float) sizeInBytes / 1024));
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void incomplete() {
        complete = false;
    }

    public boolean complete() {
        return complete;
    }

}
