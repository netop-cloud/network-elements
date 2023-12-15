package com.netop.networkelements.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DeviceIdTest {

    @Test
    public void should_create_DeviceId() {
        var deviceId = new DeviceId("ASD123");

        assertThat(deviceId.toTextId()).isEqualTo("deviceSerial:ASD123");
        assertThat(deviceId.getLevelIdentifier()).isEqualTo("ASD123");
    }
}