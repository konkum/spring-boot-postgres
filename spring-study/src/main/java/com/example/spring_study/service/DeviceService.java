package com.example.spring_study.service;

import com.example.spring_study.constant.RateType;
import com.example.spring_study.constant.Type;
import com.example.spring_study.model.Device;
import com.example.spring_study.model.payload.BaseSearchRequest;
import com.example.spring_study.model.payload.BaseSortRequest;
import com.example.spring_study.model.payload.DeviceRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface DeviceService {
    public Device createDevice(DeviceRequest request);
    public Device updateDevice(int id,DeviceRequest request);
    public Device getDeviceById(int id);
    public void deleteDevice(int id);
    public Page<Device> getAllDevices(BaseSearchRequest request);
    public Page<Device> findDeviceByItemName(String name, BaseSearchRequest request);
    public Page<Device> findDeviceByDate(LocalDateTime startDate,LocalDateTime endDate,BaseSearchRequest request);
    public Page<Device> findDeviceByType(Type type, BaseSearchRequest request);
    public Page<Device> findDeviceByRateType(RateType rateType, BaseSearchRequest request);
    public Page<Device> getDevicesSortedBy(BaseSortRequest request);
}
