package ru.shtamov.project3.services;

import ru.shtamov.project3.models.Sensor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.shtamov.project3.repositories.SensorRepository;


import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SensorService {
    private final SensorRepository sensorRepository;

    @Autowired
    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    public void create(Sensor sensor){
        fillSensor(sensor);
        sensorRepository.save(sensor);
    }
    private void fillSensor(Sensor sensor){
        sensor.setCreatedAt(LocalDateTime.now());
    }

    public Optional<Sensor> findByName(String name){
        return sensorRepository.findByName(name);
    }
}
