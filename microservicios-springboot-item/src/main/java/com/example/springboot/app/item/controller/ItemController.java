package com.example.springboot.app.item.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.springboot.app.item.model.Item;
//import com.example.springboot.app.item.model.Producto;
import pe.com.app.commons.entity.Producto;
import com.example.springboot.app.item.service.ItemService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import io.micrometer.core.annotation.TimedSet;

@RefreshScope
@RestController
public class ItemController {
	private static Logger log = LoggerFactory.getLogger(ItemController.class);
	
	@Autowired
	private CircuitBreakerFactory cbFactory;

	@Autowired
	private Environment env;

	@Autowired
	@Qualifier("serviceFeign") // serviceRestTemplate serviceFeign
	private ItemService itemService;

	@Value("${configuracion.texto}")
	private String texto;

	@GetMapping("/listar")
	public List<Item> listar(@RequestParam(name = "name", required = false) String name,
			@RequestHeader(name = "token-request", required = false) String token) {
		System.out.println("name: " + name);
		System.out.println("token: " + token);
		return itemService.findAll();
	}
// Factory primera forma
//	@HystrixCommand(fallbackMethod = "metodoAlternativo")
	@GetMapping("/ver/{id}/cantidad/{cantidad}")
	public Item detalle(@PathVariable Long id, @PathVariable Integer cantidad) {
		return cbFactory.create("items").run(() -> itemService.findById(id, cantidad),
				e -> metodoAlternativo(id, cantidad, e));
	}

	// Factory segunda forma
	@CircuitBreaker(name="items", fallbackMethod = "metodoAlternativo")
	@GetMapping("/ver2/{id}/cantidad/{cantidad}")
	public Item detalle2(@PathVariable Long id, @PathVariable Integer cantidad) {
		return itemService.findById(id, cantidad);
	}
	
	@CircuitBreaker(name = "items", fallbackMethod = "metodoAlternativo2")
	@TimeLimiter(name = "items")
	@GetMapping("/ver3/{id}/cantidad/{cantidad}")
	public CompletableFuture<Item> detalle3(@PathVariable Long id, @PathVariable Integer cantidad) {
		return CompletableFuture.supplyAsync(() -> itemService.findById(id, cantidad));
	}

	public Item metodoAlternativo(Long id, Integer cantidad, Throwable e) {
		log.error("error: ", e.getMessage());
		Item itm = new Item();
		Producto p = new Producto();
		itm.setCantidad(cantidad);
		p.setId(id);
		p.setNombre("Helados");
		p.setPrecio(2.5);
		itm.setProducto(p);
		return itm;
	}

	public CompletableFuture<Item> metodoAlternativo2(Long id, Integer cantidad, Throwable e) {
		log.error("error: ", e.getMessage());
		Item itm = new Item();
		Producto p = new Producto();
		itm.setCantidad(cantidad);
		p.setId(id);
		p.setNombre("Helados");
		p.setPrecio(2.5);
		itm.setProducto(p);
		return CompletableFuture.supplyAsync(() -> itm);
	}
	
	@GetMapping("/obtener-config")
	public ResponseEntity<?> obtenerConfig(@Value("${server.port}") String puerto) {
		log.info("texto -> " + texto);

		Map<String, String> json = new HashMap<>();
		json.put("texto", texto);
		json.put("puerto", puerto);

		if (env.getActiveProfiles().length > 0 && env.getActiveProfiles()[0].equals("dev")) {
			json.put("autor.nombre", env.getProperty("configuracion.autor.nombre"));
			json.put("autor.email", env.getProperty("configuracion.autor.email"));
		}

		return new ResponseEntity<Map<String, String>>(json, HttpStatus.OK);

	}

	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public Producto crear(@RequestBody Producto prod) {
		Producto p = itemService.create(prod);
		return p;
	}

	@PutMapping("/editar/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Producto editar(@RequestBody Producto p, @PathVariable Long id) {
		Producto prod = itemService.update(p, id);
		return prod;
	}

	@DeleteMapping("/eliminar/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void eliminar(@PathVariable Long id) {
		itemService.delete(id);

	}

}
