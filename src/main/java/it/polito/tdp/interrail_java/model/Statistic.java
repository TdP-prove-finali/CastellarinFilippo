package it.polito.tdp.interrail_java.model;

import java.util.Objects;

public class Statistic {
	
	private String INDIC_UR;
	private Integer V2018;
	private Integer V2019;
	private Integer V2020;
	
	public Statistic(String iNDIC_UR, Integer v2018, Integer v2019, Integer v2020) {
		super();
		INDIC_UR = iNDIC_UR;
		V2018 = v2018;
		V2019 = v2019;
		V2020 = v2020;
	}

	public String getINDIC_UR() {
		return INDIC_UR;
	}

	public Integer getV2018() {
		return V2018;
	}

	public Integer getV2019() {
		return V2019;
	}

	public Integer getV2020() {
		return V2020;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(INDIC_UR);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Statistic other = (Statistic) obj;
		return Objects.equals(INDIC_UR, other.INDIC_UR);
	}

	@Override
	public String toString() {
		return "Statistic [INDIC_UR=" + INDIC_UR + ", V2018=" + V2018 + ", V2019=" + V2019 + ", V2020=" + V2020 + "]";
	}

	public int greaterValue() {
		int max1 = Math.max(V2018, V2019);
		return Math.max(max1, V2020);
	}
}