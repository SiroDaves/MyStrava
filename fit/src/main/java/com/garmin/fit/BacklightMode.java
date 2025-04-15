/////////////////////////////////////////////////////////////////////////////////////////////
// Copyright 2024 Garmin International, Inc.
// Licensed under the Flexible and Interoperable Data Transfer (FIT) Protocol License; you
// may not use this file except in compliance with the Flexible and Interoperable Data
// Transfer (FIT) Protocol License.
/////////////////////////////////////////////////////////////////////////////////////////////
// ****WARNING****  This file is auto-generated!  Do NOT edit this file.
// Profile Version = 21.158.0Release
// Tag = production/release/21.158.0-0-gc9428aa
/////////////////////////////////////////////////////////////////////////////////////////////


package com.garmin.fit;


public enum BacklightMode  {
    OFF((short)0),
    MANUAL((short)1),
    KEY_AND_MESSAGES((short)2),
    AUTO_BRIGHTNESS((short)3),
    SMART_NOTIFICATIONS((short)4),
    KEY_AND_MESSAGES_NIGHT((short)5),
    KEY_AND_MESSAGES_AND_SMART_NOTIFICATIONS((short)6),
    INVALID((short)255);

    protected short value;

    private BacklightMode(short value) {
        this.value = value;
    }

    public static BacklightMode getByValue(final Short value) {
        for (final BacklightMode type : BacklightMode.values()) {
            if (value == type.value)
                return type;
        }

        return BacklightMode.INVALID;
    }

    /**
     * Retrieves the String Representation of the Value
     * @param value The enum constant
     * @return The name of this enum constant
     */
    public static String getStringFromValue( BacklightMode value ) {
        return value.name();
    }

    public short getValue() {
        return value;
    }


}
