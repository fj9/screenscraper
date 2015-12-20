package com.junipernine.screenscraping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Scanner;

/**
 * Created on 14/12/2015.
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);


        System.out.println("************************************");
        System.out.println("*      Grocery Screen Scraper      *");
        System.out.println("************************************");
        System.out.println("Enter \"Q\" to Quit");
        System.out.println("Enter \"scrape <URL>\" to list products from that page");

        ScreenScraper scraper = new ScreenScraper();

        while (true) {
            String inputValue = scanner.next();
            if (inputValue.startsWith("scrape")) {
                try {
                    List<ProductDetail> productDetails = scraper.scrape(scanner.next());
                    int completeRecords = 0;
                    for (ProductDetail product : productDetails) {
                        if (product.complete())
                            completeRecords++;
                    }
                    System.out.println(productDetails.size() + " products retrieved, " + completeRecords + " complete records");
                    System.out.println(createJson(productDetails));
                } catch (ScraperException e) {
                    System.out.println(e.getMessage());
                }
            } else if ("Q".equalsIgnoreCase(inputValue)) {
                System.out.println("Bye!");
                System.exit(0);
            }
        }
    }

    private static String createJson(List<ProductDetail> productDetails) throws ScraperException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = null;
        try {
            jsonString = mapper.writeValueAsString(productDetails);
        } catch (JsonProcessingException e) {
            throw new ScraperException("Problem mapping to Json", e);
        }
        return jsonString;
    }
}