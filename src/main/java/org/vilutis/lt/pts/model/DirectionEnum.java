package org.vilutis.lt.pts.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Trade direction:
 * <dl>
 *     <dt><code>buy</code></dt>
 *     <dd>Stock purchase ( buy )</dd>
 *     <dt><code>sell</code></dt>
 *     <dd>Stock liquidation ( sell )</dd>
 * </dl>
 */
public enum DirectionEnum {

    BUY("buy"),

    SELL("sell");

    private String value;

    DirectionEnum(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static DirectionEnum fromValue(String text) {
        for (DirectionEnum b : DirectionEnum.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }
}
