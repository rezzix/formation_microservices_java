package io.swagger.api.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ClientsDb {
	static Map <Long, Customer> clients = new HashMap<Long, Customer>();
	static {
		clients.put(0L,new Customer(0l,"Axel","Sheild"));
		clients.put(1L,new Customer(1l,"Xena","Nice"));
		clients.put(2L,new Customer(1l,"Arthur","Forest"));
		
		clients.get(0L).setSales(new ArrayList<Sale>());
		clients.get(0L).getSales().add(new Sale(100, "Eur"));
		clients.get(0L).getSales().add(new Sale(400, "Eur"));
	}

}
