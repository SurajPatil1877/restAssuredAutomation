package config;

import org.aeonbits.owner.Config;

@Config.Sources("classpath:config.properties")
public interface PropertyConfig extends Config {
    @Key("baseUrl")
    String baseURL();

}
