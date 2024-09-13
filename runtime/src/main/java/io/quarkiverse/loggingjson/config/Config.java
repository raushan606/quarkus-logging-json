package io.quarkiverse.loggingjson.config;

import java.util.Optional;

import io.quarkus.runtime.annotations.ConfigGroup;
import io.quarkus.runtime.annotations.ConfigItem;
import io.quarkus.runtime.annotations.ConfigPhase;
import io.quarkus.runtime.annotations.ConfigRoot;

@ConfigRoot(phase = ConfigPhase.RUN_TIME, name = "log.json")
public class Config {
    /**
     * Configuration properties for console formatter.
     */
    @ConfigItem(name = "console")
    public ConfigConsole console;

    /**
     * Configuration properties to customize fields
     */
    @ConfigItem
    public FieldsConfig fields;
    /**
     * The special end-of-record delimiter to be used. By default, newline delimiter is used.
     */
    @ConfigItem(defaultValue = "\n")
    public String recordDelimiter;

    @ConfigGroup
    public static class FieldsConfig {
        /**
         * Options for timestamp.
         */
        @ConfigItem
        public TimestampField timestamp;
        /**
         * Options for loggerName.
         */
        @ConfigItem
        public FieldConfig loggerName;
        /**
         * Options for level.
         */
        @ConfigItem
        public FieldConfig level;
        /**
         * Options for message.
         */
        @ConfigItem
        public FieldConfig message;
        /**
         * Options for threadName.
         */
        @ConfigItem
        public FieldConfig threadName;
        /**
         * Options for threadId.
         */
        @ConfigItem
        public FieldConfig threadId;
        /**
         * Options for throwable.
         */
        @ConfigItem
        public ThrowableField throwable;
    }

    @ConfigGroup
    public static class FieldConfig {
        /**
         * Used to change the json key for the field.
         */
        @ConfigItem
        public Optional<String> fieldName;
        /**
         * Enable or disable the field.
         */
        @ConfigItem
        public Optional<Boolean> enabled;
    }

    @ConfigGroup
    public static class TimestampField {
        /**
         * Used to change the json key for the field.
         */
        @ConfigItem
        public Optional<String> fieldName;
        /**
         * The date format to use. The special string "default" indicates that the default format should be used.
         */
        @ConfigItem(defaultValue = "default")
        public String dateFormat;
        /**
         * The zone to use when formatting the timestamp.
         */
        @ConfigItem(defaultValue = "default")
        public String zoneId;
        /**
         * Enable or disable the field.
         */
        @ConfigItem(defaultValue = "true")
        public Optional<Boolean> enabled;
    }

    @ConfigGroup
    public static class ThrowableField {
        /**
         * Used to change the json key for the field.
         */
        @ConfigItem
        public Optional<String> fieldName;
        /**
         * Enable or disable the field.
         */
        @ConfigItem(defaultValue = "true")
        public Optional<Boolean> enabled;
        /**
         * Options for stackTrace.
         */
        @ConfigItem
        public FieldConfig stackTrace;
        /**
         * Options for errorType.
         */
        @ConfigItem
        public FieldConfig exception;
    }
}
