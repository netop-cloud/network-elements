package com.netop.networkelements.domain;

import lombok.NonNull;
import lombok.Value;

@Value
public class DeviceId implements NetworkElementId {

    @NonNull
    private String deviceSerial;

    @Override
    public String getLevelIdentifier() {
        return deviceSerial;
    }

    @Override
    public String toTextId() {
        return NetworkElementIdBuilder.toTextId(this);
    }
}
