package fish.payara.loggingjson.providers.jackson;

import fish.payara.loggingjson.providers.ThreadNameJsonProviderJsonbTest;

public class ThreadNameJsonProviderJacksonTest extends ThreadNameJsonProviderJsonbTest {
    @Override
    protected Type type() {
        return Type.JACKSON;
    }
}
