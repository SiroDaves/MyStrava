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



public class BikeProfileMesg extends Mesg   {

    
    public static final int MessageIndexFieldNum = 254;
    
    public static final int NameFieldNum = 0;
    
    public static final int SportFieldNum = 1;
    
    public static final int SubSportFieldNum = 2;
    
    public static final int OdometerFieldNum = 3;
    
    public static final int BikeSpdAntIdFieldNum = 4;
    
    public static final int BikeCadAntIdFieldNum = 5;
    
    public static final int BikeSpdcadAntIdFieldNum = 6;
    
    public static final int BikePowerAntIdFieldNum = 7;
    
    public static final int CustomWheelsizeFieldNum = 8;
    
    public static final int AutoWheelsizeFieldNum = 9;
    
    public static final int BikeWeightFieldNum = 10;
    
    public static final int PowerCalFactorFieldNum = 11;
    
    public static final int AutoWheelCalFieldNum = 12;
    
    public static final int AutoPowerZeroFieldNum = 13;
    
    public static final int IdFieldNum = 14;
    
    public static final int SpdEnabledFieldNum = 15;
    
    public static final int CadEnabledFieldNum = 16;
    
    public static final int SpdcadEnabledFieldNum = 17;
    
    public static final int PowerEnabledFieldNum = 18;
    
    public static final int CrankLengthFieldNum = 19;
    
    public static final int EnabledFieldNum = 20;
    
    public static final int BikeSpdAntIdTransTypeFieldNum = 21;
    
    public static final int BikeCadAntIdTransTypeFieldNum = 22;
    
    public static final int BikeSpdcadAntIdTransTypeFieldNum = 23;
    
    public static final int BikePowerAntIdTransTypeFieldNum = 24;
    
    public static final int OdometerRolloverFieldNum = 37;
    
    public static final int FrontGearNumFieldNum = 38;
    
    public static final int FrontGearFieldNum = 39;
    
    public static final int RearGearNumFieldNum = 40;
    
    public static final int RearGearFieldNum = 41;
    
    public static final int ShimanoDi2EnabledFieldNum = 44;
    

    protected static final  Mesg bikeProfileMesg;
    static {
        // bike_profile
        bikeProfileMesg = new Mesg("bike_profile", MesgNum.BIKE_PROFILE);
        bikeProfileMesg.addField(new Field("message_index", MessageIndexFieldNum, 132, 1, 0, "", false, Profile.Type.MESSAGE_INDEX));
        
        bikeProfileMesg.addField(new Field("name", NameFieldNum, 7, 1, 0, "", false, Profile.Type.STRING));
        
        bikeProfileMesg.addField(new Field("sport", SportFieldNum, 0, 1, 0, "", false, Profile.Type.SPORT));
        
        bikeProfileMesg.addField(new Field("sub_sport", SubSportFieldNum, 0, 1, 0, "", false, Profile.Type.SUB_SPORT));
        
        bikeProfileMesg.addField(new Field("odometer", OdometerFieldNum, 134, 100, 0, "m", false, Profile.Type.UINT32));
        
        bikeProfileMesg.addField(new Field("bike_spd_ant_id", BikeSpdAntIdFieldNum, 139, 1, 0, "", false, Profile.Type.UINT16Z));
        
        bikeProfileMesg.addField(new Field("bike_cad_ant_id", BikeCadAntIdFieldNum, 139, 1, 0, "", false, Profile.Type.UINT16Z));
        
        bikeProfileMesg.addField(new Field("bike_spdcad_ant_id", BikeSpdcadAntIdFieldNum, 139, 1, 0, "", false, Profile.Type.UINT16Z));
        
        bikeProfileMesg.addField(new Field("bike_power_ant_id", BikePowerAntIdFieldNum, 139, 1, 0, "", false, Profile.Type.UINT16Z));
        
        bikeProfileMesg.addField(new Field("custom_wheelsize", CustomWheelsizeFieldNum, 132, 1000, 0, "m", false, Profile.Type.UINT16));
        
        bikeProfileMesg.addField(new Field("auto_wheelsize", AutoWheelsizeFieldNum, 132, 1000, 0, "m", false, Profile.Type.UINT16));
        
        bikeProfileMesg.addField(new Field("bike_weight", BikeWeightFieldNum, 132, 10, 0, "kg", false, Profile.Type.UINT16));
        
        bikeProfileMesg.addField(new Field("power_cal_factor", PowerCalFactorFieldNum, 132, 10, 0, "%", false, Profile.Type.UINT16));
        
        bikeProfileMesg.addField(new Field("auto_wheel_cal", AutoWheelCalFieldNum, 0, 1, 0, "", false, Profile.Type.BOOL));
        
        bikeProfileMesg.addField(new Field("auto_power_zero", AutoPowerZeroFieldNum, 0, 1, 0, "", false, Profile.Type.BOOL));
        
        bikeProfileMesg.addField(new Field("id", IdFieldNum, 2, 1, 0, "", false, Profile.Type.UINT8));
        
        bikeProfileMesg.addField(new Field("spd_enabled", SpdEnabledFieldNum, 0, 1, 0, "", false, Profile.Type.BOOL));
        
        bikeProfileMesg.addField(new Field("cad_enabled", CadEnabledFieldNum, 0, 1, 0, "", false, Profile.Type.BOOL));
        
        bikeProfileMesg.addField(new Field("spdcad_enabled", SpdcadEnabledFieldNum, 0, 1, 0, "", false, Profile.Type.BOOL));
        
        bikeProfileMesg.addField(new Field("power_enabled", PowerEnabledFieldNum, 0, 1, 0, "", false, Profile.Type.BOOL));
        
        bikeProfileMesg.addField(new Field("crank_length", CrankLengthFieldNum, 2, 2, -110, "mm", false, Profile.Type.UINT8));
        
        bikeProfileMesg.addField(new Field("enabled", EnabledFieldNum, 0, 1, 0, "", false, Profile.Type.BOOL));
        
        bikeProfileMesg.addField(new Field("bike_spd_ant_id_trans_type", BikeSpdAntIdTransTypeFieldNum, 10, 1, 0, "", false, Profile.Type.UINT8Z));
        
        bikeProfileMesg.addField(new Field("bike_cad_ant_id_trans_type", BikeCadAntIdTransTypeFieldNum, 10, 1, 0, "", false, Profile.Type.UINT8Z));
        
        bikeProfileMesg.addField(new Field("bike_spdcad_ant_id_trans_type", BikeSpdcadAntIdTransTypeFieldNum, 10, 1, 0, "", false, Profile.Type.UINT8Z));
        
        bikeProfileMesg.addField(new Field("bike_power_ant_id_trans_type", BikePowerAntIdTransTypeFieldNum, 10, 1, 0, "", false, Profile.Type.UINT8Z));
        
        bikeProfileMesg.addField(new Field("odometer_rollover", OdometerRolloverFieldNum, 2, 1, 0, "", false, Profile.Type.UINT8));
        
        bikeProfileMesg.addField(new Field("front_gear_num", FrontGearNumFieldNum, 10, 1, 0, "", false, Profile.Type.UINT8Z));
        
        bikeProfileMesg.addField(new Field("front_gear", FrontGearFieldNum, 10, 1, 0, "", false, Profile.Type.UINT8Z));
        
        bikeProfileMesg.addField(new Field("rear_gear_num", RearGearNumFieldNum, 10, 1, 0, "", false, Profile.Type.UINT8Z));
        
        bikeProfileMesg.addField(new Field("rear_gear", RearGearFieldNum, 10, 1, 0, "", false, Profile.Type.UINT8Z));
        
        bikeProfileMesg.addField(new Field("shimano_di2_enabled", ShimanoDi2EnabledFieldNum, 0, 1, 0, "", false, Profile.Type.BOOL));
        
    }

    public BikeProfileMesg() {
        super(Factory.createMesg(MesgNum.BIKE_PROFILE));
    }

    public BikeProfileMesg(final Mesg mesg) {
        super(mesg);
    }


    /**
     * Get message_index field
     *
     * @return message_index
     */
    public Integer getMessageIndex() {
        return getFieldIntegerValue(254, 0, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Set message_index field
     *
     * @param messageIndex The new messageIndex value to be set
     */
    public void setMessageIndex(Integer messageIndex) {
        setFieldValue(254, 0, messageIndex, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Get name field
     *
     * @return name
     */
    public String getName() {
        return getFieldStringValue(0, 0, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Set name field
     *
     * @param name The new name value to be set
     */
    public void setName(String name) {
        setFieldValue(0, 0, name, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Get sport field
     *
     * @return sport
     */
    public Sport getSport() {
        Short value = getFieldShortValue(1, 0, Fit.SUBFIELD_INDEX_MAIN_FIELD);
        if (value == null) {
            return null;
        }
        return Sport.getByValue(value);
    }

    /**
     * Set sport field
     *
     * @param sport The new sport value to be set
     */
    public void setSport(Sport sport) {
        setFieldValue(1, 0, sport.value, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Get sub_sport field
     *
     * @return sub_sport
     */
    public SubSport getSubSport() {
        Short value = getFieldShortValue(2, 0, Fit.SUBFIELD_INDEX_MAIN_FIELD);
        if (value == null) {
            return null;
        }
        return SubSport.getByValue(value);
    }

    /**
     * Set sub_sport field
     *
     * @param subSport The new subSport value to be set
     */
    public void setSubSport(SubSport subSport) {
        setFieldValue(2, 0, subSport.value, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Get odometer field
     * Units: m
     *
     * @return odometer
     */
    public Float getOdometer() {
        return getFieldFloatValue(3, 0, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Set odometer field
     * Units: m
     *
     * @param odometer The new odometer value to be set
     */
    public void setOdometer(Float odometer) {
        setFieldValue(3, 0, odometer, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Get bike_spd_ant_id field
     *
     * @return bike_spd_ant_id
     */
    public Integer getBikeSpdAntId() {
        return getFieldIntegerValue(4, 0, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Set bike_spd_ant_id field
     *
     * @param bikeSpdAntId The new bikeSpdAntId value to be set
     */
    public void setBikeSpdAntId(Integer bikeSpdAntId) {
        setFieldValue(4, 0, bikeSpdAntId, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Get bike_cad_ant_id field
     *
     * @return bike_cad_ant_id
     */
    public Integer getBikeCadAntId() {
        return getFieldIntegerValue(5, 0, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Set bike_cad_ant_id field
     *
     * @param bikeCadAntId The new bikeCadAntId value to be set
     */
    public void setBikeCadAntId(Integer bikeCadAntId) {
        setFieldValue(5, 0, bikeCadAntId, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Get bike_spdcad_ant_id field
     *
     * @return bike_spdcad_ant_id
     */
    public Integer getBikeSpdcadAntId() {
        return getFieldIntegerValue(6, 0, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Set bike_spdcad_ant_id field
     *
     * @param bikeSpdcadAntId The new bikeSpdcadAntId value to be set
     */
    public void setBikeSpdcadAntId(Integer bikeSpdcadAntId) {
        setFieldValue(6, 0, bikeSpdcadAntId, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Get bike_power_ant_id field
     *
     * @return bike_power_ant_id
     */
    public Integer getBikePowerAntId() {
        return getFieldIntegerValue(7, 0, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Set bike_power_ant_id field
     *
     * @param bikePowerAntId The new bikePowerAntId value to be set
     */
    public void setBikePowerAntId(Integer bikePowerAntId) {
        setFieldValue(7, 0, bikePowerAntId, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Get custom_wheelsize field
     * Units: m
     *
     * @return custom_wheelsize
     */
    public Float getCustomWheelsize() {
        return getFieldFloatValue(8, 0, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Set custom_wheelsize field
     * Units: m
     *
     * @param customWheelsize The new customWheelsize value to be set
     */
    public void setCustomWheelsize(Float customWheelsize) {
        setFieldValue(8, 0, customWheelsize, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Get auto_wheelsize field
     * Units: m
     *
     * @return auto_wheelsize
     */
    public Float getAutoWheelsize() {
        return getFieldFloatValue(9, 0, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Set auto_wheelsize field
     * Units: m
     *
     * @param autoWheelsize The new autoWheelsize value to be set
     */
    public void setAutoWheelsize(Float autoWheelsize) {
        setFieldValue(9, 0, autoWheelsize, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Get bike_weight field
     * Units: kg
     *
     * @return bike_weight
     */
    public Float getBikeWeight() {
        return getFieldFloatValue(10, 0, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Set bike_weight field
     * Units: kg
     *
     * @param bikeWeight The new bikeWeight value to be set
     */
    public void setBikeWeight(Float bikeWeight) {
        setFieldValue(10, 0, bikeWeight, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Get power_cal_factor field
     * Units: %
     *
     * @return power_cal_factor
     */
    public Float getPowerCalFactor() {
        return getFieldFloatValue(11, 0, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Set power_cal_factor field
     * Units: %
     *
     * @param powerCalFactor The new powerCalFactor value to be set
     */
    public void setPowerCalFactor(Float powerCalFactor) {
        setFieldValue(11, 0, powerCalFactor, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Get auto_wheel_cal field
     *
     * @return auto_wheel_cal
     */
    public Bool getAutoWheelCal() {
        Short value = getFieldShortValue(12, 0, Fit.SUBFIELD_INDEX_MAIN_FIELD);
        if (value == null) {
            return null;
        }
        return Bool.getByValue(value);
    }

    /**
     * Set auto_wheel_cal field
     *
     * @param autoWheelCal The new autoWheelCal value to be set
     */
    public void setAutoWheelCal(Bool autoWheelCal) {
        setFieldValue(12, 0, autoWheelCal.value, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Get auto_power_zero field
     *
     * @return auto_power_zero
     */
    public Bool getAutoPowerZero() {
        Short value = getFieldShortValue(13, 0, Fit.SUBFIELD_INDEX_MAIN_FIELD);
        if (value == null) {
            return null;
        }
        return Bool.getByValue(value);
    }

    /**
     * Set auto_power_zero field
     *
     * @param autoPowerZero The new autoPowerZero value to be set
     */
    public void setAutoPowerZero(Bool autoPowerZero) {
        setFieldValue(13, 0, autoPowerZero.value, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Get id field
     *
     * @return id
     */
    public Short getId() {
        return getFieldShortValue(14, 0, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Set id field
     *
     * @param id The new id value to be set
     */
    public void setId(Short id) {
        setFieldValue(14, 0, id, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Get spd_enabled field
     *
     * @return spd_enabled
     */
    public Bool getSpdEnabled() {
        Short value = getFieldShortValue(15, 0, Fit.SUBFIELD_INDEX_MAIN_FIELD);
        if (value == null) {
            return null;
        }
        return Bool.getByValue(value);
    }

    /**
     * Set spd_enabled field
     *
     * @param spdEnabled The new spdEnabled value to be set
     */
    public void setSpdEnabled(Bool spdEnabled) {
        setFieldValue(15, 0, spdEnabled.value, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Get cad_enabled field
     *
     * @return cad_enabled
     */
    public Bool getCadEnabled() {
        Short value = getFieldShortValue(16, 0, Fit.SUBFIELD_INDEX_MAIN_FIELD);
        if (value == null) {
            return null;
        }
        return Bool.getByValue(value);
    }

    /**
     * Set cad_enabled field
     *
     * @param cadEnabled The new cadEnabled value to be set
     */
    public void setCadEnabled(Bool cadEnabled) {
        setFieldValue(16, 0, cadEnabled.value, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Get spdcad_enabled field
     *
     * @return spdcad_enabled
     */
    public Bool getSpdcadEnabled() {
        Short value = getFieldShortValue(17, 0, Fit.SUBFIELD_INDEX_MAIN_FIELD);
        if (value == null) {
            return null;
        }
        return Bool.getByValue(value);
    }

    /**
     * Set spdcad_enabled field
     *
     * @param spdcadEnabled The new spdcadEnabled value to be set
     */
    public void setSpdcadEnabled(Bool spdcadEnabled) {
        setFieldValue(17, 0, spdcadEnabled.value, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Get power_enabled field
     *
     * @return power_enabled
     */
    public Bool getPowerEnabled() {
        Short value = getFieldShortValue(18, 0, Fit.SUBFIELD_INDEX_MAIN_FIELD);
        if (value == null) {
            return null;
        }
        return Bool.getByValue(value);
    }

    /**
     * Set power_enabled field
     *
     * @param powerEnabled The new powerEnabled value to be set
     */
    public void setPowerEnabled(Bool powerEnabled) {
        setFieldValue(18, 0, powerEnabled.value, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Get crank_length field
     * Units: mm
     *
     * @return crank_length
     */
    public Float getCrankLength() {
        return getFieldFloatValue(19, 0, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Set crank_length field
     * Units: mm
     *
     * @param crankLength The new crankLength value to be set
     */
    public void setCrankLength(Float crankLength) {
        setFieldValue(19, 0, crankLength, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Get enabled field
     *
     * @return enabled
     */
    public Bool getEnabled() {
        Short value = getFieldShortValue(20, 0, Fit.SUBFIELD_INDEX_MAIN_FIELD);
        if (value == null) {
            return null;
        }
        return Bool.getByValue(value);
    }

    /**
     * Set enabled field
     *
     * @param enabled The new enabled value to be set
     */
    public void setEnabled(Bool enabled) {
        setFieldValue(20, 0, enabled.value, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Get bike_spd_ant_id_trans_type field
     *
     * @return bike_spd_ant_id_trans_type
     */
    public Short getBikeSpdAntIdTransType() {
        return getFieldShortValue(21, 0, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Set bike_spd_ant_id_trans_type field
     *
     * @param bikeSpdAntIdTransType The new bikeSpdAntIdTransType value to be set
     */
    public void setBikeSpdAntIdTransType(Short bikeSpdAntIdTransType) {
        setFieldValue(21, 0, bikeSpdAntIdTransType, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Get bike_cad_ant_id_trans_type field
     *
     * @return bike_cad_ant_id_trans_type
     */
    public Short getBikeCadAntIdTransType() {
        return getFieldShortValue(22, 0, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Set bike_cad_ant_id_trans_type field
     *
     * @param bikeCadAntIdTransType The new bikeCadAntIdTransType value to be set
     */
    public void setBikeCadAntIdTransType(Short bikeCadAntIdTransType) {
        setFieldValue(22, 0, bikeCadAntIdTransType, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Get bike_spdcad_ant_id_trans_type field
     *
     * @return bike_spdcad_ant_id_trans_type
     */
    public Short getBikeSpdcadAntIdTransType() {
        return getFieldShortValue(23, 0, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Set bike_spdcad_ant_id_trans_type field
     *
     * @param bikeSpdcadAntIdTransType The new bikeSpdcadAntIdTransType value to be set
     */
    public void setBikeSpdcadAntIdTransType(Short bikeSpdcadAntIdTransType) {
        setFieldValue(23, 0, bikeSpdcadAntIdTransType, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Get bike_power_ant_id_trans_type field
     *
     * @return bike_power_ant_id_trans_type
     */
    public Short getBikePowerAntIdTransType() {
        return getFieldShortValue(24, 0, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Set bike_power_ant_id_trans_type field
     *
     * @param bikePowerAntIdTransType The new bikePowerAntIdTransType value to be set
     */
    public void setBikePowerAntIdTransType(Short bikePowerAntIdTransType) {
        setFieldValue(24, 0, bikePowerAntIdTransType, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Get odometer_rollover field
     * Comment: Rollover counter that can be used to extend the odometer
     *
     * @return odometer_rollover
     */
    public Short getOdometerRollover() {
        return getFieldShortValue(37, 0, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Set odometer_rollover field
     * Comment: Rollover counter that can be used to extend the odometer
     *
     * @param odometerRollover The new odometerRollover value to be set
     */
    public void setOdometerRollover(Short odometerRollover) {
        setFieldValue(37, 0, odometerRollover, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Get front_gear_num field
     * Comment: Number of front gears
     *
     * @return front_gear_num
     */
    public Short getFrontGearNum() {
        return getFieldShortValue(38, 0, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Set front_gear_num field
     * Comment: Number of front gears
     *
     * @param frontGearNum The new frontGearNum value to be set
     */
    public void setFrontGearNum(Short frontGearNum) {
        setFieldValue(38, 0, frontGearNum, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    public Short[] getFrontGear() {
        
        return getFieldShortValues(39, Fit.SUBFIELD_INDEX_MAIN_FIELD);
        
    }

    /**
     * @return number of front_gear
     */
    public int getNumFrontGear() {
        return getNumFieldValues(39, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Get front_gear field
     * Comment: Number of teeth on each gear 0 is innermost
     *
     * @param index of front_gear
     * @return front_gear
     */
    public Short getFrontGear(int index) {
        return getFieldShortValue(39, index, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Set front_gear field
     * Comment: Number of teeth on each gear 0 is innermost
     *
     * @param index of front_gear
     * @param frontGear The new frontGear value to be set
     */
    public void setFrontGear(int index, Short frontGear) {
        setFieldValue(39, index, frontGear, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Get rear_gear_num field
     * Comment: Number of rear gears
     *
     * @return rear_gear_num
     */
    public Short getRearGearNum() {
        return getFieldShortValue(40, 0, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Set rear_gear_num field
     * Comment: Number of rear gears
     *
     * @param rearGearNum The new rearGearNum value to be set
     */
    public void setRearGearNum(Short rearGearNum) {
        setFieldValue(40, 0, rearGearNum, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    public Short[] getRearGear() {
        
        return getFieldShortValues(41, Fit.SUBFIELD_INDEX_MAIN_FIELD);
        
    }

    /**
     * @return number of rear_gear
     */
    public int getNumRearGear() {
        return getNumFieldValues(41, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Get rear_gear field
     * Comment: Number of teeth on each gear 0 is innermost
     *
     * @param index of rear_gear
     * @return rear_gear
     */
    public Short getRearGear(int index) {
        return getFieldShortValue(41, index, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Set rear_gear field
     * Comment: Number of teeth on each gear 0 is innermost
     *
     * @param index of rear_gear
     * @param rearGear The new rearGear value to be set
     */
    public void setRearGear(int index, Short rearGear) {
        setFieldValue(41, index, rearGear, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Get shimano_di2_enabled field
     *
     * @return shimano_di2_enabled
     */
    public Bool getShimanoDi2Enabled() {
        Short value = getFieldShortValue(44, 0, Fit.SUBFIELD_INDEX_MAIN_FIELD);
        if (value == null) {
            return null;
        }
        return Bool.getByValue(value);
    }

    /**
     * Set shimano_di2_enabled field
     *
     * @param shimanoDi2Enabled The new shimanoDi2Enabled value to be set
     */
    public void setShimanoDi2Enabled(Bool shimanoDi2Enabled) {
        setFieldValue(44, 0, shimanoDi2Enabled.value, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

}
