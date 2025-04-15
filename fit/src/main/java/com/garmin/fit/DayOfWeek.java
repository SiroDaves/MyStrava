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


public enum DayOfWeek  {
    SUNDAY((short)0),
    MONDAY((short)1),
    TUESDAY((short)2),
    WEDNESDAY((short)3),
    THURSDAY((short)4),
    FRIDAY((short)5),
    SATURDAY((short)6),
    INVALID((short)255);

    protected short value;

    private DayOfWeek(short value) {
        this.value = value;
    }

    public static DayOfWeek getByValue(final Short value) {
        for (final DayOfWeek type : DayOfWeek.values()) {
            if (value == type.value)
                return type;
        }

        return DayOfWeek.INVALID;
    }

    /**
     * Retrieves the String Representation of the Value
     * @param value The enum constant
     * @return The name of this enum constant
     */
    public static String getStringFromValue( DayOfWeek value ) {
        return value.name();
    }

    public short getValue() {
        return value;
    }


}
