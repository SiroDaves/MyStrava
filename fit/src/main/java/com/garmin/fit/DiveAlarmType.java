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


public enum DiveAlarmType  {
    DEPTH((short)0),
    TIME((short)1),
    SPEED((short)2),
    INVALID((short)255);

    protected short value;

    private DiveAlarmType(short value) {
        this.value = value;
    }

    public static DiveAlarmType getByValue(final Short value) {
        for (final DiveAlarmType type : DiveAlarmType.values()) {
            if (value == type.value)
                return type;
        }

        return DiveAlarmType.INVALID;
    }

    /**
     * Retrieves the String Representation of the Value
     * @param value The enum constant
     * @return The name of this enum constant
     */
    public static String getStringFromValue( DiveAlarmType value ) {
        return value.name();
    }

    public short getValue() {
        return value;
    }


}
