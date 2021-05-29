package com.example.springboot.app.item.service;

import java.util.List;

import com.example.springboot.app.item.model.Item;

public interface ItemService {
	public List<Item> findAll();

	public Item findById(Long id, Integer cantidad);
}
