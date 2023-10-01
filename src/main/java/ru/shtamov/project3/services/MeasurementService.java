package ru.shtamov.project3.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.shtamov.project3.models.Measurement;
import ru.shtamov.project3.repositories.MeasurementRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MeasurementService {
    private final MeasurementRepository measurementRepository;
    private final SensorService sensorService;
    private final ModelMapper modelMapper;

    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository, SensorService sensorService, ModelMapper modelMapper) {
        this.measurementRepository = measurementRepository;
        this.sensorService = sensorService;
        this.modelMapper = modelMapper;
    }

    public void create(Measurement measurement){
        fillMeasurement(measurement);
        measurementRepository.save(measurement);
    }

    public List<Measurement> getAll(){

        return measurementRepository.findAll();
    }

    public Map<String, Integer> getRainyDays() {
        int countOfRain = 0;
        List<Measurement> measurementsDTO = getAll();
        for (Measurement measurement : measurementsDTO){
            if(measurement.isRaining())
                countOfRain++;
        }
        Map<String, Integer> mapOfRainDay = new HashMap<>();
        mapOfRainDay.put("Count_of_rain_day", countOfRain);

        return mapOfRainDay;
    }


    private void fillMeasurement(Measurement measurement){
        measurement.setSensor(sensorService.findByName(measurement.getSensor().getName()).get());
    }

}
