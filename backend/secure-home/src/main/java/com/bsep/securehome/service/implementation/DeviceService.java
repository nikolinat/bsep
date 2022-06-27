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
import java.util.Scanner;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.bsep.securehome.dto.DeviceDto;
import com.bsep.securehome.dto.SearchDeviceDto;
import com.bsep.securehome.model.DevicesLog;
import com.bsep.securehome.repository.DevicesLogRepository;
import com.bsep.securehome.utils.DeviceType;

@Component
public class DeviceService {

    @Value("${configPath}")
    private String configPath;

    private DevicesLogRepository devicesLogRepository;

    @Autowired
    public DeviceService(DevicesLogRepository devicesLogRepository) {
        this.devicesLogRepository = devicesLogRepository;
    }

    
    public DeviceDto createDevice(DeviceDto device) throws IOException{
        device.setId(UUID.randomUUID().toString());
        String filePath = "src/main/java/files/config/"+device.getRealEstateId() + ".txt";
        File file = new File(filePath);
        if(file.exists()){
            Files.write(Paths.get(filePath), device.toString().getBytes(), StandardOpenOption.APPEND);
        }else {
            Files.write(Paths.get(filePath), device.toString().getBytes(), StandardOpenOption.CREATE);
        }
        return device;
    }


    public DeviceDto mapDevice(String device){
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


    public ArrayList<DeviceDto> findDevicesForRealEstate(Long realEstateId) throws FileNotFoundException{
        ArrayList<DeviceDto> devices = new ArrayList<>();
        String filePath = configPath+"src/main/java/files/config/"+realEstateId + ".txt";
        File file = new File(filePath);
        if(file.exists()){
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
               DeviceDto device = mapDevice(scanner.nextLine());
               devices.add(device);
              }
              scanner.close();

        }else {
           return null;
        }
        return devices;

    }

    public List<DevicesLog> fetchReportForDevices(SearchDeviceDto searchDeviceDto) throws FileNotFoundException {
        List<DevicesLog> devices = devicesLogRepository.findAll();

        if(searchDeviceDto.getStartDateTime() != "")
        {
            searchDeviceDto.setStartDate(LocalDate.parse(searchDeviceDto.getStartDateTime()));
        }
        else{
            searchDeviceDto.setStartDate(LocalDate.parse("1900-01-01"));
        }
        if(searchDeviceDto.getEndDateTime() != "")
        {
            searchDeviceDto.setEndDate(LocalDate.parse(searchDeviceDto.getEndDateTime()));
        }
        else{
            searchDeviceDto.setEndDate(LocalDate.parse("2100-01-01"));
        }

        List<DevicesLog> findDevices = devices.stream().filter(device -> (device.getRealEstateId() == searchDeviceDto.getRealEstateId())
        && ( (device.getType().toString().contains(searchDeviceDto.getType())) && LocalDate.parse(device.getDateTime()) .isAfter(searchDeviceDto.getStartDate())
        && LocalDate.parse(device.getDateTime()) .isBefore(searchDeviceDto.getEndDate()))
        || ((device.getType().toString()=="")  && LocalDate.parse(device.getDateTime()) .isAfter(searchDeviceDto.getStartDate())
        && LocalDate.parse(device.getDateTime()) .isBefore(searchDeviceDto.getEndDate()))).collect(Collectors.toList());
        
        return findDevices;
    }
}
