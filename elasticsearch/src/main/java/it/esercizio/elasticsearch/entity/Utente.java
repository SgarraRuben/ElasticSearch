package it.esercizio.elasticsearch.entity;

public class Utente {

	private String id;
	private String name;
	private String lastname;
	private Integer eta;

	public String getId() {
		return id;

	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Integer getEta() {
		return eta;
	}

	public void setEta(Integer eta) {
		this.eta = eta;
	}

}
