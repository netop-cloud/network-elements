package com.netop.networkelements.domain;

interface NetworkInterfaceIdAwareId extends DeviceIdAwareId {

    NetworkInterfaceId toNetworkInterfaceId();

}
