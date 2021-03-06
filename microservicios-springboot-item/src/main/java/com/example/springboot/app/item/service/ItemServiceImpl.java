package com.example.springboot.app.item.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.springboot.app.item.model.Item;
//import com.example.springboot.app.item.model.Producto;
import pe.com.app.commons.entity.Producto;

@Service("serviceRestTemplate")
public class ItemServiceImpl implements ItemService {

	// inyectar -> integrar
	@Autowired
	private RestTemplate clienteRest;

	@Override
	public List<Item> findAll() {

//		List<Producto> productos = Arrays.asList(clienteRest.getForObject("http://localhost:8001/listar", Producto.class));

		Producto[] productos = clienteRest.getForObject("//servicio-productos/listar", Producto[].class);

		// stream -> para convertir una lista en un flujo
		// map -> para convertir cada elemento del flujo, un objeto Producto en Item
		// collect -> convertir el String a un tipo List
//		return productos.stream().map(p -> new Item(p, 1)).collect(Collectors.toList());
		List<Producto> productoItem = Arrays.asList(productos);
		return productoItem.stream().map(p -> new Item(p, 1)).collect(Collectors.toList());
	}

	@Override
	public Item findById(Long id, Integer cantidad) {
		Map<String, String> pathVariable = new HashMap<String, String>();
		pathVariable.put("id", id.toString());
		Producto producto = clienteRest.getForObject("//servicio-productos/ver/{id}", Producto.class, pathVariable);
        System.out.println("itemservice" + producto.getNombre());
		return new Item(producto, cantidad);
	}

	@Override
	public Producto create(Producto p) {
        HttpEntity<Producto> request = new HttpEntity<>(p);
		ResponseEntity<Producto> response = clienteRest.exchange("//servicio-productos/crear", HttpMethod.POST, request, Producto.class);
		
		
		return (Producto) response.getBody();
	}

	@Override
	public Producto update(Producto p, Long id) {
		Map<String, String> pathVariable = new HashMap<String, String>();
		pathVariable.put("id", id.toString());

		HttpEntity<Producto> request = new HttpEntity<>(p);
		ResponseEntity<Producto> response = clienteRest.exchange("//servicio-productos/editar/{id}", HttpMethod.POST,
				request, Producto.class, pathVariable);

		return response.getBody();
	}

	@Override
	public void delete(Long id) {
		Map<String, String> pathVariable = new HashMap<String, String>();
		pathVariable.put("id", id.toString());
		clienteRest.delete("//servicio-productos/eliminar/{id}", pathVariable);
	}

}
