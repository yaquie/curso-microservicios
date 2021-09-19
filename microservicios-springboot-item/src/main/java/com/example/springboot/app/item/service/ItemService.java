package com.example.springboot.app.item.service;

import java.util.List;

import com.example.springboot.app.item.model.Item;
//import com.example.springboot.app.item.model.Producto;
import pe.com.app.commons.entity.Producto;

public interface ItemService {
	public List<Item> findAll();
	public Item findById(Long id, Integer cantidad);
	public Producto create(Producto p);
	public Producto update(Producto p, Long id);
	public void delete(Long id);
}
