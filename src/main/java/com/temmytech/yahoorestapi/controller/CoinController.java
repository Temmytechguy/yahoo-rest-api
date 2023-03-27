package com.temmytech.yahoorestapi.controller;

import com.temmytech.yahoorestapi.entity.Coin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Controller
public class CoinController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/coin")
    public String index(Model model) {
        Coin[] coins = restTemplate.getForObject("https://api.coingecko.com/api/v3/coins/list?include_platform=false", Coin[].class);
        Coin[] firstTenCoins = Arrays.copyOf(coins, 10);
        model.addAttribute("coins", firstTenCoins);
        return "coin-price";
    }
}
