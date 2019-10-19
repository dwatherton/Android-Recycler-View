package edu.ualr.recyclerviewassignment.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import edu.ualr.recyclerviewassignment.model.Device;
import edu.ualr.recyclerviewassignment.model.Item;
import edu.ualr.recyclerviewassignment.model.SectionHeader;

/**
 * Created by irconde on 2019-10-04.
 */
public class DataGenerator {

    private static Random r = new Random();

    public static int randInt(int max) {
        int min = 0;
        return r.nextInt((max - min) + 1) + min;
    }

    public static List<Item> getDevicesDataset(int datasetSize) {
        Device device;
        SectionHeader sectionHeader;
        SectionHeader.Header section;
        String id;
        Device.DeviceStatus status;
        Device.DeviceType type;
        List<Item> items = new ArrayList<>();
        for (int i = 0; i < datasetSize; i++) {
            type = Device.DeviceType.values()[randInt(6)];
            id = type.toString() + "-" + String.valueOf(i);
            status = Device.DeviceStatus.values()[randInt(2)];
            device = new Device(id);
            device.setDeviceStatus(status);
            device.setName(id);
            device.setDeviceType(type);
            items.add(device);
        }
        for (int i = 0; i < 3; i++)
        {
            section = SectionHeader.Header.values()[i];
            sectionHeader = new SectionHeader(section);
            items.add(sectionHeader);
        }
        Collections.shuffle(items);
        return items;
    }
}
