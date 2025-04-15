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

import java.util.zip.Checksum;

public class CRC16 implements Checksum {

    private static final int[] crc16_table = { 0x0000, 0xCC01, 0xD801, 0x1400, 0xF001, 0x3C00, 0x2800, 0xE401, 0xA001, 0x6C00, 0x7800, 0xB401, 0x5000, 0x9C01, 0x8801, 0x4400 };

    private int crc;

    public CRC16() {
        reset();
    }

    public long getValue() {
        return crc;
    }

    public void reset() {
        crc = 0;
    }

    public void update(int b) {
        int tmp;
        // compute checksum of lower four bits of byte
        tmp = crc16_table[crc & 0x0F];
        crc = (crc >> 4) & 0x0FFF;
        crc = crc ^ tmp ^ crc16_table[b & 0x0F];

        // now compute checksum of upper four bits of byte
        tmp = crc16_table[crc & 0x0F];
        crc = (crc >> 4) & 0x0FFF;
        crc = crc ^ tmp ^ crc16_table[(b >> 4) & 0x0F];
    }

    public void update(byte[] bytes, int off, int len) {
        for (; off < len; off++) {
            update(bytes[off]);
        }
    }

}
