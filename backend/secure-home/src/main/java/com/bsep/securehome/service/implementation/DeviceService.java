package com.bsep.securehome.service.implementation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TimeZone;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import java.util.concurrent.ScheduledFuture;

import org.springframework.beans.BeansException;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import com.bsep.securehome.dto.DeviceDto;
import com.bsep.securehome.dto.SearchDeviceDto;
import com.bsep.securehome.model.DevicesLog;
import com.bsep.securehome.repository.DevicesLogRepository;
import com.bsep.securehome.dto.MessageDto;
import com.bsep.securehome.exception.MissingEntityException;
import com.bsep.securehome.model.RealEstate;
import com.bsep.securehome.model.Task;
import com.bsep.securehome.model.enums.DeviceType;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.util.Base64;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Component
public class DeviceService implements ApplicationContextAware {
    private ApplicationContext applicationContext;
    private String key = "ThisIsA16ByteKey";

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Autowired
    private RealEstateService realEstateService;

    @Autowired
    private TaskScheduler taskScheduler;

    private Map<String, ScheduledFuture<?>> jobsMap = new HashMap<>();

    @Value("${configPath}")
    private String configPath;

    private DevicesLogRepository devicesLogRepository;

    @Autowired
    public DeviceService(DevicesLogRepository devicesLogRepository) {
        this.devicesLogRepository = devicesLogRepository;
    }

    public DeviceDto createDevice(DeviceDto device) throws IOException {
        device.setId(UUID.randomUUID().toString());
        String filePath = "src/main/java/files/config/" + device.getRealEstateId() + ".txt";
        File file = new File(filePath);
        if (file.exists()) {
            Files.write(Paths.get(filePath), device.toString().getBytes(), StandardOpenOption.APPEND);
        } else {
            Files.write(Paths.get(filePath), device.toString().getBytes(), StandardOpenOption.CREATE);
        }
        addTaskForDevice(device);
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
        String filePath = configPath + "src/main/java/files/config/" + realEstateId + ".txt";
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

    public List<DevicesLog> fetchReportForDevices(SearchDeviceDto searchDeviceDto) throws FileNotFoundException {
        List<DevicesLog> devices = devicesLogRepository.findAll();

        if (searchDeviceDto.getStartDateTime() != "") {
            searchDeviceDto.setStartDate(LocalDate.parse(searchDeviceDto.getStartDateTime()));
        } else {
            searchDeviceDto.setStartDate(LocalDate.parse("1900-01-01"));
        }
        if (searchDeviceDto.getEndDateTime() != "") {
            searchDeviceDto.setEndDate(LocalDate.parse(searchDeviceDto.getEndDateTime()));
        } else {
            searchDeviceDto.setEndDate(LocalDate.parse("2100-01-01"));
        }

        List<DevicesLog> findDevices = devices
                .stream().filter(device -> (device.getRealEstateId() == searchDeviceDto.getRealEstateId())
                        && ((device.getType().toString().contains(searchDeviceDto.getType()))
                                && LocalDate.parse(device.getDateTime()).isAfter(searchDeviceDto.getStartDate())
                                && LocalDate.parse(device.getDateTime()).isBefore(searchDeviceDto.getEndDate()))
                        || ((device.getType().toString() == "")
                                && LocalDate.parse(device.getDateTime()).isAfter(searchDeviceDto.getStartDate())
                                && LocalDate.parse(device.getDateTime()).isBefore(searchDeviceDto.getEndDate())))
                .collect(Collectors.toList());

        return findDevices;
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

            }
            scanner.close();

        } else {
            return null;
        }
        return null;
    }

    public boolean checkRegex(MessageDto messageDto) throws FileNotFoundException {
        String regex = findRegex(messageDto.getRealEstateId(), messageDto.getId());
        if (regex != null && messageDto.getMessage().matches(regex)) {
            return true;
        }
        return false;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void createDynamicTasksForDevices() throws FileNotFoundException {
        List<RealEstate> realEstates = realEstateService.findAll();
        realEstates.forEach(realEstate -> {
            try {
                List<DeviceDto> devices = findDevicesForRealEstate(realEstate.getId());
                devices.forEach(device -> {
                    addTaskForDevice(device);
                });
            } catch (FileNotFoundException e) {
                throw new MissingEntityException("Devices for real estate " + realEstate.getId() + " don't exist.");
            }
        });
    }

    private void addTaskForDevice(DeviceDto deviceDto) {
        String seconds = deviceDto.getPeriod() == 0 ? "*" : "0/" + Integer.toString(deviceDto.getPeriod());
        ScheduledFuture<?> scheduledTask = taskScheduler.schedule(
                new Task(deviceDto.getId(), deviceDto.getPeriod(), applicationContext),
                new CronTrigger(seconds + " * * * * ?",
                        TimeZone.getTimeZone(TimeZone.getDefault().getID())));
        jobsMap.put(deviceDto.getId(), scheduledTask);
    }

    public String decrypt(String ciphertext) {
        try {
            byte[] cipherbytes = Base64.getDecoder().decode(ciphertext);

            byte[] initVector = Arrays.copyOfRange(cipherbytes, 0, 16);

            byte[] messagebytes = Arrays.copyOfRange(cipherbytes, 16, cipherbytes.length);

            IvParameterSpec iv = new IvParameterSpec(initVector);
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            byte[] byte_array = cipher.doFinal(messagebytes);

            return new String(byte_array, StandardCharsets.UTF_8);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
