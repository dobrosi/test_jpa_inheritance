package hu.bla;

import hu.bla.model.Felelos;
import hu.bla.model.Felhasznalo;
import hu.bla.model.Irat;
import hu.bla.model.SzervezetiEgyseg;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.JVM)
public class TestJpa {
	private static EntityManagerFactory factory;
	private EntityManager em;
	private EntityTransaction transaction;
	private long time;

	@Test()
	public void testHibernate() {
		String persUnit = "hibernate";
		setup(persUnit);

		test(1);

		printTime(persUnit);
	}

	@Test
	public void testEclipselink() {
		String persUnit = "eclipselink";
		setup(persUnit);

		test(1);

		printTime(persUnit);
	}

	private void test(int count) {
		for (int i = 0; i < count; i++) {
			persist();

			if (i == count - 1) {
				printResult();
			}
		}
	}

	private void persist() {
		openTx();

		Irat irat1 = new Irat();
		Irat irat2 = new Irat();
		Felelos f1, f2;
		irat1.setFelelos(f1 = new SzervezetiEgyseg("Belyegk"));
		irat2.setFelelos(f2 = new Felhasznalo("Annus"));

		em.persist(f1);
		em.persist(f2);
		em.persist(irat1);
		em.persist(irat2);

		closeTx();
	}

	private void printResult() {
		openTx();

		Query q = em.createQuery("select t from IratBase t");
		List<Irat> iratok = q.getResultList();

		for (Irat irat : iratok) {
			System.out.println(irat);
			Felelos f = irat.getFelelos();
			System.out.println("<<< EZ A LÉNYEG >>> " + f.getClass());
		}
		System.out.println("Size: " + iratok.size());
		closeTx();
	}

	private void printTime(String persUnit) {
		System.out.println("time with " + persUnit + ": " + (System.currentTimeMillis() - time) + "ms");
	}

	private void setup(String persUnit) {
		time = System.currentTimeMillis();
		if (factory != null) {
			factory.close();
		}
		factory = Persistence.createEntityManagerFactory(persUnit);
	}

	private void openTx() {
		em = factory.createEntityManager();
		transaction = em.getTransaction();
		transaction.begin();
	}

	private void closeTx() {
		transaction.commit();
		em.close();
	}

	static {
		try {
			// Jó kis trükk, mert így a H2 DB böngészőből is elérhető:
			// http://localhost:8082
			// user: sa
			// password: sa
			org.h2.tools.Server.createWebServer("-web").start();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
