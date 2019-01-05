package com.renatoviegas.starwars.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.renatoviegas.starwars.api.entity.Planet;
import com.renatoviegas.starwars.api.repository.PlanetRepository;
import com.renatoviegas.starwars.api.service.PlanetService;

@Service
public class PlanetServiceImpl implements PlanetService {

	@Autowired
	private PlanetRepository planetRepository;
	
	@Override
	public Planet findById(String id) {
		return this.planetRepository.findById(id);
	}

	@Override
	public Planet findByName(String name) {
		return this.planetRepository.findByName(name);
	}

	@Override
	public Planet createOrUpdate(Planet planet) {
		return this.planetRepository.save(planet);
	}

	@Override
	public void delete(String id) {
		this.planetRepository.delete(id);
	}

	@Override
	public List<Planet> findAll() {
		return this.planetRepository.findAll();
	}

	@Override
	public Page<Planet> findAll(int page, int count) {
		Pageable pages = new PageRequest(page, count);
		return this.planetRepository.findAll(pages);
	}

	
}
