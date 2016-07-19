package hu.bla;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.junit.Test;

import hu.bla.model.Felelos;
import hu.bla.model.Felhasznalo;
import hu.bla.model.Irat;
import hu.bla.model.IratBase;
import hu.bla.model.SzervezetiEgyseg;

public class TestFasterQuery {
	private Logger logger = Logger.getLogger("TestFasterQuery");
	private static EntityManagerFactory factory;
	private EntityManager em;
	private EntityTransaction transaction;
	private long time;

	@Test()
	public void test() {
		String persUnit = "hibernate";
		setup(persUnit);

		em = factory.createEntityManager();

		openTx();
		createData();
		closeTx();

		openTx();
		printResult(true);
		closeTx();
		
		openTx();
		printResult(true);
		closeTx();
		
		// test(1, true);
		// printTime("WITH JOIN");
		em.close();
	}

	private void test(int count, boolean withJoin) {
		/*
		 * for (int i = 0; i < count; i++) {
		 * 
		 * //if (i == count - 1) { printResult(withJoin); //} }
		 */

		printTime("no cache");
		printCount(1000, false);

		printTime("cache");
		printCount(1000, true);

	}

	private void createData() {
		Irat irat1 = new Irat();
		Irat irat2 = new Irat();
		Felelos f1, f2;
		irat1.setFelelos(f1 = new SzervezetiEgyseg("Informatikai Osztály"));
		irat2.setFelelos(f2 = new Felhasznalo("Dobrosi András"));

		em.persist(f1);
		em.persist(f2);
		em.persist(irat1);
		em.persist(irat2);
	}

	private void printResult(boolean withFetch) {
		String sql = "select i from IratBase i";
		if (withFetch) {
			sql += " join fetch i.felelos";
			logger.info("Ha join fetch van a query-ben:");
		} else {
			logger.info("Join fetch nélkül:");
		}

		Query q = em.createQuery(sql, IratBase.class);
		//q.setHint("org.hibernate.cacheable", true);
		List<IratBase> iratok = q.getResultList();

		printIratFelelosok(iratok);
	}

	private void printCount(int count, boolean withCache) {
		openTx();

		for (int i = 0; i < count; i++)
			logger.info("felelosok: " + em.createQuery("select count(*) from Felelos")
					.setHint("org.hibernate.cacheable", withCache).getSingleResult());

		closeTx();
	}

	private void printIratFelelosok(List<IratBase> iratok) {
		for (IratBase iratBase : iratok) {
			Felelos f = iratBase.getFelelos();
			logger.info("class: " + f.getClass() + ", name: " + f.getName() + ", addr: " + f.getAddress().getStreet());
		}
	}

	private void printTime(String persUnit) {
		logger.info("time with " + persUnit + ": " + (System.currentTimeMillis() - time) + "ms");
	}

	private void setup(String persUnit) {
		factory = FactoryHolder.getFactorty(persUnit);
		time = System.currentTimeMillis();
	}

	private void openTx() {
		transaction = em.getTransaction();
		transaction.begin();
	}

	private void closeTx() {
		em.flush();
		transaction.commit();
	}

}
