package ru.shtamov.project3.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.shtamov.project3.dto.MeasurementDTO;
import ru.shtamov.project3.models.Measurement;
import ru.shtamov.project3.services.MeasurementService;
import ru.shtamov.project3.util.MeasurementErrorResponse;
import ru.shtamov.project3.util.MeasurementException;
import ru.shtamov.project3.util.MeasurementValidator;

import java.util.List;
import java.util.Map;

import static ru.shtamov.project3.util.ErrorsUtil.returnErrorsToClient;

@RestController
@RequestMapping("/measurements")
public class MeasurementController {
    private final MeasurementService measurementService;
    private final ModelMapper modelMapper;

    private final MeasurementValidator measurementValidator;



    public MeasurementController(MeasurementService measurementService, ModelMapper modelMapper, MeasurementValidator measurementValidator) {
        this.measurementService = measurementService;
        this.modelMapper = modelMapper;
        this.measurementValidator = measurementValidator;
    }
    @PostMapping("/add")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid MeasurementDTO measurementDTO,
                                             BindingResult bindingResult){

        Measurement measurement = convertToMeasurement(measurementDTO);
        measurementValidator.validate(measurement, bindingResult);

        if(bindingResult.hasErrors()){
            returnErrorsToClient(bindingResult);
        }

        measurementService.create(measurement);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping()
    public List<MeasurementDTO> getAllMeasurements(){
        return measurementService.getAll().stream().map(this::convertToMeasurementDTO).toList();
    }

    @GetMapping("rainyDaysCount")
    public Map<String, Integer> getRainyDaysCount(){
        return measurementService.getRainyDays();
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> handleException(MeasurementException e){
        MeasurementErrorResponse measurementErrorResponse = new MeasurementErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(measurementErrorResponse, HttpStatus.CONFLICT);
    }

    private Measurement convertToMeasurement(MeasurementDTO measurementDTO){
        return modelMapper.map(measurementDTO, Measurement.class);
    }

    private MeasurementDTO convertToMeasurementDTO(Measurement measurement) {
        MeasurementDTO measurementDTO = modelMapper.map(measurement, MeasurementDTO.class);
        return measurementDTO;
    }
}
