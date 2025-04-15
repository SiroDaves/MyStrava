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



public class SleepAssessmentMesg extends Mesg   {

    
    public static final int CombinedAwakeScoreFieldNum = 0;
    
    public static final int AwakeTimeScoreFieldNum = 1;
    
    public static final int AwakeningsCountScoreFieldNum = 2;
    
    public static final int DeepSleepScoreFieldNum = 3;
    
    public static final int SleepDurationScoreFieldNum = 4;
    
    public static final int LightSleepScoreFieldNum = 5;
    
    public static final int OverallSleepScoreFieldNum = 6;
    
    public static final int SleepQualityScoreFieldNum = 7;
    
    public static final int SleepRecoveryScoreFieldNum = 8;
    
    public static final int RemSleepScoreFieldNum = 9;
    
    public static final int SleepRestlessnessScoreFieldNum = 10;
    
    public static final int AwakeningsCountFieldNum = 11;
    
    public static final int InterruptionsScoreFieldNum = 14;
    
    public static final int AverageStressDuringSleepFieldNum = 15;
    

    protected static final  Mesg sleepAssessmentMesg;
    static {
        // sleep_assessment
        sleepAssessmentMesg = new Mesg("sleep_assessment", MesgNum.SLEEP_ASSESSMENT);
        sleepAssessmentMesg.addField(new Field("combined_awake_score", CombinedAwakeScoreFieldNum, 2, 1, 0, "", false, Profile.Type.UINT8));
        
        sleepAssessmentMesg.addField(new Field("awake_time_score", AwakeTimeScoreFieldNum, 2, 1, 0, "", false, Profile.Type.UINT8));
        
        sleepAssessmentMesg.addField(new Field("awakenings_count_score", AwakeningsCountScoreFieldNum, 2, 1, 0, "", false, Profile.Type.UINT8));
        
        sleepAssessmentMesg.addField(new Field("deep_sleep_score", DeepSleepScoreFieldNum, 2, 1, 0, "", false, Profile.Type.UINT8));
        
        sleepAssessmentMesg.addField(new Field("sleep_duration_score", SleepDurationScoreFieldNum, 2, 1, 0, "", false, Profile.Type.UINT8));
        
        sleepAssessmentMesg.addField(new Field("light_sleep_score", LightSleepScoreFieldNum, 2, 1, 0, "", false, Profile.Type.UINT8));
        
        sleepAssessmentMesg.addField(new Field("overall_sleep_score", OverallSleepScoreFieldNum, 2, 1, 0, "", false, Profile.Type.UINT8));
        
        sleepAssessmentMesg.addField(new Field("sleep_quality_score", SleepQualityScoreFieldNum, 2, 1, 0, "", false, Profile.Type.UINT8));
        
        sleepAssessmentMesg.addField(new Field("sleep_recovery_score", SleepRecoveryScoreFieldNum, 2, 1, 0, "", false, Profile.Type.UINT8));
        
        sleepAssessmentMesg.addField(new Field("rem_sleep_score", RemSleepScoreFieldNum, 2, 1, 0, "", false, Profile.Type.UINT8));
        
        sleepAssessmentMesg.addField(new Field("sleep_restlessness_score", SleepRestlessnessScoreFieldNum, 2, 1, 0, "", false, Profile.Type.UINT8));
        
        sleepAssessmentMesg.addField(new Field("awakenings_count", AwakeningsCountFieldNum, 2, 1, 0, "", false, Profile.Type.UINT8));
        
        sleepAssessmentMesg.addField(new Field("interruptions_score", InterruptionsScoreFieldNum, 2, 1, 0, "", false, Profile.Type.UINT8));
        
        sleepAssessmentMesg.addField(new Field("average_stress_during_sleep", AverageStressDuringSleepFieldNum, 132, 100, 0, "", false, Profile.Type.UINT16));
        
    }

    public SleepAssessmentMesg() {
        super(Factory.createMesg(MesgNum.SLEEP_ASSESSMENT));
    }

    public SleepAssessmentMesg(final Mesg mesg) {
        super(mesg);
    }


    /**
     * Get combined_awake_score field
     * Comment: Average of awake_time_score and awakenings_count_score. If valid: 0 (worst) to 100 (best). If unknown: FIT_UINT8_INVALID.
     *
     * @return combined_awake_score
     */
    public Short getCombinedAwakeScore() {
        return getFieldShortValue(0, 0, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Set combined_awake_score field
     * Comment: Average of awake_time_score and awakenings_count_score. If valid: 0 (worst) to 100 (best). If unknown: FIT_UINT8_INVALID.
     *
     * @param combinedAwakeScore The new combinedAwakeScore value to be set
     */
    public void setCombinedAwakeScore(Short combinedAwakeScore) {
        setFieldValue(0, 0, combinedAwakeScore, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Get awake_time_score field
     * Comment: Score that evaluates the total time spent awake between sleep. If valid: 0 (worst) to 100 (best). If unknown: FIT_UINT8_INVALID.
     *
     * @return awake_time_score
     */
    public Short getAwakeTimeScore() {
        return getFieldShortValue(1, 0, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Set awake_time_score field
     * Comment: Score that evaluates the total time spent awake between sleep. If valid: 0 (worst) to 100 (best). If unknown: FIT_UINT8_INVALID.
     *
     * @param awakeTimeScore The new awakeTimeScore value to be set
     */
    public void setAwakeTimeScore(Short awakeTimeScore) {
        setFieldValue(1, 0, awakeTimeScore, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Get awakenings_count_score field
     * Comment: Score that evaluates the number of awakenings that interrupt sleep. If valid: 0 (worst) to 100 (best). If unknown: FIT_UINT8_INVALID.
     *
     * @return awakenings_count_score
     */
    public Short getAwakeningsCountScore() {
        return getFieldShortValue(2, 0, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Set awakenings_count_score field
     * Comment: Score that evaluates the number of awakenings that interrupt sleep. If valid: 0 (worst) to 100 (best). If unknown: FIT_UINT8_INVALID.
     *
     * @param awakeningsCountScore The new awakeningsCountScore value to be set
     */
    public void setAwakeningsCountScore(Short awakeningsCountScore) {
        setFieldValue(2, 0, awakeningsCountScore, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Get deep_sleep_score field
     * Comment: Score that evaluates the amount of deep sleep. If valid: 0 (worst) to 100 (best). If unknown: FIT_UINT8_INVALID.
     *
     * @return deep_sleep_score
     */
    public Short getDeepSleepScore() {
        return getFieldShortValue(3, 0, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Set deep_sleep_score field
     * Comment: Score that evaluates the amount of deep sleep. If valid: 0 (worst) to 100 (best). If unknown: FIT_UINT8_INVALID.
     *
     * @param deepSleepScore The new deepSleepScore value to be set
     */
    public void setDeepSleepScore(Short deepSleepScore) {
        setFieldValue(3, 0, deepSleepScore, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Get sleep_duration_score field
     * Comment: Score that evaluates the quality of sleep based on sleep stages, heart-rate variability and possible awakenings during the night. If valid: 0 (worst) to 100 (best). If unknown: FIT_UINT8_INVALID.
     *
     * @return sleep_duration_score
     */
    public Short getSleepDurationScore() {
        return getFieldShortValue(4, 0, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Set sleep_duration_score field
     * Comment: Score that evaluates the quality of sleep based on sleep stages, heart-rate variability and possible awakenings during the night. If valid: 0 (worst) to 100 (best). If unknown: FIT_UINT8_INVALID.
     *
     * @param sleepDurationScore The new sleepDurationScore value to be set
     */
    public void setSleepDurationScore(Short sleepDurationScore) {
        setFieldValue(4, 0, sleepDurationScore, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Get light_sleep_score field
     * Comment: Score that evaluates the amount of light sleep. If valid: 0 (worst) to 100 (best). If unknown: FIT_UINT8_INVALID.
     *
     * @return light_sleep_score
     */
    public Short getLightSleepScore() {
        return getFieldShortValue(5, 0, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Set light_sleep_score field
     * Comment: Score that evaluates the amount of light sleep. If valid: 0 (worst) to 100 (best). If unknown: FIT_UINT8_INVALID.
     *
     * @param lightSleepScore The new lightSleepScore value to be set
     */
    public void setLightSleepScore(Short lightSleepScore) {
        setFieldValue(5, 0, lightSleepScore, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Get overall_sleep_score field
     * Comment: Total score that summarizes the overall quality of sleep, combining sleep duration and quality. If valid: 0 (worst) to 100 (best). If unknown: FIT_UINT8_INVALID.
     *
     * @return overall_sleep_score
     */
    public Short getOverallSleepScore() {
        return getFieldShortValue(6, 0, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Set overall_sleep_score field
     * Comment: Total score that summarizes the overall quality of sleep, combining sleep duration and quality. If valid: 0 (worst) to 100 (best). If unknown: FIT_UINT8_INVALID.
     *
     * @param overallSleepScore The new overallSleepScore value to be set
     */
    public void setOverallSleepScore(Short overallSleepScore) {
        setFieldValue(6, 0, overallSleepScore, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Get sleep_quality_score field
     * Comment: Score that evaluates the quality of sleep based on sleep stages, heart-rate variability and possible awakenings during the night. If valid: 0 (worst) to 100 (best). If unknown: FIT_UINT8_INVALID.
     *
     * @return sleep_quality_score
     */
    public Short getSleepQualityScore() {
        return getFieldShortValue(7, 0, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Set sleep_quality_score field
     * Comment: Score that evaluates the quality of sleep based on sleep stages, heart-rate variability and possible awakenings during the night. If valid: 0 (worst) to 100 (best). If unknown: FIT_UINT8_INVALID.
     *
     * @param sleepQualityScore The new sleepQualityScore value to be set
     */
    public void setSleepQualityScore(Short sleepQualityScore) {
        setFieldValue(7, 0, sleepQualityScore, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Get sleep_recovery_score field
     * Comment: Score that evaluates stress and recovery during sleep. If valid: 0 (worst) to 100 (best). If unknown: FIT_UINT8_INVALID.
     *
     * @return sleep_recovery_score
     */
    public Short getSleepRecoveryScore() {
        return getFieldShortValue(8, 0, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Set sleep_recovery_score field
     * Comment: Score that evaluates stress and recovery during sleep. If valid: 0 (worst) to 100 (best). If unknown: FIT_UINT8_INVALID.
     *
     * @param sleepRecoveryScore The new sleepRecoveryScore value to be set
     */
    public void setSleepRecoveryScore(Short sleepRecoveryScore) {
        setFieldValue(8, 0, sleepRecoveryScore, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Get rem_sleep_score field
     * Comment: Score that evaluates the amount of REM sleep. If valid: 0 (worst) to 100 (best). If unknown: FIT_UINT8_INVALID.
     *
     * @return rem_sleep_score
     */
    public Short getRemSleepScore() {
        return getFieldShortValue(9, 0, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Set rem_sleep_score field
     * Comment: Score that evaluates the amount of REM sleep. If valid: 0 (worst) to 100 (best). If unknown: FIT_UINT8_INVALID.
     *
     * @param remSleepScore The new remSleepScore value to be set
     */
    public void setRemSleepScore(Short remSleepScore) {
        setFieldValue(9, 0, remSleepScore, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Get sleep_restlessness_score field
     * Comment: Score that evaluates the amount of restlessness during sleep. If valid: 0 (worst) to 100 (best). If unknown: FIT_UINT8_INVALID.
     *
     * @return sleep_restlessness_score
     */
    public Short getSleepRestlessnessScore() {
        return getFieldShortValue(10, 0, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Set sleep_restlessness_score field
     * Comment: Score that evaluates the amount of restlessness during sleep. If valid: 0 (worst) to 100 (best). If unknown: FIT_UINT8_INVALID.
     *
     * @param sleepRestlessnessScore The new sleepRestlessnessScore value to be set
     */
    public void setSleepRestlessnessScore(Short sleepRestlessnessScore) {
        setFieldValue(10, 0, sleepRestlessnessScore, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Get awakenings_count field
     * Comment: The number of awakenings during sleep.
     *
     * @return awakenings_count
     */
    public Short getAwakeningsCount() {
        return getFieldShortValue(11, 0, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Set awakenings_count field
     * Comment: The number of awakenings during sleep.
     *
     * @param awakeningsCount The new awakeningsCount value to be set
     */
    public void setAwakeningsCount(Short awakeningsCount) {
        setFieldValue(11, 0, awakeningsCount, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Get interruptions_score field
     * Comment: Score that evaluates the sleep interruptions. If valid: 0 (worst) to 100 (best). If unknown: FIT_UINT8_INVALID.
     *
     * @return interruptions_score
     */
    public Short getInterruptionsScore() {
        return getFieldShortValue(14, 0, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Set interruptions_score field
     * Comment: Score that evaluates the sleep interruptions. If valid: 0 (worst) to 100 (best). If unknown: FIT_UINT8_INVALID.
     *
     * @param interruptionsScore The new interruptionsScore value to be set
     */
    public void setInterruptionsScore(Short interruptionsScore) {
        setFieldValue(14, 0, interruptionsScore, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Get average_stress_during_sleep field
     * Comment: Excludes stress during awake periods in the sleep window
     *
     * @return average_stress_during_sleep
     */
    public Float getAverageStressDuringSleep() {
        return getFieldFloatValue(15, 0, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Set average_stress_during_sleep field
     * Comment: Excludes stress during awake periods in the sleep window
     *
     * @param averageStressDuringSleep The new averageStressDuringSleep value to be set
     */
    public void setAverageStressDuringSleep(Float averageStressDuringSleep) {
        setFieldValue(15, 0, averageStressDuringSleep, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

}
