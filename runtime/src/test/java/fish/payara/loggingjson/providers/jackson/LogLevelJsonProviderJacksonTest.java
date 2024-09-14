package fish.payara.loggingjson.providers.jackson;

import fish.payara.loggingjson.providers.LogLevelJsonProviderJsonbTest;

public class LogLevelJsonProviderJacksonTest extends LogLevelJsonProviderJsonbTest {
    @Override
    protected Type type() {
        return Type.JACKSON;
    }
}
