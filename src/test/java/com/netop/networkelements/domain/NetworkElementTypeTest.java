package com.netop.networkelements.domain;

import org.junit.jupiter.api.Test;

import java.util.List;

import static com.netop.networkelements.domain.ElementPartIdentifier.*;
import static org.assertj.core.api.Assertions.assertThat;

class NetworkElementTypeTest {

    @Test
    public void should_get_identifiers() {
        assertThat(NetworkElementType.DEVICE.getElementPartIdentifiers()).isEqualTo(List.of(DEVICE_SERIAL));
        assertThat(NetworkElementType.NETWORK_INTERFACE.getElementPartIdentifiers()).isEqualTo(List.of(DEVICE_SERIAL, INTERFACE_NAME));
        assertThat(NetworkElementType.SLA.getElementPartIdentifiers()).isEqualTo(List.of(DEVICE_SERIAL, INTERFACE_NAME, SLA_NAME));

        assertThat(NetworkElementType.DEVICE_ELEMENT.getElementPartIdentifiers()).isEqualTo(List.of(DEVICE_SERIAL, DEVICE_ELEMENT));

        assertThat(NetworkElementType.DEVICE_VPN.getElementPartIdentifiers()).isEqualTo(List.of(DEVICE_SERIAL, VPN_DESTINATION_NAME));
        assertThat(NetworkElementType.DEVICE_VPN_NETWORK_INTERFACE.getElementPartIdentifiers()).isEqualTo(List.of(DEVICE_SERIAL, VPN_DESTINATION_NAME, INTERFACE_NAME));
    }

}