package hu.bla.model;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "DTYPE")
public abstract class IratBase {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	private Felelos felelos;
	
	public IratBase() {}
	
	public IratBase(Felelos felelos) {
		this.felelos = felelos;
	}

	public Long getId() {
		return id;
	}

	public Felelos getFelelos() {
		return felelos;
	}

	public void setFelelos(Felelos felelos) {
		this.felelos = felelos;
	}
}
