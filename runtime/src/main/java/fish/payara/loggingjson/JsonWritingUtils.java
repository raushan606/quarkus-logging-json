package fish.payara.loggingjson;

import java.io.IOException;
import java.util.Map;

public class JsonWritingUtils {

    /**
     * Writes entries of the map as fields.
     */
    public static void writeMapEntries(JsonGenerator generator, Map<?, ?> map) throws IOException {
        if (map != null) {
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                if (entry.getKey() != null && entry.getValue() != null) {
                    generator.writeFieldName(entry.getKey().toString());
                    generator.writeObject(entry.getValue());
                }
            }
        }
    }

    /**
     * Writes a map as String fields to the generator if and only if the fieldName and values are not null.
     */
    public static void writeMapStringFields(JsonGenerator generator, String fieldName, Map<String, String> map)
            throws IOException {
        writeMapStringFields(generator, fieldName, map, false);
    }

    /**
     * Writes a map as String fields to the generator if and only if the fieldName and values are not null.
     *
     * @param lowerCaseKeys when true, the map keys will be written in lowercase.
     */
    public static void writeMapStringFields(JsonGenerator generator, String fieldName, Map<String, String> map,
            boolean lowerCaseKeys) throws IOException {
        if (map != null && !map.isEmpty()) {
            if (fieldName != null)
                generator.writeObjectFieldStart(fieldName);
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String key = entry.getKey() != null && lowerCaseKeys
                        ? entry.getKey().toLowerCase()
                        : entry.getKey();
                writeStringField(generator, key, entry.getValue());
            }
            if (fieldName != null)
                generator.writeEndObject();
        }
    }

    /**
     * Writes an array of strings to the generator if and only if the fieldName and values are not null.
     */
    public static void writeStringArrayField(JsonGenerator generator, String fieldName, String[] fieldValues)
            throws IOException {
        if (fieldName != null && fieldValues != null && fieldValues.length > 0) {
            generator.writeArrayFieldStart(fieldName);
            for (String fieldValue : fieldValues) {
                generator.writeString(fieldValue);
            }
            generator.writeEndArray();
        }
    }

    /**
     * Writes the field to the generator if and only if the fieldName and fieldValue are not null.
     */
    public static void writeStringField(JsonGenerator generator, String fieldName, String fieldValue) throws IOException {
        if (fieldName != null && fieldValue != null) {
            generator.writeStringField(fieldName, fieldValue);
        }
    }

    /**
     * Writes the field to the generator if and only if the fieldName is not null.
     */
    public static void writeNumberField(JsonGenerator generator, String fieldName, int fieldValue) throws IOException {
        if (fieldName != null) {
            generator.writeNumberField(fieldName, fieldValue);
        }
    }

    /**
     * Writes the field to the generator if and only if the fieldName is not null.
     */
    public static void writeNumberField(JsonGenerator generator, String fieldName, long fieldValue) throws IOException {
        if (fieldName != null) {
            generator.writeNumberField(fieldName, fieldValue);
        }
    }

    public static boolean isNotNullOrEmpty(final String value) {
        return value != null && !value.isEmpty();
    }
}
