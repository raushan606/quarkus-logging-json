package fish.payara.loggingjson.jsonb;

import java.util.HashMap;

import jakarta.json.Json;
import jakarta.json.stream.JsonGeneratorFactory;

import org.eclipse.yasson.YassonJsonb;
import org.eclipse.yasson.internal.JsonBindingBuilder;

import fish.payara.loggingjson.JsonFactory;
import fish.payara.loggingjson.JsonGenerator;
import fish.payara.loggingjson.StringBuilderWriter;

public class JsonbJsonFactory implements JsonFactory {

    private final JsonGeneratorFactory factory;
    private final YassonJsonb jsonb;

    public JsonbJsonFactory() {
        factory = Json.createGeneratorFactory(new HashMap<>());
        jsonb = (YassonJsonb) new JsonBindingBuilder().build();
    }

    @Override
    public JsonGenerator createGenerator(StringBuilderWriter writer) {
        return new JsonbJsonGenerator(factory.createGenerator(writer), jsonb);
    }
}
