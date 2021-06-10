package com.example.springboot.app.productos.service;

import java.util.List;

import com.example.springboot.app.productos.model.entity.Producto;

public interface IProductoService {
	public List<Producto> findAll();
	public Producto findById(Long id);
	public Producto save(Producto producto);
	public void deleteById(Long id) ;
}
