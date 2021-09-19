package com.example.springboot.app.item.clientes;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

//import com.example.springboot.app.item.model.Producto;
import pe.com.app.commons.entity.Producto;

//declarando que esta interfaz es un cliente feign
@FeignClient(name = "servicio-productos")
//@RibbonClient(name = "servicio-productos")
public interface ProductoClienteRest {

	@GetMapping("/listar")
	public List<Producto> listar();

	@GetMapping("/ver/{id}")
	public Producto detalle(@PathVariable Long id);

	@PostMapping("/crear")
	public Producto crear(@RequestBody Producto producto);

	@PutMapping("/editar/{id}")
	public Producto editar(@RequestBody Producto producto, @PathVariable Long id);

	@DeleteMapping("/eliminar/{id}")
	public void eliminar(@PathVariable Long id);

}
