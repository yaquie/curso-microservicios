package com.example.springboot.app.productos.dao;

import org.springframework.data.repository.CrudRepository;

//import com.example.springboot.app.productos.model.entity.Producto;
import pe.com.app.commons.entity.Producto;

public interface ProductoDao extends CrudRepository<Producto, Long>{
	

}
