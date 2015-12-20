package com.junipernine.screenscraping;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static junit.framework.Assert.assertTrue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;


/**
 * Created on 20/12/2015.
 */
public class DataExtractorTest {

    @Mock
    private Element product;
    @Mock
    private org.jsoup.select.Elements elements;
    @Mock
    private Document productDoc;

    private DataExtractor dataExtractor;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        dataExtractor = new DataExtractor();
    }

    @Test
    public void testPricePounds() throws Exception {
        mockReturnPrice("£3.00");
        String price = dataExtractor.extractPrice(product);
        String expectedPrice = "3.00";
        assertThat(price, is(expectedPrice));
    }

    @Test
    public void testPricePoundsAndPence() throws Exception {
        mockReturnPrice("£3.50");
        String price = dataExtractor.extractPrice(product);
        String expectedPrice = "3.50";
        assertThat(price, is(expectedPrice));
    }

    @Test
    public void testPricePence() throws Exception {
        mockReturnPrice("£0.33");
        String price = dataExtractor.extractPrice(product);
        String expectedPrice = "0.33";
        assertThat(price, is(expectedPrice));
    }

    @Test
    public void pricePerUnit() throws Exception {
        mockReturnPrice("£3.50/unit");
        String price = dataExtractor.extractPrice(product);
        String expectedPrice = "3.50";
        assertThat(price, is(expectedPrice));
    }

    @Test
    public void testDescription() throws Exception {
        Document productDoc = mockReturnDescription("Apricots");
        String result = dataExtractor.extractDescription(productDoc);
        String expected = "Apricots";
        assertThat(result, is(expected));

    }

    @Test
    public void testEmptyDescription() throws Exception {
        Document productDoc = mockReturnDescription("");
        String result = dataExtractor.extractDescription(productDoc);
        String expected = "";
        assertThat(result, is(expected));

    }

    private Document mockReturnDescription(String description) {
        String html = "<head></head><body>" +
                "<h3 class=\"productDataItemHeader\">Description</h3>\n" +
                "<div class=\"productText\">\n" +
                "<p>" + description +
                "</p>\n" +
                "<p>\n" +
                "<p></p>\n" +
                "</p>\n" +
                "</div>\n" +
                "\n" +
                "<h3 class=\"productDataItemHeader\">Nutrition</h3>\n" +
                "<div class=\"productText\">\n" +
                "<div>\n" +
                "<p>\n" +
                "<strong>Table of Nutritional Information</strong>\n" +
                "</p>" +
                "</body>";
        return Jsoup.parse(html);
    }

    @Test
    public void testNoDescription() throws Exception {
        String html = "<head></head><body>" +
                "<h3 class=\"productDataItemHeader\">Nutrition</h3>\n" +
                "<div class=\"productText\">\n" +
                "<div>\n" +
                "<p>\n" +
                "<strong>Table of Nutritional Information</strong>\n" +
                "</p>" +
                "</body>";
        Document productDoc = Jsoup.parse(html);
        String result = dataExtractor.extractDescription(productDoc);
        String expected = "";
        assertThat(result, is(expected));

    }

    @Test
    public void testExtractIncompleteProductDetails() throws Exception {
        mockReturnURL("http://junipernine.com/sainsburys-apricot-ripe---ready-320g.html");
        mockReturnPrice("£2.00");
        ProductDetail productDetail = dataExtractor.extractFromElement(product);
        assertFalse(productDetail.complete());

    }

    @Test
    public void testExtractCompleteProductDetails() throws Exception {
        //TODO: this test is fragile. Would need to mock the interaction with the web page.
        mockReturnURL("http://hiring-tests.s3-website-eu-west-1.amazonaws.com/2015_Developer_Scrape/sainsburys-apricot-ripe---ready-320g.html");
        mockReturnPrice("£2.00");
        ProductDetail productDetail = dataExtractor.extractFromElement(product);
        assertTrue(productDetail.complete());
    }

    private void mockReturnURL(String value) {
        when(product.select(anyString())).thenReturn(elements);
        when(elements.select("a[href]")).thenReturn(elements);
        when(elements.attr("abs:href")).thenReturn(value);
    }

    private void mockReturnPrice(String price) {
        when(product.select("p.pricePerUnit")).thenReturn(elements);
        when(elements.text()).thenReturn(price);
    }
}