package com.junipernine.screenscraping;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created on 14/12/2015.
 */
public class ScreenScraper {
    private  DataExtractor dataExtractor;

    public ScreenScraper() {
        this.dataExtractor = new DataExtractor();
    }

    public List<ProductDetail> scrape(String url) throws ScraperException {
        Elements products = retrieveProductsFormUrl(url);
        return createProductDetails(products);
    }

    private List<ProductDetail> createProductDetails(Elements products) {
        List<ProductDetail> productDetails = new ArrayList<>(products.size());
        for (Element product : products) {
            ProductDetail productDetail = dataExtractor.extractFromElement(product);
            productDetails.add(productDetail);
        }
        return productDetails;
    }

    private Elements retrieveProductsFormUrl(String url) throws ScraperException {
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException | IllegalArgumentException e) {
            throw new ScraperException("Problem accessing Product listing URL " + url, e);
        }
        return doc.select("div.product");
    }

}
