package com.renatoviegas.starwars.api.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.renatoviegas.starwars.api.entity.Planet;

public interface PlanetRepository extends MongoRepository<Planet, String> {

	Planet findById(String id);
	
	Planet findByName(String name);
	
}
