package com.netop.networkelements.domain;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Builder
@Value
public class DeviceElementId implements NetworkElementId {

    @NonNull
    private String deviceSerial;
    @NonNull
    private String elementName;

    @Override
    public String getLevelIdentifier() {
        return elementName;
    }

    @Override
    public String toTextId() {
        return NetworkElementIdBuilder.toTextId(this);
    }
}
