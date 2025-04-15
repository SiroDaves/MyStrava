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


public enum SplitType  {
    ASCENT_SPLIT((short)1),
    DESCENT_SPLIT((short)2),
    INTERVAL_ACTIVE((short)3),
    INTERVAL_REST((short)4),
    INTERVAL_WARMUP((short)5),
    INTERVAL_COOLDOWN((short)6),
    INTERVAL_RECOVERY((short)7),
    INTERVAL_OTHER((short)8),
    CLIMB_ACTIVE((short)9),
    CLIMB_REST((short)10),
    SURF_ACTIVE((short)11),
    RUN_ACTIVE((short)12),
    RUN_REST((short)13),
    WORKOUT_ROUND((short)14),
    RWD_RUN((short)17),
    RWD_WALK((short)18),
    WINDSURF_ACTIVE((short)21),
    RWD_STAND((short)22),
    TRANSITION((short)23),
    SKI_LIFT_SPLIT((short)28),
    SKI_RUN_SPLIT((short)29),
    INVALID((short)255);

    protected short value;

    private SplitType(short value) {
        this.value = value;
    }

    public static SplitType getByValue(final Short value) {
        for (final SplitType type : SplitType.values()) {
            if (value == type.value)
                return type;
        }

        return SplitType.INVALID;
    }

    /**
     * Retrieves the String Representation of the Value
     * @param value The enum constant
     * @return The name of this enum constant
     */
    public static String getStringFromValue( SplitType value ) {
        return value.name();
    }

    public short getValue() {
        return value;
    }


}
