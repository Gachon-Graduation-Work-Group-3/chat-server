package whenyourcar.storage.redis.data.enums;

public enum RedisPrefixEnum {
    STREAM_KEY_PREFIX("chat:user:"),
    GROUP_NAME_PREFIX("chat:user:group:");
    private final String value;

    RedisPrefixEnum(String value) {
        this.value = value;
    }

    public String getValue(Long id) {
        return value+id;
    }
}
