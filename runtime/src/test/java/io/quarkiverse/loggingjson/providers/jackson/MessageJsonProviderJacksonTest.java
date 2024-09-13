package io.quarkiverse.loggingjson.providers.jackson;

import io.quarkiverse.loggingjson.providers.LogMessageJsonProviderJsonbTest;

public class MessageJsonProviderJacksonTest extends LogMessageJsonProviderJsonbTest {
    @Override
    protected Type type() {
        return Type.JACKSON;
    }
}
