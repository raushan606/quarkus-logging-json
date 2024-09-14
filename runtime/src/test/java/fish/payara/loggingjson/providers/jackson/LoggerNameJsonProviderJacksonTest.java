package fish.payara.loggingjson.providers.jackson;

import fish.payara.loggingjson.providers.LoggerNameJsonProviderJsonbTest;

public class LoggerNameJsonProviderJacksonTest extends LoggerNameJsonProviderJsonbTest {
    @Override
    protected Type type() {
        return Type.JACKSON;
    }
}
