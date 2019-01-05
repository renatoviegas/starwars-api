package com.renatoviegas.starwars;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.junit.Assert;
import org.junit.Test;

public class StarwarsApiApplicationTests {
	
	@Test
    public void testaApiSW() {
		Client client = ClientBuilder.newClient();
        WebTarget target = client.target("https://swapi.co");
        String conteudo = target.path("/api/people/1/").request().get(String.class);
        System.out.println(conteudo);
        Assert.assertTrue(conteudo.contains("Luke Skywalker"));
    }

}
