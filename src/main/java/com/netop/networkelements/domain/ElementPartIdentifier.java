package com.netop.networkelements.domain;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum ElementPartIdentifier {
    DEVICE_SERIAL("deviceSerial"),
    INTERFACE_NAME("interfaceName"),
    DEVICE_ELEMENT("deviceElement"),
    VPN_DESTINATION_NAME("vpnDestinationName"),
    SLA_NAME("slaName");

    private final String systemIdentifierName;

    private static final Map<String, ElementPartIdentifier> SYSTEM_IDENTIFIER_NAME_TO_ELEMENT_PART_IDENTIFIER = Arrays.stream(ElementPartIdentifier.values())
            .collect(Collectors.toMap(ElementPartIdentifier::getSystemIdentifierName, Function.identity()));

    ElementPartIdentifier(String systemIdentifierName) {
        this.systemIdentifierName = systemIdentifierName;
    }

    public static ElementPartIdentifier fromSystemIdentifierName(String systemIdentifierName) {
        var elementPartIdentifier = SYSTEM_IDENTIFIER_NAME_TO_ELEMENT_PART_IDENTIFIER.get(systemIdentifierName);
        if (elementPartIdentifier == null) {
            throw new IllegalArgumentException("Cannot find ElementPartIdentifier for systemIdentifierName: " + systemIdentifierName);
        }

        return elementPartIdentifier;
    }

    public String getSystemIdentifierName() {
        return systemIdentifierName;
    }

}
