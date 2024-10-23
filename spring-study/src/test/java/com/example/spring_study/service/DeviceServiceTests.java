package com.example.spring_study.service;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.example.spring_study.constant.RateType;
import com.example.spring_study.constant.Type;
import com.example.spring_study.model.Device;
import com.example.spring_study.model.payload.BaseSearchRequest;
import com.example.spring_study.model.payload.DeviceRequest;
import com.example.spring_study.repository.DeviceRepository;
import com.example.spring_study.service.impl.DeviceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class DeviceServiceTests {
    @Mock
    private DeviceRepository deviceRepository;
    @InjectMocks
    private DeviceServiceImpl deviceService;

    private Device device;
    private DeviceRequest deviceRequest;

    @BeforeEach
    void setup(){
        device = new Device(Type.MOUSE, 110.0, RateType.LIKENEW,"Branch 1","Item 1","Version 1",130.0);
        deviceRequest = new DeviceRequest();
        deviceRequest.setType(Type.MOUSE.toString());
        deviceRequest.setUnitPrice(110.0);
        deviceRequest.setRateType(RateType.LIKENEW.toString());
        deviceRequest.setBranchName("Branch 1");
        deviceRequest.setItemName("Item 1");
        deviceRequest.setVersion("Version 1");
        deviceRequest.setOriginalPrice(130.0);
    }

    @Test
    void testCreateDevice() {
        when(deviceRepository.save(any(Device.class))).thenReturn(device);

        Device createdDevice = deviceService.createDevice(deviceRequest);
        assertNotNull(createdDevice);
        assertEquals(deviceRequest.getItemName(), createdDevice.getItemName());
        verify(deviceRepository, times(1)).save(device);
    }
}
