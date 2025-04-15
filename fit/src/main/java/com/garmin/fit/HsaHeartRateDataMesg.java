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



public class HsaHeartRateDataMesg extends Mesg   {

    
    public static final int TimestampFieldNum = 253;
    
    public static final int ProcessingIntervalFieldNum = 0;
    
    public static final int StatusFieldNum = 1;
    
    public static final int HeartRateFieldNum = 2;
    

    protected static final  Mesg hsaHeartRateDataMesg;
    static {
        // hsa_heart_rate_data
        hsaHeartRateDataMesg = new Mesg("hsa_heart_rate_data", MesgNum.HSA_HEART_RATE_DATA);
        hsaHeartRateDataMesg.addField(new Field("timestamp", TimestampFieldNum, 134, 1, 0, "s", false, Profile.Type.DATE_TIME));
        
        hsaHeartRateDataMesg.addField(new Field("processing_interval", ProcessingIntervalFieldNum, 132, 1, 0, "s", false, Profile.Type.UINT16));
        
        hsaHeartRateDataMesg.addField(new Field("status", StatusFieldNum, 2, 1, 0, "", false, Profile.Type.UINT8));
        
        hsaHeartRateDataMesg.addField(new Field("heart_rate", HeartRateFieldNum, 2, 1, 0, "bpm", false, Profile.Type.UINT8));
        
    }

    public HsaHeartRateDataMesg() {
        super(Factory.createMesg(MesgNum.HSA_HEART_RATE_DATA));
    }

    public HsaHeartRateDataMesg(final Mesg mesg) {
        super(mesg);
    }


    /**
     * Get timestamp field
     * Units: s
     *
     * @return timestamp
     */
    public DateTime getTimestamp() {
        return timestampToDateTime(getFieldLongValue(253, 0, Fit.SUBFIELD_INDEX_MAIN_FIELD));
    }

    /**
     * Set timestamp field
     * Units: s
     *
     * @param timestamp The new timestamp value to be set
     */
    public void setTimestamp(DateTime timestamp) {
        setFieldValue(253, 0, timestamp.getTimestamp(), Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Get processing_interval field
     * Units: s
     * Comment: Processing interval length in seconds
     *
     * @return processing_interval
     */
    public Integer getProcessingInterval() {
        return getFieldIntegerValue(0, 0, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Set processing_interval field
     * Units: s
     * Comment: Processing interval length in seconds
     *
     * @param processingInterval The new processingInterval value to be set
     */
    public void setProcessingInterval(Integer processingInterval) {
        setFieldValue(0, 0, processingInterval, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Get status field
     * Comment: Status of measurements in buffer - 0 indicates SEARCHING 1 indicates LOCKED
     *
     * @return status
     */
    public Short getStatus() {
        return getFieldShortValue(1, 0, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Set status field
     * Comment: Status of measurements in buffer - 0 indicates SEARCHING 1 indicates LOCKED
     *
     * @param status The new status value to be set
     */
    public void setStatus(Short status) {
        setFieldValue(1, 0, status, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    public Short[] getHeartRate() {
        
        return getFieldShortValues(2, Fit.SUBFIELD_INDEX_MAIN_FIELD);
        
    }

    /**
     * @return number of heart_rate
     */
    public int getNumHeartRate() {
        return getNumFieldValues(2, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Get heart_rate field
     * Units: bpm
     * Comment: Beats / min. Blank: 0
     *
     * @param index of heart_rate
     * @return heart_rate
     */
    public Short getHeartRate(int index) {
        return getFieldShortValue(2, index, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Set heart_rate field
     * Units: bpm
     * Comment: Beats / min. Blank: 0
     *
     * @param index of heart_rate
     * @param heartRate The new heartRate value to be set
     */
    public void setHeartRate(int index, Short heartRate) {
        setFieldValue(2, index, heartRate, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

}
