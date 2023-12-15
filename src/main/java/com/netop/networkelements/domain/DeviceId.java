package com.netop.networkelements.domain;

import lombok.NonNull;
import lombok.Value;

@Value
public class DeviceId implements DeviceIdAwareId {

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

    @Override
    public DeviceId toDeviceId() {
        return this;
    }
}
