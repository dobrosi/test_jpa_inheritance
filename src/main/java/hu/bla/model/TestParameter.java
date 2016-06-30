package hu.bla.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TestParameter {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String felelosName;
	
	private Integer intValue;
	
	private String text;
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public TestParameter() {}
	
	public TestParameter(String felelosName, Integer intValue, String text) {
		this.felelosName = felelosName;
		this.intValue = intValue;
		this.text = text;
	}

	public String getFelelosName() {
		return felelosName;
	}

	public void setFelelosName(String felelosName) {
		this.felelosName = felelosName;
	}

	public Integer getIntValue() {
		return intValue;
	}

	public void setIntValue(Integer intValue) {
		this.intValue = intValue;
	}
}
