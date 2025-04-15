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

public class CourseCapabilities  {
    public static final long PROCESSED = 0x00000001;
    public static final long VALID = 0x00000002;
    public static final long TIME = 0x00000004;
    public static final long DISTANCE = 0x00000008;
    public static final long POSITION = 0x00000010;
    public static final long HEART_RATE = 0x00000020;
    public static final long POWER = 0x00000040;
    public static final long CADENCE = 0x00000080;
    public static final long TRAINING = 0x00000100;
    public static final long NAVIGATION = 0x00000200;
    public static final long BIKEWAY = 0x00000400;
    public static final long AVIATION = 0x00001000; // Denote course files to be used as flight plans
    public static final long INVALID = Fit.UINT32Z_INVALID;

    private static final Map<Long, String> stringMap;

    static {
        stringMap = new HashMap<Long, String>();
        stringMap.put(PROCESSED, "PROCESSED");
        stringMap.put(VALID, "VALID");
        stringMap.put(TIME, "TIME");
        stringMap.put(DISTANCE, "DISTANCE");
        stringMap.put(POSITION, "POSITION");
        stringMap.put(HEART_RATE, "HEART_RATE");
        stringMap.put(POWER, "POWER");
        stringMap.put(CADENCE, "CADENCE");
        stringMap.put(TRAINING, "TRAINING");
        stringMap.put(NAVIGATION, "NAVIGATION");
        stringMap.put(BIKEWAY, "BIKEWAY");
        stringMap.put(AVIATION, "AVIATION");
    }


    /**
     * Retrieves the String Representation of the Value
     * @param value The enum constant
     * @return The name of this enum contsant
     */
    public static String getStringFromValue( Long value ) {
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
    public static Long getValueFromString( String value ) {
        for( Map.Entry<Long, String> entry : stringMap.entrySet() ) {
            if( entry.getValue().equals( value ) ) {
                return entry.getKey();
            }
        }

        return INVALID;
    }

}
