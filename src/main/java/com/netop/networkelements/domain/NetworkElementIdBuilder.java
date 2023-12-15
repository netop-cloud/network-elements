package com.netop.networkelements.domain;

import lombok.NonNull;
import lombok.Value;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.netop.networkelements.domain.ElementPartIdentifier.*;

public class NetworkElementIdBuilder {

    private static final String VALUE_SEPARATOR = ":";
    private static final String PROPERTIES_SEPARATOR = ";";

    private static final Map<NetworkElementType, Function<String, NetworkElementId>> NETWORK_ELEMENT_ID_BUILDERS;

    static {
        Map<NetworkElementType, Function<String, NetworkElementId>> builderFunctions = new HashMap<>();
        builderFunctions.put(NetworkElementType.DEVICE, NetworkElementIdBuilder::toDeviceId);
        builderFunctions.put(NetworkElementType.NETWORK_INTERFACE, NetworkElementIdBuilder::toNetworkInterfaceId);
        builderFunctions.put(NetworkElementType.SLA, NetworkElementIdBuilder::toNetworkInterfaceSlaId);
        builderFunctions.put(NetworkElementType.DEVICE_ELEMENT, NetworkElementIdBuilder::toDeviceElementId);
        builderFunctions.put(NetworkElementType.DEVICE_VPN, NetworkElementIdBuilder::toDeviceVpnId);
        builderFunctions.put(NetworkElementType.DEVICE_VPN_NETWORK_INTERFACE, NetworkElementIdBuilder::toDeviceVpnNetworkInterfaceId);
        NETWORK_ELEMENT_ID_BUILDERS = builderFunctions;
    }

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
        var networkElementIdBuilderFunction = NETWORK_ELEMENT_ID_BUILDERS.get(networkElementType);
        if (networkElementIdBuilderFunction == null) {
            throw new IllegalArgumentException("Network element type " + networkElementType + " is not supported");
        }
        return networkElementIdBuilderFunction.apply(networkElementId);
    }

    public static DeviceId toDeviceId(NetworkElementType networkElementType, String networkElementTextId) {
        NetworkElementId networkElementId = toNetworkElementId(networkElementType, networkElementTextId);
        if (networkElementId instanceof DeviceIdAwareId) {
            return ((DeviceIdAwareId) networkElementId).toDeviceId();
        } else {
            throw new IllegalArgumentException("Network element type " + networkElementType + " is not supported");
        }
    }

    public static NetworkInterfaceId toNetworkInterfaceId(NetworkElementType networkElementType, String networkElementTextId) {
        NetworkElementId networkElementId = toNetworkElementId(networkElementType, networkElementTextId);
        if (networkElementId instanceof NetworkInterfaceIdAwareId) {
            return ((NetworkInterfaceIdAwareId) networkElementId).toNetworkInterfaceId();
        } else {
            throw new IllegalArgumentException("Network element type " + networkElementType + " is not supported");
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
