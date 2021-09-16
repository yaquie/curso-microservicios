package com.example.springboot.app.productos.controller;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.springboot.app.productos.model.entity.Producto;
import com.example.springboot.app.productos.service.IProductoService;

@RestController
public class ProductoController {
	private static Logger log = LoggerFactory.getLogger(ProductoController.class);

	@Autowired
	private Environment env;

	@Value("${server.port}")
	private Integer port;

	@Autowired // para injectar la clase service
	private IProductoService iProductoService;

	@GetMapping("/listar") // permite mapear una url
	public List<Producto> listar() {
		return iProductoService.findAll().stream().map(producto -> {
//			producto.setPort(Integer.parseInt(env.getProperty("local.server.port")));
			producto.setPort(port);
			return producto;
		}).collect(Collectors.toList());
	}

	@GetMapping("/ver/{id}") // permite mapear una url
	public Producto detalle(@PathVariable Long id) throws Exception {
		if (id.equals(10L)) {
			throw new IllegalStateException("Producto no Encontrado");
		}

		if (id.equals(7L)) {
			TimeUnit.SECONDS.sleep(5L);
		}

		Producto producto = iProductoService.findById(id);
		if (null != producto) {
			producto.setPort(port);
		} else {
			log.info("no se encontro producto");
		}

		return producto;
	}

	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public Producto crear(@RequestBody Producto p) {

		return iProductoService.save(p);
	}

	@PutMapping("/editar/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Producto editar(@RequestBody Producto p, @PathVariable Long id) {
		Producto prodDB = new Producto();
		try {
			prodDB = iProductoService.findById(id);
			prodDB.setNombre(p.getNombre());
			prodDB.setPrecio(p.getPrecio());

		} catch (Exception e) {
			log.error("error -> ", e);
		}
		return iProductoService.save(prodDB);
	}

	@DeleteMapping("/eliminar/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void eliminar(@PathVariable Long id) {
		iProductoService.deleteById(id);
	}
}
