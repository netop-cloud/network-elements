package com.netop.networkelements.domain;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public enum NetworkElementType {
    DEVICE(null, ElementPartIdentifier.DEVICE_SERIAL),

    NETWORK_INTERFACE(DEVICE, ElementPartIdentifier.INTERFACE_NAME),
    SLA(NETWORK_INTERFACE, ElementPartIdentifier.SLA_NAME),

    DEVICE_ELEMENT(DEVICE, ElementPartIdentifier.DEVICE_ELEMENT),

    DEVICE_VPN(DEVICE, ElementPartIdentifier.VPN_DESTINATION_NAME),
    DEVICE_VPN_NETWORK_INTERFACE(DEVICE_VPN, ElementPartIdentifier.INTERFACE_NAME);

    private final NetworkElementType parent;
    private final ElementPartIdentifier elementSystemIdentifier;

    NetworkElementType(NetworkElementType parent, ElementPartIdentifier ElementPartIdentifier) {
        this.parent = parent;
        this.elementSystemIdentifier = ElementPartIdentifier;
    }

    public List<ElementPartIdentifier> getElementPartIdentifiers() {
        List<ElementPartIdentifier> identifiers = new ArrayList<>();
        if (parent != null) {
            identifiers.addAll(parent.getElementPartIdentifiers());
        }
        identifiers.add(this.elementSystemIdentifier);
        return identifiers;
    }

    public boolean isRoot() {
        return parent == null;
    }

}
