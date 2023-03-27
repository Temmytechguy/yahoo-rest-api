package com.temmytech.yahoorestapi.entity;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import yahoofinance.Stock;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YahooFinanceAPI {

    private String apiKey = "YOUR_API_KEY_HERE";

    public Map<String, Stock> getStocks(String[] symbols) throws IOException, InterruptedException {
        String symbolsStr = String.join(",", symbols);
        String url = String.format("https://apidojo-yahoo-finance-v1.p.rapidapi.com/market/v2/get-quotes?region=US&symbols=%s", symbolsStr);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("x-rapidapi-host", "apidojo-yahoo-finance-v1.p.rapidapi.com")
                .header("x-rapidapi-key", apiKey)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> data = objectMapper.readValue(response.body(), new TypeReference<Map<String,Object>>(){});
        List<Map<String, Object>> quotes = (List<Map<String, Object>>) data.get("quoteResponse");
        List<Map<String, Object>> quoteList = (List<Map<String, Object>>) quotes.get(0).get("quote");

        Map<String, Stock> stocks = new HashMap<>();
        for (Map<String, Object> quote : quoteList) {
            String symbol = (String) quote.get("symbol");
            String name = (String) quote.get("shortName");
            BigDecimal price = new BigDecimal((Double) quote.get("regularMarketPrice"));
            Stock stock = new Stock(symbol);

            stocks.put(symbol, stock);
        }

        return stocks;
    }
}
