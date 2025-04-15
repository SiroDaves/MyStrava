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


package com.garmin.fit.csv;

import com.garmin.fit.Field;
import com.garmin.fit.FieldBase;
import com.garmin.fit.Fit;
import com.garmin.fit.Profile;
import com.garmin.fit.util.DateTimeConverter;
import com.garmin.fit.util.SemicirclesConverter;

import java.io.ByteArrayOutputStream;

public class MesgCSVWriterBase {
    protected CSVWriter csv;
    protected boolean bytesAsHex = false;
    protected boolean dateTimeAsISO8601 = false;
    protected boolean enumsAsStrings = false;
    protected boolean hideUnknownData = false;
    protected boolean showInvalidsAsEmpty = false;
    protected boolean removeExpandedFields = false;
    protected boolean preserveGaps = false;
    protected boolean semicirclesAsDegrees = false;

    public MesgCSVWriterBase(ByteArrayOutputStream byteArrayOutputStream) {
        this.csv = new CSVWriter(byteArrayOutputStream);
    }

    public void close() {
        csv.close();
    }

    public void enableHideUnknownData(boolean enable) {
        this.hideUnknownData = enable;
    }
    public boolean isHideUnknownDataEnabled() {
        return hideUnknownData;
    }

    public void enableBytesAsHex(boolean enable) {
        this.bytesAsHex = enable;
    }
    public boolean isBytesAsHexEnabled() {
        return bytesAsHex;
    }

    public void enableDateTimeAsISO8601(boolean enable) {
        this.dateTimeAsISO8601 = enable;
    }
    public boolean isDateTimeAsISO8601Enabled() {
        return dateTimeAsISO8601;
    }

    public void enableSemicirclesAsDegrees(boolean enable) {
        this.semicirclesAsDegrees = enable;
    }
    public boolean isSemicirclesAsDegreesEnabled() {
        return semicirclesAsDegrees;
    }

    public void enableShowInvalidsAsEmpty(boolean enable) {
        this.showInvalidsAsEmpty = enable;
    }
    public boolean isShowInvalidsAsEmptyEnabled() {
        return showInvalidsAsEmpty;
    }

    public void enableEnumsAsStrings(boolean enable) {
        this.enumsAsStrings = enable;
    }
    public boolean isEnumsAsStringsEnabled() {
        return enumsAsStrings;
    }

    public void enablePreserveGaps(boolean enable) {
        this.preserveGaps = enable; 
    }
    public boolean isPreserveGapsEnabled() {
        return preserveGaps;
    }

    public void enableRemoveExpandedFields(boolean enable) {
        this.removeExpandedFields = enable;
    }
    public boolean isRemoveExpandedFieldsEnabled() {
        return removeExpandedFields;
    }


    protected String getValueString(FieldBase fieldBase, int subFieldIndex) {
        Object value = fieldBase.getValue(0, subFieldIndex);
        StringBuilder outStringBuilder = new StringBuilder();
        String out;

        Profile.Type profileType;

        try {
            // This might fail as the fieldBase could be a developer field
            Field field = (Field) fieldBase;
            profileType = field.getProfileType();
        } catch (ClassCastException e) {
            // Default to dummy ENUM type
            profileType = Profile.Type.ENUM;
        }

        for (int fieldElement = 0; fieldElement < fieldBase.getNumValues(); fieldElement++) {
            if (value != null && !(showInvalidsAsEmpty && value.equals(Fit.baseTypeInvalidMap.get(fieldBase.getType(subFieldIndex))))) {
                value = fieldBase.getValue(fieldElement, subFieldIndex);

                if (bytesAsHex && fieldBase.getType(subFieldIndex) == Fit.BASE_TYPE_BYTE) {
                    outStringBuilder.append(String.format("0x%02x", value));
                }
                else if (semicirclesAsDegrees && fieldBase.getUnits().equals("semicircles")) {
                    double degrees = SemicirclesConverter.semicirclesToDegrees(Integer.parseInt(value.toString()));
                    outStringBuilder.append(String.format("%.8f", degrees));
                }
                else if (dateTimeAsISO8601 && profileType.name().equalsIgnoreCase("DATE_TIME")) {
                    outStringBuilder.append(DateTimeConverter.fitTimestampToISO8601((Long) value));
                }
                else if (enumsAsStrings && profileType.ordinal() > Profile.Type.BOOL.ordinal()) {
                    outStringBuilder.append(Profile.enumValueName(profileType, ((Number) value).longValue()));
                }
                else {
                    outStringBuilder.append(value.toString());
                }

                if (fieldElement != fieldBase.getNumValues() - 1) {
                    outStringBuilder.append('|');
                }

            }

        }

        out = outStringBuilder.toString();
        // Escapes embedded commas, double quotes, and newline characters
        out = out.replaceAll("\"", "\"\"");
        out = "\"" + out + "\"";
        return out;
    }

    protected String formatUnits(String units) {
        return formatUnits(units, null);
    }

    protected String formatUnits(String units, String profileType) {
        if (semicirclesAsDegrees && units.equalsIgnoreCase("semicircles")) {
            return "degrees";
        }
        if (dateTimeAsISO8601 && profileType != null && profileType.equalsIgnoreCase("DATE_TIME")) {
            return "";
        }
        else {
            return units;
        }
    }
}
