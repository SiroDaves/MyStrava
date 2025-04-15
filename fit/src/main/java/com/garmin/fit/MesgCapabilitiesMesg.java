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



public class MesgCapabilitiesMesg extends Mesg   {

    
    public static final int MessageIndexFieldNum = 254;
    
    public static final int FileFieldNum = 0;
    
    public static final int MesgNumFieldNum = 1;
    
    public static final int CountTypeFieldNum = 2;
    
    public static final int CountFieldNum = 3;
    

    protected static final  Mesg mesgCapabilitiesMesg;
    static {
        int field_index = 0;
        int subfield_index = 0;
        // mesg_capabilities
        mesgCapabilitiesMesg = new Mesg("mesg_capabilities", MesgNum.MESG_CAPABILITIES);
        mesgCapabilitiesMesg.addField(new Field("message_index", MessageIndexFieldNum, 132, 1, 0, "", false, Profile.Type.MESSAGE_INDEX));
        field_index++;
        mesgCapabilitiesMesg.addField(new Field("file", FileFieldNum, 0, 1, 0, "", false, Profile.Type.FILE));
        field_index++;
        mesgCapabilitiesMesg.addField(new Field("mesg_num", MesgNumFieldNum, 132, 1, 0, "", false, Profile.Type.MESG_NUM));
        field_index++;
        mesgCapabilitiesMesg.addField(new Field("count_type", CountTypeFieldNum, 0, 1, 0, "", false, Profile.Type.MESG_COUNT));
        field_index++;
        mesgCapabilitiesMesg.addField(new Field("count", CountFieldNum, 132, 1, 0, "", false, Profile.Type.UINT16));
        subfield_index = 0;
        mesgCapabilitiesMesg.fields.get(field_index).subFields.add(new SubField("num_per_file", 132, 1, 0, ""));
        mesgCapabilitiesMesg.fields.get(field_index).subFields.get(subfield_index).addMap(2, 0);
        subfield_index++;
        mesgCapabilitiesMesg.fields.get(field_index).subFields.add(new SubField("max_per_file", 132, 1, 0, ""));
        mesgCapabilitiesMesg.fields.get(field_index).subFields.get(subfield_index).addMap(2, 1);
        subfield_index++;
        mesgCapabilitiesMesg.fields.get(field_index).subFields.add(new SubField("max_per_file_type", 132, 1, 0, ""));
        mesgCapabilitiesMesg.fields.get(field_index).subFields.get(subfield_index).addMap(2, 2);
        subfield_index++;
        field_index++;
    }

    public MesgCapabilitiesMesg() {
        super(Factory.createMesg(MesgNum.MESG_CAPABILITIES));
    }

    public MesgCapabilitiesMesg(final Mesg mesg) {
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
     * Get file field
     *
     * @return file
     */
    public File getFile() {
        Short value = getFieldShortValue(0, 0, Fit.SUBFIELD_INDEX_MAIN_FIELD);
        if (value == null) {
            return null;
        }
        return File.getByValue(value);
    }

    /**
     * Set file field
     *
     * @param file The new file value to be set
     */
    public void setFile(File file) {
        setFieldValue(0, 0, file.value, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Get mesg_num field
     *
     * @return mesg_num
     */
    public Integer getMesgNum() {
        return getFieldIntegerValue(1, 0, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Set mesg_num field
     *
     * @param mesgNum The new mesgNum value to be set
     */
    public void setMesgNum(Integer mesgNum) {
        setFieldValue(1, 0, mesgNum, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Get count_type field
     *
     * @return count_type
     */
    public MesgCount getCountType() {
        Short value = getFieldShortValue(2, 0, Fit.SUBFIELD_INDEX_MAIN_FIELD);
        if (value == null) {
            return null;
        }
        return MesgCount.getByValue(value);
    }

    /**
     * Set count_type field
     *
     * @param countType The new countType value to be set
     */
    public void setCountType(MesgCount countType) {
        setFieldValue(2, 0, countType.value, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Get count field
     *
     * @return count
     */
    public Integer getCount() {
        return getFieldIntegerValue(3, 0, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Set count field
     *
     * @param count The new count value to be set
     */
    public void setCount(Integer count) {
        setFieldValue(3, 0, count, Fit.SUBFIELD_INDEX_MAIN_FIELD);
    }

    /**
     * Get num_per_file field
     *
     * @return num_per_file
     */
    public Integer getNumPerFile() {
        return getFieldIntegerValue(3, 0, Profile.SubFields.MESG_CAPABILITIES_MESG_COUNT_FIELD_NUM_PER_FILE);
    }

    /**
     * Set num_per_file field
     *
     * @param numPerFile The new numPerFile value to be set
     */
    public void setNumPerFile(Integer numPerFile) {
        setFieldValue(3, 0, numPerFile, Profile.SubFields.MESG_CAPABILITIES_MESG_COUNT_FIELD_NUM_PER_FILE);
    }

    /**
     * Get max_per_file field
     *
     * @return max_per_file
     */
    public Integer getMaxPerFile() {
        return getFieldIntegerValue(3, 0, Profile.SubFields.MESG_CAPABILITIES_MESG_COUNT_FIELD_MAX_PER_FILE);
    }

    /**
     * Set max_per_file field
     *
     * @param maxPerFile The new maxPerFile value to be set
     */
    public void setMaxPerFile(Integer maxPerFile) {
        setFieldValue(3, 0, maxPerFile, Profile.SubFields.MESG_CAPABILITIES_MESG_COUNT_FIELD_MAX_PER_FILE);
    }

    /**
     * Get max_per_file_type field
     *
     * @return max_per_file_type
     */
    public Integer getMaxPerFileType() {
        return getFieldIntegerValue(3, 0, Profile.SubFields.MESG_CAPABILITIES_MESG_COUNT_FIELD_MAX_PER_FILE_TYPE);
    }

    /**
     * Set max_per_file_type field
     *
     * @param maxPerFileType The new maxPerFileType value to be set
     */
    public void setMaxPerFileType(Integer maxPerFileType) {
        setFieldValue(3, 0, maxPerFileType, Profile.SubFields.MESG_CAPABILITIES_MESG_COUNT_FIELD_MAX_PER_FILE_TYPE);
    }

}
