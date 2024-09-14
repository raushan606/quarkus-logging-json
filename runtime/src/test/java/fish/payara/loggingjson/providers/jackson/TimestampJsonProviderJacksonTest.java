package fish.payara.loggingjson.providers.jackson;

import fish.payara.loggingjson.providers.TimestampJsonProviderJsonbTest;

public class TimestampJsonProviderJacksonTest extends TimestampJsonProviderJsonbTest {
    @Override
    protected Type type() {
        return Type.JACKSON;
    }
}
