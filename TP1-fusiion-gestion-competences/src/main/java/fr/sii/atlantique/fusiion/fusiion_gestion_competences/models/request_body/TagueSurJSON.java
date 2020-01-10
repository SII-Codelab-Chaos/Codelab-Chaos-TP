package fr.sii.atlantique.fusiion.fusiion_gestion_competences.models.request_body;

import javax.validation.constraints.NotBlank;

public class TagueSurJSON {
	@NotBlank
	private String akTag;

	/**
	 * 
	 */
	public TagueSurJSON() {
		super();
	}

	/**
	 * 
	 * @param akTag
	 */
	public TagueSurJSON(@NotBlank String akTag) {
		super();
		this.akTag = akTag;
	}

	/**
	 * 
	 * @return
	 */
	public String getAkTag() {
		return akTag;
	}

	/**
	 * 
	 * @param akTag
	 */
	public void setAkTag(String akTag) {
		this.akTag = akTag;
	}

	/**
	 * 
	 */
	@Override
	public String toString() {
		return "TagueSurJSON [akCompetence=" + akTag + "]";
	}
	
}
