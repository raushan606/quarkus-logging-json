package fish.payara.loggingjson.providers.jackson;

import fish.payara.loggingjson.providers.LogMessageJsonProviderJsonbTest;

public class MessageJsonProviderJacksonTest extends LogMessageJsonProviderJsonbTest {
    @Override
    protected Type type() {
        return Type.JACKSON;
    }
}
