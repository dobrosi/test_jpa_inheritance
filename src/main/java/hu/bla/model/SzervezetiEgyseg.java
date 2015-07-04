package hu.bla.model;

import javax.persistence.Entity;

@Entity
public class SzervezetiEgyseg extends Felelos {

	public SzervezetiEgyseg() {}
	
	public SzervezetiEgyseg(String name) {
		super(name);
	}
	
	@Override
	public String getReferenceName() {
		return "szervezetiEgyseg";
	}
}