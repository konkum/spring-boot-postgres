package com.example.spring_study.controller;

import com.example.spring_study.constant.RateType;
import com.example.spring_study.constant.Type;
import com.example.spring_study.model.Device;
import com.example.spring_study.model.payload.BaseSearchRequest;
import com.example.spring_study.model.payload.BaseSortRequest;
import com.example.spring_study.model.payload.DeviceRequest;
import com.example.spring_study.service.DeviceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/device")
public class DeviceController {
    @Autowired
    private DeviceService deviceService;

    @PostMapping(path = "/create")
    private ResponseEntity<Device> createDevice(@Valid @RequestBody DeviceRequest request){
        Device device = deviceService.createDevice(request);
        if (device ==null){
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(device);
    }

    @GetMapping(path = "/get")
    private ResponseEntity<Device> getDeviceById(@Param("id") int id){
        Device device = deviceService.getDeviceById(id);
        if (device ==null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(device);
    }

    @GetMapping(path = "/getAll")
    private ResponseEntity<Page<Device>> getDevices(@Valid @ModelAttribute BaseSearchRequest request){
        Page<Device> devices = deviceService.getAllDevices(request);
        if (devices ==null || devices.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(devices);
    }

    @GetMapping(path = "/getDevicesSortBy")
    private ResponseEntity<Page<Device>> getDevicesSortBy(@Valid @ModelAttribute BaseSortRequest request){
        Page<Device> devices = deviceService.getDevicesSortedBy(request);
        if (devices ==null || devices.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(devices);
    }

    @GetMapping(path = "/findByItemName")
    private ResponseEntity<Page<Device>> findByItemName(@Param("item name") String itemName,@Valid @ModelAttribute BaseSearchRequest pageable){
        Page<Device> devices = deviceService.findDeviceByItemName(itemName, pageable);
        if (devices.isEmpty() || devices==null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(devices);
    }

    @GetMapping(path = "/findByCreatedDate")
    private ResponseEntity<Page<Device>> findByCreatedDate( @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime startDate,
                                                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime endDate,
                                                            @Valid @ModelAttribute BaseSearchRequest pageable){
        Page<Device> devices = deviceService.findDeviceByDate(startDate,endDate, pageable);
        if (devices.isEmpty() || devices==null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(devices);
    }

    @GetMapping(path = "/findByType")
    private ResponseEntity<Page<Device>> findByType(@Param("type") Type type,@Valid @ModelAttribute BaseSearchRequest pageable){
        Page<Device> devices = deviceService.findDeviceByType(type, pageable);
        if (devices.isEmpty() || devices==null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(devices);
    }

    @GetMapping(path = "/findByRateType")
    private ResponseEntity<Page<Device>> findByRateType(@Param("rate type") RateType rateType, @Valid @ModelAttribute BaseSearchRequest pageable){
        Page<Device> devices = deviceService.findDeviceByRateType(rateType, pageable);
        if (devices.isEmpty() || devices==null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(devices);
    }

    @PutMapping(path = "/update")
    private ResponseEntity<Device> updateDevice(@Param("id")int id,@Valid @RequestBody DeviceRequest request){
        Device device = deviceService.updateDevice(id,request);
        if (device ==null){
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }

        return ResponseEntity.ok(device);
    }

    @DeleteMapping(path = "/delete")
    private ResponseEntity deleteDevice(@Param("id")int id){
        deviceService.deleteDevice(id);
        return ResponseEntity.ok().build();
    }
}
