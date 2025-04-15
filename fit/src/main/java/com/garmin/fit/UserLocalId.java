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

import java.util.HashMap;
import java.util.Map;

public class UserLocalId  {
    public static final int LOCAL_MIN = 0x0000;
    public static final int LOCAL_MAX = 0x000F;
    public static final int STATIONARY_MIN = 0x0010;
    public static final int STATIONARY_MAX = 0x00FF;
    public static final int PORTABLE_MIN = 0x0100;
    public static final int PORTABLE_MAX = 0xFFFE;
    public static final int INVALID = Fit.UINT16_INVALID;

    private static final Map<Integer, String> stringMap;

    static {
        stringMap = new HashMap<Integer, String>();
        stringMap.put(LOCAL_MIN, "LOCAL_MIN");
        stringMap.put(LOCAL_MAX, "LOCAL_MAX");
        stringMap.put(STATIONARY_MIN, "STATIONARY_MIN");
        stringMap.put(STATIONARY_MAX, "STATIONARY_MAX");
        stringMap.put(PORTABLE_MIN, "PORTABLE_MIN");
        stringMap.put(PORTABLE_MAX, "PORTABLE_MAX");
    }


    /**
     * Retrieves the String Representation of the Value
     * @param value The enum constant
     * @return The name of this enum contsant
     */
    public static String getStringFromValue( Integer value ) {
        if( stringMap.containsKey( value ) ) {
            return stringMap.get( value );
        }

        return "";
    }

    /**
     * Returns the enum constant with the specified name.
     * @param value The enum string value
     * @return The enum constant or INVALID if unknown
     */
    public static Integer getValueFromString( String value ) {
        for( Map.Entry<Integer, String> entry : stringMap.entrySet() ) {
            if( entry.getValue().equals( value ) ) {
                return entry.getKey();
            }
        }

        return INVALID;
    }

}
