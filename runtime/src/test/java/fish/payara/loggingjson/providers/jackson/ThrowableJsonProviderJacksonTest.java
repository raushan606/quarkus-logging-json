package fish.payara.loggingjson.providers.jackson;

import fish.payara.loggingjson.providers.ThrowableJsonProviderJsonbTest;

public class ThrowableJsonProviderJacksonTest extends ThrowableJsonProviderJsonbTest {
    @Override
    protected Type type() {
        return Type.JACKSON;
    }
}
