package whenyourcar.redis.enums;

public enum RedisPrefixEnum {
    STREAM_KEY_PREFIX("chat:room:"),
    GROUP_NAME_PREFIX("chat:room:group:");
    private final String value;

    RedisPrefixEnum(String value) {
        this.value = value;
    }

    public String getValue(Long id) {
        return value+id;
    }
}
