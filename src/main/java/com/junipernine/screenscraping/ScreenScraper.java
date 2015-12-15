package com.junipernine.screenscraping;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;


/**
 * Created on 14/12/2015.
 */
public class ScreenScraper {
    public void doThing(String url) {
        try {
            Document doc = Jsoup.connect("http://hiring-tests.s3-website-eu-west-1.amazonaws.com/2015_Developer_Scrape/5_products.html").get();
            Elements products = doc.select("div.product");
            List<ProductDetail> productDetails = new ArrayList<>(products.size());
            for (Element product : products) {
                ProductDetail productDetail = new ProductDetail();
                Elements productHeading = product.select("h3");
                productDetail.setTitle(productHeading.text());
                productDetail.setPriceInPence(extractPrice(product));
                String productURL = productHeading.select("a[href]").attr("abs:href");
                Document productdoc = Jsoup.connect(productURL).get();
                productDetail.setDescription(extractDescription(productdoc));
                productDetail.setSizeInBytes(productdoc.data().getBytes().length);
                productDetails.add(productDetail);

            }

            ObjectMapper mapper = new ObjectMapper();
            String jsonString = mapper.writeValueAsString(productDetails);

            System.out.println(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String extractDescription(Document productdoc) {
        Elements productDatas = productdoc.select("h3.productDataItemHeader");
        for (Element productData : productDatas) {
            if (productData.text().equalsIgnoreCase("Description")) {
                return productData.nextElementSibling().select("div.productText").text();
            }
        }
        return "";
    }

    private int extractPrice(Element product) {
        String priceString = product.select("p.pricePerUnit").text();
        priceString = priceString.toLowerCase().trim().substring(1).replaceAll("[^0-9]", "");
        return parseInt(priceString);
    }

    private static String getUrlSource(String urlString) throws IOException {
        URL url = new URL(urlString);
        URLConnection connection = url.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(
                connection.getInputStream(), "UTF-8"));
        String inputLine;
        StringBuilder builder = new StringBuilder();
        while ((inputLine = in.readLine()) != null)
            builder.append(inputLine);
        in.close();

        return builder.toString();
    }

    private class ProductDetail {
        //"title":"Sainsbury's Avocado, Ripe & Ready x2",
//
//        "size": "90.6kb",
//
//                "unit_price": 1.80,
//
//                "description": "Great to eat now - refrigerate at home 1 of 5 a day 1
//
//        avocado counts as 1 of your 5..."
        String title;
        String unit_price;
        String size;
        String description;

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

        public void setPriceInPence(int priceInPence) {
            this.unit_price = Math.floor(priceInPence/100)+"."+(priceInPence%100);
        }

        public void setSizeInBytes(int sizeInBytes) {
            this.size=String.format("%.2f", ((float)sizeInBytes/1024));
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
