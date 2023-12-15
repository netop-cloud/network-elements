package com.netop.networkelements.domain;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class NetworkInterfaceId implements NetworkInterfaceIdAwareId {

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

    @Override
    public DeviceId toDeviceId() {
        return new DeviceId(deviceSerial);
    }

    @Override
    public NetworkInterfaceId toNetworkInterfaceId() {
        return this;
    }

}
