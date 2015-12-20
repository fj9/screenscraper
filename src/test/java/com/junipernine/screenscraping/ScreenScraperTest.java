package com.junipernine.screenscraping;

import org.junit.Test;

/**
 * Created on 14/12/2015.
 */
public class ScreenScraperTest {

    @Test (expected = ScraperException.class)
    public void testBadURl() throws Exception, ScraperException {
        ScreenScraper scraper = new ScreenScraper();
        scraper.scrape("http://junipernine.com");
    }

    @Test (expected = ScraperException.class)
    public void testMalformedURl() throws Exception, ScraperException {
        ScreenScraper scraper = new ScreenScraper();
        scraper.scrape("junipernine.com");
    }

}