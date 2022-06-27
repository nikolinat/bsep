package com.bsep.securehome.model;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import java.io.File;
import java.io.FileNotFoundException;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.bsep.securehome.dto.AlarmDto;
import com.bsep.securehome.dto.MessageDto;
import com.bsep.securehome.exception.BadLogicException;
import com.bsep.securehome.model.enums.DeviceType;
import com.bsep.securehome.service.implementation.AlarmService;
import com.bsep.securehome.service.implementation.DeviceMessageService;
import com.bsep.securehome.service.implementation.DeviceService;

public class Task implements Runnable {
    private AlarmService alarmService;

    private DeviceMessageService deviceMessageService;

    private DeviceService deviceService;

    private String deviceId;

    private Integer period;

    public Task() {

    }

    public Task(String deviceId, Integer period, ApplicationContext applicationContext) {
        this.deviceId = deviceId;
        this.period = period;
        this.alarmService = applicationContext.getBean(AlarmService.class);
        this.deviceMessageService = applicationContext.getBean(DeviceMessageService.class);
        this.deviceService = applicationContext.getBean(DeviceService.class);
    }

    @Override
    public void run() {
        try {
            readMessagesFromFile();
        } catch (FileNotFoundException e) {
            throw new BadLogicException("Error with device messages.");
        }
    }

    private List<MessageDto> readMessagesFromFile() throws FileNotFoundException {
        LocalDateTime now = LocalDateTime.now();
        List<MessageDto> messageDtos = new ArrayList<>();
        String filePath = "src/main/java/files/messages/messages.txt";
        File file = new File(filePath);
        if (file.exists()) {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                MessageDto messageDto = mapMessage(scanner.nextLine());
                messageDto.setDateTime(messageDto.getDateTime().plusHours(2));
                if (deviceService.checkRegex(messageDto)
                        && now.minusSeconds(this.period).isBefore(messageDto.getDateTime())
                        && messageDto.getId().equals(this.deviceId)) {
                    List<AlarmDto> alarms = alarmService.findAlarmsForDevice(messageDto.getRealEstateId(),
                            messageDto.getType());
                    deviceMessageService.create(new DeviceMessage(UUID.randomUUID(),
                            messageDto.getId(), messageDto.getType(),
                            messageDto.getMessage(), LocalDateTime.now(ZoneOffset.UTC), false,
                            messageDto.getValue()), alarms,
                            messageDto.getRealEstateId());
                }
            }
            scanner.close();
        } else {
            return null;
        }
        return messageDtos;
    }

    public MessageDto mapMessage(String message) {
        String[] splitted = message.split(",");
        MessageDto messageDto = new MessageDto();
        messageDto.setDateTime(LocalDateTime.parse(splitted[0]));
        messageDto.setId(splitted[1]);
        messageDto.setMessage(splitted[2]);
        messageDto.setRealEstateId(Long.parseLong(splitted[3]));
        messageDto.setType(DeviceType.valueOf(splitted[4]));
        messageDto.setValue(Integer.parseInt(splitted[5]));
        return messageDto;
    }
}
