package com.netop.networkelements.domain;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.netop.networkelements.domain.NetworkElementIdBuilder.*;
import static com.netop.networkelements.domain.NetworkElementType.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class NetworkElementIdBuilderTest {

    @Test
    public void should_build_text_device_id() {
        // given
        var deviceId = new DeviceId("ASD345");

        // when
        var textId = toTextId(deviceId);

        // then
        assertThat(textId).isEqualTo("deviceSerial:ASD345");
    }

    @Test
    public void should_build_text_network_interface_id() {
        // given
        var networkInterfaceId = NetworkInterfaceId.builder()
                .deviceSerial("ASD345")
                .interfaceName("wan1")
                .build();

        // when
        var textId = toTextId(networkInterfaceId);

        // then
        assertThat(textId).isEqualTo("deviceSerial:ASD345;interfaceName:wan1");
    }

    @Test
    public void should_get_device_id_from_text_id() {
        // given
        var textId = "deviceSerial:ASD345";

        // when
        var deviceId = toDeviceId(textId);

        // then
        assertThat(deviceId.getDeviceSerial()).isEqualTo("ASD345");
    }

    @Test
    public void should_get_network_interface_id_from_text_id() {
        // given
        var textId = "deviceSerial:ASD345;interfaceName:wan1";

        // when
        var networkInterfaceId = toNetworkInterfaceId(textId);

        // then
        assertThat(networkInterfaceId).isEqualTo(NetworkInterfaceId.builder()
                .deviceSerial("ASD345")
                .interfaceName("wan1")
                .build());
    }

    @Test
    public void should_throw_exception_when_text_id_has_moe_parts_than_network_interface_id() {
        // given
        var textId = "deviceSerial:ASD345;interfaceName:wan1;slaName:8.8.8.8";

        // when
        assertThatThrownBy(() -> toNetworkInterfaceId(textId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Cannot extract properties from '" + textId + "'. Found: 3 properties expected: 2");
    }

    @Test
    public void should_throw_exception_when_text_id_has_not_required_key() {
        // given
        var textId = "deviceSerial:ASD345;otherKey:someValue";

        // when
        assertThatThrownBy(() -> toNetworkInterfaceId(textId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Cannot find ElementPartIdentifier for systemIdentifierName: otherKey");
    }

    @Test
    public void should_throw_exception_when_text_id_property_is_not_formatted_correctly() {
        // given
        var textId = "deviceSerial:ASD345;interfaceName:wan1:wan2";

        // when
        assertThatThrownBy(() -> toNetworkInterfaceId(textId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Cannot extract properties from '" + textId + "'");
    }

    @Test
    public void should_build_text_device_id_for_DEVICE_element_type() {
        // given
        var systemPartIdentifiers = Map.of(ElementPartIdentifier.DEVICE_SERIAL, "ASD345");

        // when
        var textId = toTextId(DEVICE, systemPartIdentifiers);

        // then
        assertThat(textId).isEqualTo("deviceSerial:ASD345");
    }

    @Test
    public void should_build_text_network_interface_id_for_NETWORK_INTERFACE_element_type() {
        // given
        var systemPartIdentifiers = Map.of(
                ElementPartIdentifier.DEVICE_SERIAL, "ASD345",
                ElementPartIdentifier.INTERFACE_NAME, "wan1"
        );

        // when
        var textId = toTextId(NetworkElementType.NETWORK_INTERFACE, systemPartIdentifiers);

        // then
        assertThat(textId).isEqualTo("deviceSerial:ASD345;interfaceName:wan1");
    }

    @Test
    public void should_create_network_element_id_from_text_id() {
        assertThat(toNetworkElementId(DEVICE, "deviceSerial:ASD123")).isEqualTo(new DeviceId("ASD123"));

        assertThat(toNetworkElementId(NETWORK_INTERFACE, "deviceSerial:ASD123;interfaceName:wan1")).isEqualTo(
                NetworkInterfaceId.builder().deviceSerial("ASD123").interfaceName("wan1").build());

        assertThat(toNetworkElementId(SLA, "deviceSerial:ASD123;interfaceName:wan1;slaName:8.8.8.8")).isEqualTo(
                NetworkInterfaceSlaId.builder().deviceSerial("ASD123").interfaceName("wan1").slaName("8.8.8.8").build());

        assertThat(toNetworkElementId(DEVICE_ELEMENT, "deviceSerial:ASD123;deviceElement:xyzElement")).isEqualTo(
                DeviceElementId.builder().deviceSerial("ASD123").elementName("xyzElement").build());

        assertThat(toNetworkElementId(DEVICE_VPN, "deviceSerial:ASD123;vpnDestinationName:Some vpn destination")).isEqualTo(
                DeviceVpnId.builder().deviceSerial("ASD123").vpnDestinationName("Some vpn destination").build());

        assertThat(toNetworkElementId(DEVICE_VPN_NETWORK_INTERFACE, "deviceSerial:ASD123;vpnDestinationName:Some vpn destination;interfaceName:wan1")).isEqualTo(
                DeviceVpnNetworkInterfaceId.builder().deviceSerial("ASD123").vpnDestinationName("Some vpn destination").interfaceName("wan1").build());
    }

    @Test
    public void should_get_device_id() {
        var expectedDeviceId = new DeviceId("ASD123");

        assertThat(NetworkElementIdBuilder.toDeviceId(NetworkElementType.DEVICE, "deviceSerial:ASD123")).isEqualTo(expectedDeviceId);
        assertThat(NetworkElementIdBuilder.toDeviceId(NetworkElementType.NETWORK_INTERFACE, "deviceSerial:ASD123;interfaceName:wan1")).isEqualTo(expectedDeviceId);
        assertThat(NetworkElementIdBuilder.toDeviceId(NetworkElementType.SLA, "deviceSerial:ASD123;interfaceName:wan1;slaName:8.8.8.8")).isEqualTo(expectedDeviceId);

        assertThat(NetworkElementIdBuilder.toDeviceId(NetworkElementType.DEVICE_ELEMENT, "deviceSerial:ASD123;deviceElement:XYZ")).isEqualTo(expectedDeviceId);

        assertThat(NetworkElementIdBuilder.toDeviceId(NetworkElementType.DEVICE_VPN, "deviceSerial:ASD123;vpnDestinationName:Some vpn")).isEqualTo(expectedDeviceId);
        assertThat(NetworkElementIdBuilder.toDeviceId(NetworkElementType.DEVICE_VPN_NETWORK_INTERFACE, "deviceSerial:ASD123;vpnDestinationName:Some vpn;interfaceName:wan1")).isEqualTo(expectedDeviceId);
    }

    @Test
    public void should_get_network_interface_id() {
        var expectedNetworkInterfaceId = NetworkInterfaceId.builder()
                .deviceSerial("ASD123")
                .interfaceName("wan1")
                .build();

        assertThat(NetworkElementIdBuilder.toNetworkInterfaceId(NetworkElementType.NETWORK_INTERFACE, "deviceSerial:ASD123;interfaceName:wan1")).isEqualTo(expectedNetworkInterfaceId);
        assertThat(NetworkElementIdBuilder.toNetworkInterfaceId(NetworkElementType.SLA, "deviceSerial:ASD123;interfaceName:wan1;slaName:8.8.8.8")).isEqualTo(expectedNetworkInterfaceId);

        assertThatThrownBy(() -> NetworkElementIdBuilder.toNetworkInterfaceId(DEVICE, "deviceSerial:ASD123"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}