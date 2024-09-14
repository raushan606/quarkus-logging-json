package fish.payara.loggingjson.providers;

import java.io.IOException;

import org.jboss.logmanager.ExtLogRecord;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import fish.payara.loggingjson.JsonFactory;
import fish.payara.loggingjson.JsonGenerator;
import fish.payara.loggingjson.JsonProvider;
import fish.payara.loggingjson.StringBuilderWriter;
import fish.payara.loggingjson.jackson.JacksonJsonFactory;
import fish.payara.loggingjson.jsonb.JsonbJsonFactory;

abstract class JsonProviderBaseTest {

    private static final JsonFactory jsonb = new JsonbJsonFactory();
    private static final JsonFactory jackson = new JacksonJsonFactory();
    private static final ObjectMapper mapper = new ObjectMapper();

    protected abstract Type type();

    private JsonFactory getJsonFactory() {
        switch (type()) {
            case JSONB:
                return jsonb;
            case JACKSON:
                return jackson;
            default:
                throw new RuntimeException("Unsupported type");
        }
    }

    protected JsonGenerator getGenerator(StringBuilderWriter writer) throws IOException {
        return getJsonFactory().createGenerator(writer);
    }

    protected String getResult(JsonProvider jsonProvider, ExtLogRecord event) throws IOException {
        try (StringBuilderWriter writer = new StringBuilderWriter();
                JsonGenerator generator = getGenerator(writer)) {
            generator.writeStartObject();
            jsonProvider.writeTo(generator, event);
            generator.writeEndObject();
            generator.flush();
            return writer.toString();
        }
    }

    protected JsonNode getResultAsJsonNode(JsonProvider jsonProvider, ExtLogRecord event) throws IOException {
        return mapper.readValue(getResult(jsonProvider, event), JsonNode.class);
    }

    public enum Type {
        JSONB,
        JACKSON
    }
}
