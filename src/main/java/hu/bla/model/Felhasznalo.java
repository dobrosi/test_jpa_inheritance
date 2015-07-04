package hu.bla.model;

import javax.persistence.Entity;

@Entity
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