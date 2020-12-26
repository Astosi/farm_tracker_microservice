package com.farmTracker.demo;

import com.farmTracker.demo.Repo.PlantTypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {
PlantTypeRepo plantTypeRepo;


	@Autowired
	public DemoApplication(PlantTypeRepo plantTypeRepo) {
		this.plantTypeRepo = plantTypeRepo;
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.printf(plantTypeRepo.findAll().get(0).getPTUID());

	}
}
