package com.renatoviegas.starwars.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.renatoviegas.starwars.api.entity.Planet;
import com.renatoviegas.starwars.api.response.Response;
import com.renatoviegas.starwars.api.service.PlanetService;

@RestController
@RequestMapping("/api/planet")
@CrossOrigin(origins="*")
public class PlanetController {

	@Autowired
	private PlanetService planetService;
	
	@PostMapping
	public ResponseEntity<Response<Planet>> create(HttpServletRequest request, @RequestBody Planet planet,
			BindingResult result) {
		
		Response<Planet> response = new Response<Planet>();
		
		try {
			validateCreatePlanet(planet, result);
			if (result.hasErrors()) {
				result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
				return ResponseEntity.badRequest().body(response);
			}
			
			Planet planetPersisted = (Planet) planetService.createOrUpdate(planet);
			response.setData(planetPersisted);
			
		} catch (DuplicateKeyException de) {
			response.getErrors().add("Name already exists!");
			return ResponseEntity.badRequest().body(response);
			
		} catch (Exception e) {
			response.getErrors().add(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}

		return ResponseEntity.ok(response);
	}
	
	private void validateCreatePlanet(Planet planet, BindingResult result) {
		if (planet.getName() == null) {
			result.addError(new ObjectError("Planet", "Nome não informado"));
		}
	}
	
	@PutMapping
	public ResponseEntity<Response<Planet>> update(HttpServletRequest request, @RequestBody Planet planet,
			BindingResult result) {
		Response<Planet> response = new Response<Planet>();
		
		try {
			validateUpdatePlanet(planet, result);
			if (result.hasErrors()) {
				result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
				return ResponseEntity.badRequest().body(response);
			}
			
			Planet planetPersisted = (Planet) planetService.createOrUpdate(planet);
			response.setData(planetPersisted);
					
		} catch (Exception e) {
			response.getErrors().add(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}		
		
		return ResponseEntity.ok(response);
	}
	
	private void validateUpdatePlanet(Planet planet, BindingResult result) {
		if(planet.getId() == null) {
			result.addError(new ObjectError("Planet", "Id não informado"));
		}
		
		if(planet.getName() == null) {
			result.addError(new ObjectError("Planet", "Nome não informado"));
		}
	}	
	
	@GetMapping(value = "{id}")
	public ResponseEntity<Response<Planet>> findById(@PathVariable("id") String id) {
		Response<Planet> response = new Response<Planet>();
		Planet planet = planetService.findById(id);

		if (planet == null) {
			response.getErrors().add("Registro não encontrado. Id: " + id);
			return ResponseEntity.badRequest().body(response);
		}
		
		response.setData(planet);		
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping(value = "{id}")
	public ResponseEntity<Response<String>> delete(@PathVariable("id") String id) {
		Response<String> response = new Response<String>();
		Planet planet = planetService.findById(id);

		if (planet == null) {
			response.getErrors().add("Registro não encontrado id: " + id);
			return ResponseEntity.badRequest().body(response);
		}
		
		planetService.delete(id);
		
		return ResponseEntity.ok(new Response<String>());
	}	
	
	@GetMapping(value = "{page}/{count}")
	public ResponseEntity<Response<Page<Planet>>> findAll(@PathVariable int page, @PathVariable int count) {
		Response<Page<Planet>> response = new Response<Page<Planet>>();
		Page<Planet> planets = planetService.findAll(page, count);
		response.setData(planets);
		return ResponseEntity.ok(response);		
	}
	
	@GetMapping
	public ResponseEntity<Response<Iterable<Planet>>> findAllReviews() {
		Response<Iterable<Planet>> response = new Response<Iterable<Planet>>();		
		Iterable<Planet> planets = planetService.findAll();		
		response.setData(planets);
		return ResponseEntity.ok(response);		
	}	
	
}