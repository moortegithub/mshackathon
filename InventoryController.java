package com.event257.demo;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.Iterator;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InventoryController {

	private static final String updateTemplateStr = "Updated Inventory, %s!";
	private static final String addTemplateStr = "Added Inventory: , %s!";
	private String deleteTemplateStr = "Deleted Inventory: , %s!";
	private String getTemplateStr = "Retrieved Inventory: , %s!";
	private final AtomicLong counter = new AtomicLong();
	private Map inventoryMap = new HashMap<Integer, String>();

	@GetMapping("/inventory")
	public Inventory retrieveIntentory(@RequestParam(value = "id", defaultValue = "Inventory") int id) {
		String inventoryItem = null;
		/*
		 * if (inventoryMap.containsKey(id)) {
		 * inventoryItem = (String) inventoryMap.get(id);
		 * } else {
		 * getTemplateStr = "No records found.";
		 * }
		 */
		inventoryItem = (String) inventoryMap.get(id);
		if (inventoryItem == null) {
			inventoryItem = "No Records Found";
		}
		return new Inventory(id, String.format(getTemplateStr,
				inventoryItem));
	}

	@PostMapping("/inventory")
	public Inventory addIntentory(@RequestParam(value = "name", defaultValue = "World") String name,
			@RequestParam(value = "id", defaultValue = "0") int id) {
		inventoryMap.put(id, name);
		return new Inventory(id, String.format(addTemplateStr, name));
	}

	@PutMapping("/inventory")
	public Inventory putIntentory(@RequestParam(value = "name", defaultValue = "inventory") String name,
			@RequestParam(value = "id", defaultValue = "0") int id) {
		/*
		 * if (inventoryMap.containsKey(id)) {
		 * inventoryMap.remove(id);
		 * }
		 */
		inventoryMap.put(id, name);
		return new Inventory(id, String.format(updateTemplateStr, name));
	}

	@DeleteMapping("/inventory")
	public Inventory deleteIntentory(@RequestParam(value = "id", defaultValue = "remove") int id) {
		if (inventoryMap.containsKey(id)) {
			inventoryMap.remove(id);
		} else {
			deleteTemplateStr = "No records found.";
		}
		return new Inventory(id, String.format(deleteTemplateStr, id));
	}
}
