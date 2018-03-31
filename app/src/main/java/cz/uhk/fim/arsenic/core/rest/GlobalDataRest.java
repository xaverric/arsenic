package cz.uhk.fim.arsenic.core.rest;

import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Rest;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import cz.uhk.fim.arsenic.core.model.GlobalData;

@Rest(converters = MappingJackson2HttpMessageConverter.class)
public interface GlobalDataRest {

    @Get("https://api.coinmarketcap.com/v1/global/")
    GlobalData getGlobalData();
}
