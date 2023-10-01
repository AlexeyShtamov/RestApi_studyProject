package ru.shtamov.project3.controllers;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import ru.shtamov.project3.dto.SensorDTO;
import jakarta.validation.Valid;
import ru.shtamov.project3.models.Sensor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import ru.shtamov.project3.services.SensorService;
import ru.shtamov.project3.util.MeasurementErrorResponse;
import ru.shtamov.project3.util.MeasurementException;
import ru.shtamov.project3.util.SensorValidator;


import static ru.shtamov.project3.util.ErrorsUtil.returnErrorsToClient;

@RestController
@RequestMapping("/sensors")
public class SensorController {

    private final ModelMapper modelMapper;
    private final SensorService sensorService;

    private final SensorValidator sensorValidator;

    @Autowired
    public SensorController(ModelMapper modelMapper, SensorService sensorService, SensorValidator sensorValidator) {
        this.modelMapper = modelMapper;
        this.sensorService = sensorService;
        this.sensorValidator = sensorValidator;
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid SensorDTO sensorDTO,
                                             BindingResult bindingResult){

        Sensor sensor = convertSensorFromDTO(sensorDTO);
        sensorValidator.validate(sensor, bindingResult);

        if(bindingResult.hasErrors()){
            returnErrorsToClient(bindingResult);
        }
        sensorService.create(sensor);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/{name}")
    public Sensor getSensor(@PathVariable("name") String name){
        return sensorService.findByName(name).get();
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> handleException(MeasurementException e){
        MeasurementErrorResponse measurementException = new MeasurementErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(measurementException, HttpStatus.BAD_REQUEST);
    }


    private Sensor convertSensorFromDTO(SensorDTO sensorDTO){
        return modelMapper.map(sensorDTO, Sensor.class);
    }
}

