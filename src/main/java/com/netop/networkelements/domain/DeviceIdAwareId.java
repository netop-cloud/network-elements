package com.netop.networkelements.domain;

interface DeviceIdAwareId extends NetworkElementId {

    DeviceId toDeviceId();

}
