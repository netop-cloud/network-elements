package com.netop.networkelements.domain;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Builder
@Value
public class DeviceVpnNetworkInterfaceId implements NetworkElementId {

    @NonNull
    private String deviceSerial;
    @NonNull
    private String vpnDestinationName;
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
}
