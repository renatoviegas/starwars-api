package com.renatoviegas.starwars.api.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.renatoviegas.starwars.api.entity.Planet;

public interface PlanetService {

	Planet findById(String id);
	
	Planet findByName(String name);
	
	Planet createOrUpdate(Planet planet);
	
	void delete(String id);
	
	Page<Planet> findAll(int page, int count);
	
	List<Planet> findAll();
	
}
