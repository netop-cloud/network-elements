package com.netop.networkelements.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NetworkInterfaceSlaIdTest {

    @Test
    public void should_create_NetworkInterfaceSlaId() {
        var networkInterfaceSlaId = NetworkInterfaceSlaId.builder()
                .deviceSerial("ASD123")
                .interfaceName("wan1")
                .slaName("8.8.8.8")
                .build();

        assertThat(networkInterfaceSlaId.toTextId()).isEqualTo("deviceSerial:ASD123;interfaceName:wan1;slaName:8.8.8.8");
        assertThat(networkInterfaceSlaId.getLevelIdentifier()).isEqualTo("8.8.8.8");

        assertThat(networkInterfaceSlaId.toDeviceId()).isEqualTo(new DeviceId("ASD123"));
        assertThat(networkInterfaceSlaId.toNetworkInterfaceId()).isEqualTo(NetworkInterfaceId.builder()
                .deviceSerial("ASD123")
                .interfaceName("wan1")
                .build());
    }
}