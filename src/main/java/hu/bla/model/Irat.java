package hu.bla.model;

import javax.persistence.Entity;

@Entity
public class Irat extends IratBase {
	public Irat() {}

	public Irat(Felelos felelos) {
		super(felelos);
	}
}
