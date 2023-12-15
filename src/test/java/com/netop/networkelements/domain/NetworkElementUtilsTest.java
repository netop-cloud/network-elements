package com.netop.networkelements.domain;

import org.junit.jupiter.api.Test;

import static com.netop.networkelements.domain.NetworkElementUtils.extractDeviceSerial;
import static org.assertj.core.api.Assertions.assertThat;

class NetworkElementUtilsTest {

    @Test
    public void should_extract_device_serial() {
        var expectedDeviceSerial = "ASD123";

        assertThat(extractDeviceSerial(NetworkElementType.DEVICE, "deviceSerial:ASD123")).isEqualTo(expectedDeviceSerial);
        assertThat(extractDeviceSerial(NetworkElementType.NETWORK_INTERFACE, "deviceSerial:ASD123;interfaceName:wan1")).isEqualTo(expectedDeviceSerial);
        assertThat(extractDeviceSerial(NetworkElementType.SLA, "deviceSerial:ASD123;interfaceName:wan1;slaName:8.8.8.8")).isEqualTo(expectedDeviceSerial);

        assertThat(extractDeviceSerial(NetworkElementType.DEVICE_ELEMENT, "deviceSerial:ASD123;deviceElement:XYZ")).isEqualTo(expectedDeviceSerial);

        assertThat(extractDeviceSerial(NetworkElementType.DEVICE_VPN, "deviceSerial:ASD123;vpnDestinationName:Some vpn")).isEqualTo(expectedDeviceSerial);
        assertThat(extractDeviceSerial(NetworkElementType.DEVICE_VPN_NETWORK_INTERFACE, "deviceSerial:ASD123;vpnDestinationName:Some vpn;interfaceName:wan1")).isEqualTo(expectedDeviceSerial);
    }


}