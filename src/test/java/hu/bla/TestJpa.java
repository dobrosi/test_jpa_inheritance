package hu.bla;

import hu.bla.model.Felelos;
import hu.bla.model.Felhasznalo;
import hu.bla.model.Irat;
import hu.bla.model.IratBase;
import hu.bla.model.SzervezetiEgyseg;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.JVM)
public class TestJpa {
	private Logger logger = Logger.getLogger("TestJpa");
	private static EntityManagerFactory factory;
	private EntityManager em;
	private EntityTransaction transaction;
	private long time;

	@Test
	public void initBla() {
		System.out.println("!!!!!!!");
	}
	
	@Test()
	public void testHibernate() {
		String persUnit = "hibernate";
		setup(persUnit);

		test(1);

		printTime(persUnit);
		
		System.out.println("Andriska");
	}

	@Test
	@Ignore
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
				printResult(false);
				printResult(true);
			}
		}
	}

	private void persist() {
		openTx();

		Irat irat1 = new Irat();
		Irat irat2 = new Irat();
		Felelos f1, f2;
		irat1.setFelelos(f1 = new SzervezetiEgyseg("Informatikai Osztály"));
		irat2.setFelelos(f2 = new Felhasznalo("Dobrosi András"));

		em.persist(f1);
		em.persist(f2);
		em.persist(irat1);
		em.persist(irat2);

		closeTx();
	}

	private void printResult(boolean withFetch) {
		openTx();

		String sql = "select i from IratBase i";
		if(withFetch) {
			sql += " join fetch i.felelos";
			logger.info("Ha join fetch van a query-ben:");
		} else {
			logger.info("Join fetch nélkül:");
		}
		Query q = em.createQuery(sql);
		List<IratBase> iratok = q.getResultList();
		
		iratok.add(new Irat(new Felhasznalo("Hibernétnál Kakukk Tojás")));

		for (IratBase iratBase : iratok) {
			Felelos f = iratBase.getFelelos();
			// Hibernate proxy-zott tipust rak ki,
			// ezzel szemben az Eclipselink a ténylegeset.
			logger.info("<<< EZ A LÉNYEG >>> class: " + f.getClass() + ", name: " + f.getName());
		}

		logger.info("Size: " + iratok.size());
		
		closeTx();
	}

	private void printTime(String persUnit) {
		logger.info("time with " + persUnit + ": " + (System.currentTimeMillis() - time) + "ms");
	}

	private void setup(String persUnit) {
		factory = FactoryHolder.getFactorty(persUnit);
		time = System.currentTimeMillis();
	}

	private void openTx() {
		em = factory.createEntityManager();
		transaction = em.getTransaction();
		transaction.begin();
	}

	private void closeTx() {
		em.flush();
		em.close();
		transaction.commit();
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
