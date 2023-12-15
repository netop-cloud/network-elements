package com.netop.networkelements.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DeviceElementIdTest {

    @Test
    public void should_create_DeviceElementId() {
        var deviceElementId = DeviceElementId.builder()
                .deviceSerial("ASD123")
                .elementName("xyz")
                .build();

        assertThat(deviceElementId.toTextId()).isEqualTo("deviceSerial:ASD123;deviceElement:xyz");
        assertThat(deviceElementId.getLevelIdentifier()).isEqualTo("xyz");
    }

}