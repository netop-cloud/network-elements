package com.netop.networkelements.domain;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class NetworkInterfaceSlaId implements NetworkElementId {

    @NonNull
    private String deviceSerial;
    @NonNull
    private String interfaceName;
    @NonNull
    private String slaName;

    @Override
    public String getLevelIdentifier() {
        return slaName;
    }

    @Override
    public String toTextId() {
        return NetworkElementIdBuilder.toTextId(this);
    }

    public DeviceId toDeviceId() {
        return new DeviceId(deviceSerial);
    }

    public NetworkInterfaceId toNetworkInterfaceId() {
        return NetworkInterfaceId.builder()
                .deviceSerial(deviceSerial)
                .interfaceName(interfaceName)
                .build();
    }
}
