package org.vilutis.lt.pts.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Trade direction:
 * <ul>
 *   <li>`buy` - Stock purchase ( buy )</li>
 *   <li>`sell` - Stock liquidation ( sell )</li>
 * </ul>
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
