package com.netop.networkelements.domain;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class NetworkInterfaceId implements NetworkElementId {
    @NonNull
    private String deviceSerial;
    @NonNull
    private String interfaceName;

    @Override
    public String getLevelIdentifier() {
        return interfaceName;
    }

    @Override
    public String toTextId() {
        return NetworkElementIdBuilder.toTextId(this);
    }

    public DeviceId toDeviceId() {
        return new DeviceId(deviceSerial);
    }
}
