package com.netop.networkelements.domain;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Builder
@Value
public class DeviceVpnId implements DeviceIdAwareId {

    @NonNull
    private String deviceSerial;
    @NonNull
    private String vpnDestinationName;

    @Override
    public String getLevelIdentifier() {
        return vpnDestinationName;
    }

    @Override
    public String toTextId() {
        return NetworkElementIdBuilder.toTextId(this);
    }

    @Override
    public DeviceId toDeviceId() {
        return new DeviceId(deviceSerial);
    }
}
