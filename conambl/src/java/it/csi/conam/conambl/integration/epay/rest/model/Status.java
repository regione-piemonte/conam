package it.csi.conam.conambl.integration.epay.rest.model;

import java.util.Objects;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonValue;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.ser.std.ToStringSerializer;

/**
 * Status class converted to use Jackson for JSON serialization/deserialization.
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2024-04-03T15:13:52.122Z")
public class Status {

    public enum CodeEnum {
        _0("0"),
        _1("1"),
        _2("2"),
        _3("3"),
        _4("4");

        private final String value;

        CodeEnum(String value) {
            this.value = value;
        }

        @JsonValue // Jackson uses this to serialize the value
        public String getValue() {
            return value;
        }

        @JsonCreator // Jackson uses this to deserialize the value
        public static CodeEnum fromValue(String text) {
            for (CodeEnum b : CodeEnum.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            return null;
        }
    }

    @JsonSerialize(using = ToStringSerializer.class) // Use ToStringSerializer for serializing enum values to JSON
    private CodeEnum code = null;

    public enum DescriptionEnum {
        PAGATO("Pagato"),
        NON_PAGATO("Non Pagato"),
        ANNULLATO_DALL_ENTE("Annullato dall'Ente"),
        NON_ANCORA_ATTIVA("Non ancora attiva"),
        NON_PIU_ATTIVA("Non piu' attiva");

        private final String value;

        DescriptionEnum(String value) {
            this.value = value;
        }

        @JsonValue // Jackson uses this to serialize the value
        public String getValue() {
            return value;
        }

        @JsonCreator // Jackson uses this to deserialize the value
        public static DescriptionEnum fromValue(String text) {
            for (DescriptionEnum b : DescriptionEnum.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            return null;
        }
    }

    @JsonSerialize(using = ToStringSerializer.class) // Use ToStringSerializer for serializing enum values to JSON
    private DescriptionEnum description = null;

    private Result result = null;

    // Getters, setters, and other methods remain unchanged

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Status status = (Status) o;
        return code == status.code &&
                description == status.description &&
                Objects.equals(result, status.result);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, description, result);
    }

    @Override
    public String toString() {
        return "Status{" +
                "code=" + code +
                ", description=" + description +
                ", result=" + result +
                '}';
    }

    // Example method to convert this object to a string with indentation
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
