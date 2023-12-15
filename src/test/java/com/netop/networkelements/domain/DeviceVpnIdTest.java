package com.netop.networkelements.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DeviceVpnIdTest {

    @Test
    public void should_create_DeviceVpnId() {
        var deviceVpnId = DeviceVpnId.builder()
                .deviceSerial("ASD123")
                .vpnDestinationName("some destination")
                .build();

        assertThat(deviceVpnId.toTextId()).isEqualTo("deviceSerial:ASD123;vpnDestinationName:some destination");
        assertThat(deviceVpnId.getLevelIdentifier()).isEqualTo("some destination");
    }
}