package com.junipernine.screenscraping;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created on 20/12/2015.
 */
public class DataExtractor {

    public ProductDetail extractFromElement(Element product) {
        Elements productHeading = product.select("h3");
        ProductDetail productDetail = new ProductDetail();
        productDetail.setTitle(productHeading.text());
        productDetail.setUnit_price(extractPrice(product));
        String productURL = productHeading.select("a[href]").attr("abs:href");
        try {
            Document productDoc = Jsoup.connect(productURL).get();
            productDetail.setDescription(extractDescription(productDoc));
            productDetail.setSizeInBytes(productDoc.data().getBytes().length);
        } catch (IOException e) {
            productDetail.incomplete();
        }
        return productDetail;
    }

    public String extractDescription(Document productDoc) {
        Elements productDatas = productDoc.select("h3.productDataItemHeader");
        for (Element productData : productDatas) {
            if (productData.text().equalsIgnoreCase("Description")) {
                return productData.nextElementSibling().select("div.productText").text();
            }
        }
        return "";
    }

    public String extractPrice(Element product) {
        String priceString = product.select("p.pricePerUnit").text();
        priceString = priceString.toLowerCase().trim().substring(1).replaceAll("[^0-9.]", "");
        return priceString;
    }
}
