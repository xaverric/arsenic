package cz.uhk.fim.arsenic.core.rest;

import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Path;
import org.androidannotations.rest.spring.annotations.Rest;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.List;

import cz.uhk.fim.arsenic.core.model.Currency;

@Rest(converters = MappingJackson2HttpMessageConverter.class)
public interface CurrencyRest {

    @Get("https://api.coinmarketcap.com/v1/ticker/{currencyId}/?convert={convertTo}")
    List<Currency> getCurrency(@Path String currencyId, @Path String convertTo);

    @Get("https://api.coinmarketcap.com/v1/ticker/?convert={convertTo}&limit={limit}")
    List<Currency> getConvertedCurrencies(@Path String convertTo, @Path Long limit);

}
