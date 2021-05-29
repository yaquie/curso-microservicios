package com.example.springboot.app.productos.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.springboot.app.productos.model.entity.Producto;
import com.example.springboot.app.productos.service.IProductoService;

@RestController
public class ProductoController {
	
	@Autowired
	private Environment env;
	
	@Value("${server.port}")
	private Integer port;
	
	@Autowired //para injectar la clase service
	private IProductoService iProductoService;

	@GetMapping("/listar") // permite mapear una url
	public List<Producto> listar() {
		return iProductoService.findAll().stream().map(producto ->{
//			producto.setPort(Integer.parseInt(env.getProperty("local.server.port")));
			producto.setPort(port);
			return producto;
		}).collect(Collectors.toList());
	}

	@GetMapping("/ver/{id}") // permite mapear una url
	public Producto detalle(@PathVariable Long id) throws Exception {
		Producto producto= iProductoService.findById(id);
//		producto.setPort(Integer.parseInt(env.getProperty("local.server.port")));
		producto.setPort(port);
		
//		boolean ok=false;
//		if (ok=false) {
//			throw new Exception("No se pudo cargar el producto");
//		}
		
//		try {
//			Thread.sleep(2000L);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		return producto;
	}
}
