package edu.ualr.recyclerviewassignment.model;

import androidx.annotation.NonNull;

import java.util.Date;

/**
 * Created by irconde on 2019-10-04.
 */
public class Device extends Item {
    private String name;
    private DeviceType deviceType;
    private Date lastConnection;

    public DeviceStatus getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(DeviceStatus deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    private DeviceStatus deviceStatus;

    public Device(@NonNull String deviceId) {
        super(false);
        this.lastConnection = null;
        this.name = "";
        this.deviceStatus = DeviceStatus.Linked;
        this.deviceType = DeviceType.Unknown;
    }

    public Device(final Device device) {
        super(false);
        name = device.getName();
        deviceStatus = device.getDeviceStatus();
        deviceType = device.getDeviceType();
        Date timeStamp = device.getLastConnection();
        if (timeStamp == null) {
            lastConnection = null;
        } else {
            lastConnection = (Date) timeStamp.clone();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }

    public Date getLastConnection() {
        return this.lastConnection;
    }

    public void setLastConnection(Date timeStamp) {
        this.lastConnection = timeStamp;
    }

    public enum DeviceType {
        Unknown,
        Desktop,
        Laptop,
        Smartphone,
        Tablet,
        SmartTV,
        GameConsole
    }

    public enum DeviceStatus {
        Connected,
        Ready,
        Linked
    }
}
