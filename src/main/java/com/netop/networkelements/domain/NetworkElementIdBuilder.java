package com.netop.networkelements.domain;

import lombok.NonNull;
import lombok.Value;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.netop.networkelements.domain.ElementPartIdentifier.*;


public class NetworkElementIdBuilder {

    private static final String VALUE_SEPARATOR = ":";
    private static final String PROPERTIES_SEPARATOR = ";";

    public static DeviceId toDeviceId(String textId) {
        Map<ElementPartIdentifier, String> properties = extractProperties(textId, 1);
        return new DeviceId(getPropertyValue(properties, DEVICE_SERIAL));
    }

    public static String toTextId(NetworkElementType networkElementType, Map<ElementPartIdentifier, String> systemPartIdentifiers) {
        return toTextId(networkElementType.getElementPartIdentifiers().stream()
                .map(it -> new IdProperty(it, systemPartIdentifiers.get(it)))
                .collect(Collectors.toList()));
    }

    public static NetworkInterfaceId toNetworkInterfaceId(String textId) {
        Map<ElementPartIdentifier, String> properties = extractProperties(textId, 2);

        return NetworkInterfaceId.builder()
                .deviceSerial(getPropertyValue(properties, DEVICE_SERIAL))
                .interfaceName(getPropertyValue(properties, INTERFACE_NAME))
                .build();
    }

    public static NetworkInterfaceSlaId toNetworkInterfaceSlaId(String textId) {
        Map<ElementPartIdentifier, String> properties = extractProperties(textId, 3);

        return NetworkInterfaceSlaId.builder()
                .deviceSerial(getPropertyValue(properties, DEVICE_SERIAL))
                .interfaceName(getPropertyValue(properties, INTERFACE_NAME))
                .slaName(getPropertyValue(properties, SLA_NAME))
                .build();
    }

    public static DeviceElementId toDeviceElementId(String textId) {
        Map<ElementPartIdentifier, String> properties = extractProperties(textId, 2);

        return DeviceElementId.builder()
                .deviceSerial(getPropertyValue(properties, DEVICE_SERIAL))
                .elementName(getPropertyValue(properties, DEVICE_ELEMENT))
                .build();
    }

    public static DeviceVpnId toDeviceVpnId(String textId) {
        Map<ElementPartIdentifier, String> properties = extractProperties(textId, 2);

        return DeviceVpnId.builder()
                .deviceSerial(getPropertyValue(properties, DEVICE_SERIAL))
                .vpnDestinationName(getPropertyValue(properties, VPN_DESTINATION_NAME))
                .build();
    }

    public static DeviceVpnNetworkInterfaceId toDeviceVpnNetworkInterfaceId(String textId) {
        Map<ElementPartIdentifier, String> properties = extractProperties(textId, 3);

        return DeviceVpnNetworkInterfaceId.builder()
                .deviceSerial(getPropertyValue(properties, DEVICE_SERIAL))
                .vpnDestinationName(getPropertyValue(properties, VPN_DESTINATION_NAME))
                .interfaceName(getPropertyValue(properties, INTERFACE_NAME))
                .build();
    }

    static String toTextId(DeviceId deviceId) {
        return toTextId(List.of(
                new IdProperty(DEVICE_SERIAL, deviceId.getDeviceSerial())));
    }

    public static String toTextId(NetworkInterfaceId networkInterfaceId) {
        return toTextId(List.of(
                new IdProperty(DEVICE_SERIAL, networkInterfaceId.getDeviceSerial()),
                new IdProperty(INTERFACE_NAME, networkInterfaceId.getInterfaceName())));
    }

    static String toTextId(NetworkInterfaceSlaId networkInterfaceSlaId) {
        return toTextId(List.of(
                new IdProperty(DEVICE_SERIAL, networkInterfaceSlaId.getDeviceSerial()),
                new IdProperty(INTERFACE_NAME, networkInterfaceSlaId.getInterfaceName()),
                new IdProperty(SLA_NAME, networkInterfaceSlaId.getSlaName())
        ));
    }

    static String toTextId(DeviceVpnId deviceVpnId) {
        return toTextId(List.of(
                new IdProperty(DEVICE_SERIAL, deviceVpnId.getDeviceSerial()),
                new IdProperty(VPN_DESTINATION_NAME, deviceVpnId.getVpnDestinationName())
        ));
    }

    static String toTextId(DeviceVpnNetworkInterfaceId deviceVpnNetworkInterfaceId) {
        return toTextId(List.of(
                new IdProperty(DEVICE_SERIAL, deviceVpnNetworkInterfaceId.getDeviceSerial()),
                new IdProperty(VPN_DESTINATION_NAME, deviceVpnNetworkInterfaceId.getVpnDestinationName()),
                new IdProperty(INTERFACE_NAME, deviceVpnNetworkInterfaceId.getInterfaceName())
        ));
    }

    static String toTextId(DeviceElementId deviceElementId) {
        return toTextId(List.of(
                new IdProperty(DEVICE_SERIAL, deviceElementId.getDeviceSerial()),
                new IdProperty(DEVICE_ELEMENT, deviceElementId.getElementName())
        ));
    }


    public static String addPropertiesSeparator(String textId) {
        return textId + PROPERTIES_SEPARATOR;
    }

    private static String getPropertyValue(Map<ElementPartIdentifier, String> properties, ElementPartIdentifier key) {
        String value = properties.get(key);
        if (value == null) {
            throw new IllegalArgumentException("No value for key '" + key + "'");
        }
        return value;
    }

    private static Map<ElementPartIdentifier, String> extractProperties(String textId, int expectedSize) {
        Map<ElementPartIdentifier, String> properties = new HashMap<>();
        String[] stringProperties = textId.split(PROPERTIES_SEPARATOR);
        for (String stringProperty : stringProperties) {
            String[] propertyParts = stringProperty.split(VALUE_SEPARATOR);
            if (propertyParts.length != 2) {
                throw new IllegalArgumentException("Cannot extract properties from '" + textId + "'");
            }
            properties.put(ElementPartIdentifier.fromSystemIdentifierName(propertyParts[0]), propertyParts[1]);
        }

        if (properties.size() != expectedSize) {
            throw new IllegalArgumentException("Cannot extract properties from '" + textId + "'. Found: " + properties.size() + " properties expected: " + expectedSize);
        }
        return properties;
    }

    private static String toTextId(List<IdProperty> idProperties) {
        return idProperties.stream()
                .map(idProperty -> idProperty.getKey().getSystemIdentifierName() + VALUE_SEPARATOR + idProperty.getValue())
                .collect(Collectors.joining(PROPERTIES_SEPARATOR));
    }

    public static NetworkElementId toNetworkElementId(NetworkElementType networkElementType, String networkElementId) {
        switch (networkElementType) {
            case DEVICE:
                return toDeviceId(networkElementId);
            case NETWORK_INTERFACE:
                return toNetworkInterfaceId(networkElementId);
            case SLA:
                return toNetworkInterfaceSlaId(networkElementId);
            case DEVICE_ELEMENT:
                return toDeviceElementId(networkElementId);
            case DEVICE_VPN:
                return toDeviceVpnId(networkElementId);
            case DEVICE_VPN_NETWORK_INTERFACE:
                return toDeviceVpnNetworkInterfaceId(networkElementId);
            default:
                throw new IllegalArgumentException("Type " + networkElementType + " is not supported");
        }
    }

    @Value
    private static class IdProperty {

        @NonNull
        private ElementPartIdentifier key;
        @NonNull
        private String value;
    }

}
