package io.tofpu.umbrella.domain.nbt;

public interface ItemNBTHandler {
    void setString(String key, String value);
    String getString(String key);
}
