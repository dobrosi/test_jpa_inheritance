package hu.bla;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class FactoryHolder {
	private static Map<String, EntityManagerFactory> factories = new HashMap<>();

	public static EntityManagerFactory getFactorty(String persUnit) {

		EntityManagerFactory factory = factories.get(persUnit);
		if (factory == null) {
			factory = Persistence.createEntityManagerFactory(persUnit);
			factories.put(persUnit, factory);
		}
		return factory;
	}
}
