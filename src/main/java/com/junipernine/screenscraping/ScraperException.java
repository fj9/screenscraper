package com.junipernine.screenscraping;

/**
 * Created on 19/12/2015.
 */
public class ScraperException extends Throwable {
    public ScraperException(String message, Exception ex) {
        super(message, ex);
    }
}
