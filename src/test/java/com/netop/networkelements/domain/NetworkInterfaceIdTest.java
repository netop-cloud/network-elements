package com.netop.networkelements.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NetworkInterfaceIdTest {

    @Test
    public void should_create_NetworkInterfaceId() {
        var networkInterfaceId = NetworkInterfaceId.builder()
                .deviceSerial("ASD123")
                .interfaceName("wan1")
                .build();

        assertThat(networkInterfaceId.toTextId()).isEqualTo("deviceSerial:ASD123;interfaceName:wan1");
        assertThat(networkInterfaceId.getLevelIdentifier()).isEqualTo("wan1");

        assertThat(networkInterfaceId.toDeviceId()).isEqualTo(new DeviceId("ASD123"));
    }
}