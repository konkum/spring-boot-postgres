package com.example.spring_study.service.impl;

import com.example.spring_study.constant.RateType;
import com.example.spring_study.constant.Type;
import com.example.spring_study.model.Device;
import com.example.spring_study.model.payload.BaseSearchRequest;
import com.example.spring_study.model.payload.BaseSortRequest;
import com.example.spring_study.model.payload.DeviceRequest;
import com.example.spring_study.repository.DeviceRepository;
import com.example.spring_study.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DeviceServiceImpl implements DeviceService {
    @Autowired
    private DeviceRepository deviceRepository;

    @Override
    public Device createDevice(DeviceRequest request) {
        try{
            Type type = Type.valueOf(request.getType());
            RateType rateType = RateType.valueOf(request.getRateType());
            Device response = new Device(type,request.getUnitPrice(),rateType,request.getBranchName(),request.getItemName(),request.getVersion(),request.getOriginalPrice());
            return deviceRepository.save(response);
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Device updateDevice(int id,DeviceRequest request) {
        try {
            Device device = getDeviceById(id);
            if (device == null){
                return null;
            }

            device.setType(Type.valueOf(request.getType()));
            device.setUnitPrice(request.getUnitPrice());
            device.setRateType(RateType.valueOf(request.getRateType()));
            device.setBranchName(request.getBranchName());
            device.setItemName(request.getItemName());
            device.setVersion(request.getVersion());
            device.setOriginalPrice(request.getOriginalPrice());
            device.getDateAudit().updateUpdatedAt();

            return deviceRepository.save(device);
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Device getDeviceById(int id) {
        return deviceRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteDevice(int id) {
        deviceRepository.deleteById(id);
    }

    @Override
    public Page<Device> getAllDevices(BaseSearchRequest request) {
        Sort sort = Sort.by(Sort.Order.asc("id"));
        Pageable pageable = PageRequest.of(request.getPageNumber(),request.getPageSize(),sort);
        return deviceRepository.findAll(pageable);
    }

    @Override
    public Page<Device> findDeviceByItemName(String name, BaseSearchRequest request) {
        Pageable pageable = PageRequest.of(request.getPageNumber(),request.getPageSize());
        Page<Device> devices = deviceRepository.findByItemName(name, pageable);
        if (devices.isEmpty()){
            return null;
        }

        return devices;
    }

    @Override
    public Page<Device> findDeviceByDate(LocalDateTime startDate, LocalDateTime endDate, BaseSearchRequest request) {
        Pageable pageable = PageRequest.of(request.getPageNumber(),request.getPageSize());
        Page<Device> devices = deviceRepository.findByDateAudit_CreatedAtBetween(startDate,endDate, pageable);
        if (devices.isEmpty()){
            return null;
        }

        return devices;
    }

    @Override
    public Page<Device> findDeviceByType(Type type, BaseSearchRequest request) {
        Pageable pageable = PageRequest.of(request.getPageNumber(),request.getPageSize());
        Page<Device> devices = deviceRepository.findByType(type,pageable);
        if (devices.isEmpty()){
            return null;
        }

        return devices;
    }

    @Override
    public Page<Device> findDeviceByRateType(RateType rateType, BaseSearchRequest request) {
        Pageable pageable = PageRequest.of(request.getPageNumber(),request.getPageSize());
        Page<Device> devices = deviceRepository.findByRateType(rateType,pageable);
        if (devices.isEmpty()){
            return null;
        }

        return devices;
    }

    @Override
    public Page<Device> getDevicesSortedBy(BaseSortRequest request) {
        Sort sort = Sort.by(Sort.Order.asc(request.getSortString()));
        if (request.getSortDirection()!=null){
            switch (request.getSortDirection()){
                case ASC -> sort = Sort.by(Sort.Order.asc(request.getSortString()));
                case DESC -> sort = Sort.by(Sort.Order.desc(request.getSortString()));
            }
        }
        Pageable pageable = PageRequest.of(request.getPageNumber(),request.getPageSize(),sort);
        return deviceRepository.findAll(pageable);
    }
}
