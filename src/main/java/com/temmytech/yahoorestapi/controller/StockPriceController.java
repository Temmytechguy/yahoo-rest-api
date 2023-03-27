package com.temmytech.yahoorestapi.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.temmytech.yahoorestapi.entity.StockPrice;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/stock-prices")
public class StockPriceController {

    private static final String API_ENDPOINT = "https://apidojo-yahoo-finance-v1.p.rapidapi.com/market/v2/get-quotes?region=US&symbols=AAPL,GOOG,MSFT,AMZN,FB,TSLA";

    @GetMapping("/{symbol}")
    public String getStockPrice(@PathVariable String symbol, Model model) throws IOException {
        Stock stock = YahooFinance.get(symbol);
        String name = stock.getName();
        BigDecimal price = stock.getQuote().getPrice();
        StockPrice stockPrice = new StockPrice();
        stockPrice.setName(name);
        stockPrice.setSymbol(symbol);
        stockPrice.setPrice(price);

        model.addAttribute("stockPrice", stockPrice);
        return "stock-price";

    }


    @GetMapping("/top")
    public String getTopStocks(Model model) throws IOException {
        String[] symbols = {"GME", "AMC", "SPCE", "BB", "CLOV", "CLNE", "BNGO", "EXPR", "MVIS", "NOK"};
        Map<String, Stock> stocks = YahooFinance.get(symbols);
        List<StockPrice> topStocks = new ArrayList<>();
        for (String symbol : stocks.keySet()) {
            Stock stock = stocks.get(symbol);
            BigDecimal price = stock.getQuote().getPrice();
            BigDecimal prevClose = stock.getQuote().getPreviousClose();
            BigDecimal change = price.subtract(prevClose);
            BigDecimal percentageChange = change.divide(prevClose, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));

            String name = stock.getName();
            StockPrice stockPrice = new StockPrice();
            stockPrice.setName(name);
            stockPrice.setSymbol(symbol);
            stockPrice.setPrice(price);
            stockPrice.setPercentageChange(percentageChange);
            topStocks.add(stockPrice);
        }
        topStocks.sort(Comparator.comparing(StockPrice::getPercentageChange).reversed());
        model.addAttribute("topStocks", topStocks.subList(0, 10));
        return "top-stocks";
    }

    @GetMapping("/toppers/{limit}")
    public String getMostActiveCurrencies(@PathVariable int limit, Model model) throws IOException, InterruptedException {
        List<StockPrice> topStocks = new ArrayList<>();
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(API_ENDPOINT + "?q=" + URLEncoder.encode("select * from yahoo.finance.quote where symbol in ('AAPL', 'GOOG', 'MSFT', 'AMZN', 'FB', 'TSLA')", StandardCharsets.UTF_8)))
                .header("X-RapidAPI-Host", "apidojo-yahoo-finance-v1.p.rapidapi.com")
                .header("X-RapidAPI-Key", "Bearer " + "dj0yJmk9eTJzQVBGaWZBUW94JmQ9WVdrOVIzbG5RWGhIYjJjbWNHbzlNQT09JnM9Y29uc3VtZXJzZWNyZXQmc3Y9MCZ4PTYx" + "c0f399a5e184c6676a0a9a78db3eb4fd341d6be0")
                .build();
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = objectMapper.readTree(httpResponse.body());
        System.out.println(httpResponse.body());
        JsonNode finance = root.get("finance");

        if (finance != null) {
            JsonNode result = finance.get("result");
            if (result != null && result.size() > 0) {
                JsonNode quotes = result.get(0).get("quotes");
                for (JsonNode quote : quotes) {
                    String name = quote.get("shortName").asText();
                    String symbol = quote.get("symbol").asText();
                    BigDecimal price = quote.get("regularMarketPrice").decimalValue();
                    BigDecimal prevClose = quote.get("regularMarketPreviousClose").decimalValue();
                    BigDecimal change = price.subtract(prevClose);
                    BigDecimal percentageChange = change.divide(prevClose, 4, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100));

                    StockPrice currency = new StockPrice();
                    currency.setName(name);
                    currency.setSymbol(symbol);
                    currency.setPrice(price);
                    currency.setPercentageChange(percentageChange);
                    topStocks.add(currency);
                }
            }
        }

        topStocks.sort(Comparator.comparing(StockPrice::getPercentageChange).reversed());
        topStocks = topStocks.subList(0, Math.min(limit, topStocks.size()));
        model.addAttribute("topStocks", topStocks);
        return "top-stocks";
    }

}
