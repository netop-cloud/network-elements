package com.netop.networkelements.domain;

import static com.netop.networkelements.domain.NetworkElementIdBuilder.toDeviceId;

public class NetworkElementUtils {

    public static String extractDeviceSerial(NetworkElementType networkElementType, String networkElementId) {
        return toDeviceId(networkElementType, networkElementId).getDeviceSerial();
    }

}
