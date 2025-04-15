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

public class OlympicLiftExerciseName  {
    public static final int BARBELL_HANG_POWER_CLEAN = 0;
    public static final int BARBELL_HANG_SQUAT_CLEAN = 1;
    public static final int BARBELL_POWER_CLEAN = 2;
    public static final int BARBELL_POWER_SNATCH = 3;
    public static final int BARBELL_SQUAT_CLEAN = 4;
    public static final int CLEAN_AND_JERK = 5;
    public static final int BARBELL_HANG_POWER_SNATCH = 6;
    public static final int BARBELL_HANG_PULL = 7;
    public static final int BARBELL_HIGH_PULL = 8;
    public static final int BARBELL_SNATCH = 9;
    public static final int BARBELL_SPLIT_JERK = 10;
    public static final int CLEAN = 11;
    public static final int DUMBBELL_CLEAN = 12;
    public static final int DUMBBELL_HANG_PULL = 13;
    public static final int ONE_HAND_DUMBBELL_SPLIT_SNATCH = 14;
    public static final int PUSH_JERK = 15;
    public static final int SINGLE_ARM_DUMBBELL_SNATCH = 16;
    public static final int SINGLE_ARM_HANG_SNATCH = 17;
    public static final int SINGLE_ARM_KETTLEBELL_SNATCH = 18;
    public static final int SPLIT_JERK = 19;
    public static final int SQUAT_CLEAN_AND_JERK = 20;
    public static final int INVALID = Fit.UINT16_INVALID;

    private static final Map<Integer, String> stringMap;

    static {
        stringMap = new HashMap<Integer, String>();
        stringMap.put(BARBELL_HANG_POWER_CLEAN, "BARBELL_HANG_POWER_CLEAN");
        stringMap.put(BARBELL_HANG_SQUAT_CLEAN, "BARBELL_HANG_SQUAT_CLEAN");
        stringMap.put(BARBELL_POWER_CLEAN, "BARBELL_POWER_CLEAN");
        stringMap.put(BARBELL_POWER_SNATCH, "BARBELL_POWER_SNATCH");
        stringMap.put(BARBELL_SQUAT_CLEAN, "BARBELL_SQUAT_CLEAN");
        stringMap.put(CLEAN_AND_JERK, "CLEAN_AND_JERK");
        stringMap.put(BARBELL_HANG_POWER_SNATCH, "BARBELL_HANG_POWER_SNATCH");
        stringMap.put(BARBELL_HANG_PULL, "BARBELL_HANG_PULL");
        stringMap.put(BARBELL_HIGH_PULL, "BARBELL_HIGH_PULL");
        stringMap.put(BARBELL_SNATCH, "BARBELL_SNATCH");
        stringMap.put(BARBELL_SPLIT_JERK, "BARBELL_SPLIT_JERK");
        stringMap.put(CLEAN, "CLEAN");
        stringMap.put(DUMBBELL_CLEAN, "DUMBBELL_CLEAN");
        stringMap.put(DUMBBELL_HANG_PULL, "DUMBBELL_HANG_PULL");
        stringMap.put(ONE_HAND_DUMBBELL_SPLIT_SNATCH, "ONE_HAND_DUMBBELL_SPLIT_SNATCH");
        stringMap.put(PUSH_JERK, "PUSH_JERK");
        stringMap.put(SINGLE_ARM_DUMBBELL_SNATCH, "SINGLE_ARM_DUMBBELL_SNATCH");
        stringMap.put(SINGLE_ARM_HANG_SNATCH, "SINGLE_ARM_HANG_SNATCH");
        stringMap.put(SINGLE_ARM_KETTLEBELL_SNATCH, "SINGLE_ARM_KETTLEBELL_SNATCH");
        stringMap.put(SPLIT_JERK, "SPLIT_JERK");
        stringMap.put(SQUAT_CLEAN_AND_JERK, "SQUAT_CLEAN_AND_JERK");
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
