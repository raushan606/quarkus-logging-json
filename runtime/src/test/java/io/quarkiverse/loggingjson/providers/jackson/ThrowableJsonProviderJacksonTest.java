package io.quarkiverse.loggingjson.providers.jackson;

import io.quarkiverse.loggingjson.providers.ThrowableJsonProviderJsonbTest;

public class ThrowableJsonProviderJacksonTest extends ThrowableJsonProviderJsonbTest {
    @Override
    protected Type type() {
        return Type.JACKSON;
    }
}
