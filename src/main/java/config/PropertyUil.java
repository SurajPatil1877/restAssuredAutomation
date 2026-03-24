package config;

import org.aeonbits.owner.ConfigCache;

public class PropertyUil {
    public static PropertyConfig getConfig() {
        return ConfigCache.getOrCreate(PropertyConfig.class);
    }


}
