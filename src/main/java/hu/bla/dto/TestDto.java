package hu.bla.dto;

import hu.bla.model.Felhasznalo;
import hu.bla.model.TestParameter;

public class TestDto {
	private Felhasznalo felhasznalo;
	private Integer intValue;
	private TestParameter testParameter;

	public TestDto(Felhasznalo felhasznalo, TestParameter testParameter, Integer intValue) {
		super();
		this.felhasznalo = felhasznalo;
		this.testParameter = testParameter;
		this.intValue = intValue;
	}

	@Override
	public String toString() {
		return "TestDto, f.name: " + felhasznalo.getName() + ", p.text: " + testParameter.getText() + ", intValue: "
				+ intValue;
	}
}
