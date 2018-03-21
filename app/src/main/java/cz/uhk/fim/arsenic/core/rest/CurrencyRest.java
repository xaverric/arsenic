package cz.uhk.fim.arsenic.core.rest;

import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Path;
import org.androidannotations.rest.spring.annotations.Rest;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.List;

@Rest(converters = MappingJackson2HttpMessageConverter.class)
public interface CurrencyRest<T> {

    @Get("https://api.coinmarketcap.com/v1/ticker/{currencyId}")
    List<T> getCurrency(@Path String currencyId);

    @Get("https://api.coinmarketcap.com/v1/ticker")
    List<T> getAllCurrencies();

    @Get("https://api.coinmarketcap.com/v1/ticker/?limit={limit}")
    List<T> getCurrencies(@Path Integer limit);

    @Get("https://api.coinmarketcap.com/v1/ticker/?convert={convertTo}&limit={limit}")
    List<T> getConvertedCurrencies(@Path String convertTo, @Path Integer limit);

}
