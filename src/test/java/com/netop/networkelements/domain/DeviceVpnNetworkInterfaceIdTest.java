package com.netop.networkelements.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DeviceVpnNetworkInterfaceIdTest {
    @Test
    public void should_create_DeviceVpnNetworkInterfaceId() {
        var deviceVpnNetworkInterfaceId = DeviceVpnNetworkInterfaceId.builder()
                .deviceSerial("ASD123")
                .vpnDestinationName("some destination")
                .interfaceName("wan1")
                .build();

        assertThat(deviceVpnNetworkInterfaceId.toTextId()).isEqualTo("deviceSerial:ASD123;vpnDestinationName:some destination;interfaceName:wan1");
        assertThat(deviceVpnNetworkInterfaceId.getLevelIdentifier()).isEqualTo("wan1");
    }
}