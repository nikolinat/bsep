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

import com.bsep.securehome.dto.AlarmDto;
import com.bsep.securehome.model.enums.DeviceType;

@Component
public class AlarmService {

    public AlarmDto createAlarm(AlarmDto alarmDto) throws IOException {
        alarmDto.setId(UUID.randomUUID().toString());
        String filePath = "src/main/java/files/alarms/alarm" + alarmDto.getRealEstateId() + ".txt";
        File file = new File(filePath);
        if (file.exists()) {
            Files.write(Paths.get(filePath), alarmDto.toString().getBytes(), StandardOpenOption.APPEND);
        } else {
            Files.write(Paths.get(filePath), alarmDto.toString().getBytes(), StandardOpenOption.CREATE);
        }
        return alarmDto;
    }

    public AlarmDto mapAlarm(String alarm, DeviceType type) {
        String[] splitted = alarm.split(",");
        if (DeviceType.valueOf(splitted[2]).equals(type)) {
            AlarmDto alarmDto = new AlarmDto();
            alarmDto.setId(splitted[0]);
            alarmDto.setRealEstateId(Long.parseLong(splitted[1]));
            alarmDto.setType(DeviceType.valueOf(splitted[2]));
            alarmDto.setAlarmMessage(splitted[3]);
            return alarmDto;
        }
        return null;
    }

    public ArrayList<AlarmDto> findAlarmsForDevice(Long realEstateId, DeviceType type) throws FileNotFoundException {
        ArrayList<AlarmDto> alarms = new ArrayList<>();
        String filePath = "src/main/java/files/alarms/alarm" + realEstateId + ".txt";
        File file = new File(filePath);
        if (file.exists()) {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                AlarmDto alarmDto = mapAlarm(scanner.nextLine(), type);
                if (alarmDto != null) {
                    alarms.add(alarmDto);
                }
            }
            scanner.close();

        } else {
            return alarms;
        }
        return alarms;

    }

    public AlarmDto mapAlarm(String alarm) {
        String[] splitted = alarm.split(",");
        AlarmDto alarmDto = new AlarmDto();
        alarmDto.setId(splitted[0]);
        alarmDto.setRealEstateId(Long.parseLong(splitted[1]));
        alarmDto.setType(DeviceType.valueOf(splitted[2]));
        alarmDto.setAlarmMessage(splitted[3]);
        return alarmDto;
    }

    public ArrayList<AlarmDto> findAlarmsForRealEstate(Long realEstateId) throws FileNotFoundException {
        ArrayList<AlarmDto> alarms = new ArrayList<>();
        String filePath = "src/main/java/files/alarms/alarm" + realEstateId + ".txt";
        File file = new File(filePath);
        if (file.exists()) {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                AlarmDto alarmDto = mapAlarm(scanner.nextLine());
                alarms.add(alarmDto);
            }
            scanner.close();

        } else {
            return alarms;
        }
        return alarms;

    }

}
