package fish.payara.loggingjson.providers.jackson;

import fish.payara.loggingjson.providers.ThreadIdJsonProviderJsonbTest;

public class ThreadIdJsonProviderJacksonTest extends ThreadIdJsonProviderJsonbTest {
    @Override
    protected Type type() {
        return Type.JACKSON;
    }
}
