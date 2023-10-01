package ru.shtamov.project3;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Project3Application {

	public static void main(String[] args) {
		SpringApplication.run(Project3Application.class, args);
	}

	//Создаем бин (объект) класса ModelMapper, так как будем часто его ипользовать в коде.
	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}
}
