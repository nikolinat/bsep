package com.bsep.securehome.service.implementation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.bsep.securehome.dto.DeviceDto;
import com.bsep.securehome.dto.MessageDto;
import com.bsep.securehome.model.enums.DeviceType;

@Component
public class DeviceService {

    public DeviceDto createDevice(DeviceDto device) throws IOException {
        device.setId(UUID.randomUUID().toString());
        String filePath = "src/main/java/files/config/" + device.getRealEstateId() + ".txt";
        File file = new File(filePath);
        if (file.exists()) {
            Files.write(Paths.get(filePath), device.toString().getBytes(), StandardOpenOption.APPEND);
        } else {
            Files.write(Paths.get(filePath), device.toString().getBytes(), StandardOpenOption.CREATE);
        }
        return device;
    }

    public DeviceDto mapDevice(String device) {
        String[] splitted = device.split(",");
        DeviceDto deviceDto = new DeviceDto();
        deviceDto.setId(splitted[0]);
        deviceDto.setRealEstateId(Long.parseLong(splitted[1]));
        deviceDto.setName(splitted[2]);
        deviceDto.setType(DeviceType.valueOf(splitted[3]));
        deviceDto.setPeriod(Integer.parseInt(splitted[4]));
        deviceDto.setFilter(splitted[5]);
        return deviceDto;
    }

    public ArrayList<DeviceDto> findDevicesForRealEstate(Long realEstateId) throws FileNotFoundException {
        ArrayList<DeviceDto> devices = new ArrayList<>();
        String filePath = "src/main/java/files/config/" + realEstateId + ".txt";
        File file = new File(filePath);
        if (file.exists()) {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                DeviceDto device = mapDevice(scanner.nextLine());
                devices.add(device);
            }
            scanner.close();

        } else {
            return null;
        }
        return devices;

    }

    public String findRegex(Long realEstateId, String deviceId) throws FileNotFoundException {
        String filePath = "src/main/java/files/config/" + realEstateId + ".txt";
        File file = new File(filePath);
        if (file.exists()) {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] splitted = line.split(",");
                if (splitted[0].equals(deviceId)) {
                    return splitted[5];
                }
                scanner.close();
            }

        } else {
            return null;
        }
        return null;
    }

    public boolean checkRegex(MessageDto messageDto) throws FileNotFoundException {
        String regex = findRegex(messageDto.getRealEstateId(), messageDto.getId());
        if (messageDto.getMessage().matches(regex)) {
            return true;
        }
        return false;

    }
}
