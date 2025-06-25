// This file is part of the 'portfolio-manager' (Portfolio Manager)
// project, an open source stock portfolio manager application
// written in Java.
//
// Copyright 2015 Oscar Stigter
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.ozsoft.portfoliomanager.services.downloader;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ozsoft.portfoliomanager.domain.Quote;
import org.ozsoft.portfoliomanager.domain.Stock;

import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;
import yahoofinance.histquotes2.HistoricalDividend;
import yahoofinance.quotes.stock.StockQuote;

/**
 * Stock quote downloader using the Yahoo Finance API.<br />
 * <br />
 *
 * Updates the stock's last price and change percentage. <br />
 * <br />
 *
 * Uses near-realtime intraday prices (accuracy of a few seconds), or the last closing price outside market hours. <br />
 * <br />
 *
 * Uses Stijn Strickx' yahoofinance-api library (https://github.com/sstrickx/yahoofinance-api). <br />
 * <br />
 *
 * <b>WARNING:</b> The underlying Yahoo API is not officially supported, so it's reliability and durability cannot be guaranteed!
 *
 * @author Oscar Stigter
 */
public class YahooFinanceQuoteDownloader extends QuoteDownloader {

    private static final Logger LOGGER = LogManager.getLogger(YahooFinanceQuoteDownloader.class);

    /**
     * Constructor.
     *
     * @param httpPageReader
     *                           The HTTP page reader.
     */
    public YahooFinanceQuoteDownloader() {
        super(null);
    }

    @Override
    public boolean updateStock(Stock stock) {
        boolean isUpdated = false;

        try {
            long startTime = System.currentTimeMillis();
            StockQuote quote = YahooFinance.get(stock.getSymbol()).getQuote();
            long duration = System.currentTimeMillis() - startTime;
            BigDecimal price = quote.getPrice();
            if (price != null) {
                BigDecimal oldPrice = stock.getPrice();
                if (price.compareTo(oldPrice) != 0) {
                    // Update price and timestamp.
                    stock.setPrice(price);
                    stock.setChangePerc(quote.getChangeInPercent());
                    isUpdated = true;
                    LOGGER.debug(String.format("Updated %s: $ %,.2f (%+.2f %%) (%,d ms)", stock, price, stock.getChangePerc(), duration));
                }
            }
        } catch (IOException e) {
            LOGGER.error(String.format("Failed to retrieve quote for %s: %s", stock, e.getMessage()));
        }

        return isUpdated;
    }

    @Override
    public List<Quote> getHistoricPrices(Stock stock) {
        List<Quote> prices = new ArrayList<Quote>();

        Calendar fromCal = Calendar.getInstance();
        fromCal.set(Calendar.DATE, 1);
        fromCal.set(Calendar.MONTH, 0);
        fromCal.set(Calendar.YEAR, 1900);

        try {
            long startTime = System.currentTimeMillis();
            for (HistoricalQuote quote : YahooFinance.get(stock.getSymbol()).getHistory(fromCal, Interval.DAILY)) {
                BigDecimal price = quote.getAdjClose();
                if (price != null) {
                    prices.add(new Quote(quote.getDate().getTime(), price));
                }
            }
            Collections.sort(prices);
            long duration = System.currentTimeMillis() - startTime;
            LOGGER.debug(String.format("Retrieved historic prices for %s in %,d ms", stock, duration));

        } catch (IOException e) {
            LOGGER.error(String.format("Failed to retrieve historic prices for %s: %s", stock, e.getMessage()));
        }

        return prices;
    }

    @Override
    public List<Quote> getDividendPayouts(Stock stock) {
        List<Quote> divs = new ArrayList<Quote>();

        Calendar fromCal = Calendar.getInstance();
        fromCal.set(Calendar.DATE, 1);
        fromCal.set(Calendar.MONTH, 0);
        fromCal.set(Calendar.YEAR, 1900);

        try {
            long startTime = System.currentTimeMillis();
            for (HistoricalDividend div : YahooFinance.get(stock.getSymbol()).getDividendHistory(fromCal)) {
                divs.add(new Quote(div.getDate().getTime(), div.getAdjDividend()));
            }
            Collections.sort(divs);
            long duration = System.currentTimeMillis() - startTime;
            LOGGER.debug(String.format("Retrieved dividend payouts for %s in %,d ms", stock, duration));

        } catch (IOException e) {
            LOGGER.error(String.format("Failed to retrieve dividend payouts for %s: %s", stock, e.getMessage()));
        }

        return divs;
    }
}
