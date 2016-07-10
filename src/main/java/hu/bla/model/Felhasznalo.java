package hu.bla.model;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Felhasznalo extends Felelos {

	
	public Felhasznalo() {
	}
	
	public Felhasznalo(String name) {
		super(name);
	}
		
	@Override
	public String getReferenceName() {
		return "felhasznalo";
	}
}