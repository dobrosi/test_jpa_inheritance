package hu.bla;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import hu.bla.dto.TestDto;
import hu.bla.model.Felhasznalo;
import hu.bla.model.TestParameter;

public class TestQuery {
	private static EntityManagerFactory factory;
	private EntityManager em;
	private EntityTransaction transaction;

	// adatbazis:
	// 2 felhasznalo. 
	// mindenkihez felelos nev-vel (nem ID-val) kotodik par parameter, amiknek van egy-egy int value-ja
	private void createData() {
		openTx();

		Felhasznalo f1 = new Felhasznalo("Zsolti");
		Felhasznalo f2 = new Felhasznalo("Andris");

		em.persist(new TestParameter(f1.getName(), 28, "param1"));
		em.persist(new TestParameter(f1.getName(), 115, "param2"));

		em.persist(new TestParameter(f2.getName(), 5, "param3"));
		em.persist(new TestParameter(f2.getName(), 11, "param4"));
		em.persist(new TestParameter(f2.getName(), 19, "param5"));

		em.persist(f1);
		em.persist(f2);

		closeTx();
	}
	
	// MAGA A TESZT
	@Test()
	public void testQuery() {
		List<TestDto> list = em.createQuery(
				"SELECT new hu.bla.dto.TestDto(f, p, p.intValue) "
				+ "FROM Felhasznalo f, TestParameter p " 
				+ "WHERE p.felelosName = f.name "
				+ "AND p.intValue = "
					+ "(select max(p1.intValue) "
					+ "FROM Felhasznalo f1, TestParameter p1 "
					+ "WHERE p1.felelosName = f1.name "
					+ "AND f.name = f1.name)",
				TestDto.class).getResultList();

		System.out.println("RESULT:");
		
		list.forEach(l -> System.out.println(l));
	}
	
	@Before
	public void setUp() {
		String persUnit = "hibernate";
		setup(persUnit);

		createData();

		openTx();
	}
	
	@After
	public void tearDown() {
		closeTx();
	}

	private void setup(String persUnit) {
		factory = FactoryHolder.getFactorty(persUnit);
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
}