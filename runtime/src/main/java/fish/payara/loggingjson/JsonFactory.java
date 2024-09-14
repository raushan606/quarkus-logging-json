package fish.payara.loggingjson;

import java.io.IOException;

public interface JsonFactory {

    JsonGenerator createGenerator(StringBuilderWriter writer) throws IOException;
}
