package gov.ons.local.data.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the geographic_level_type database table.
 * 
 */
@Entity
@Table(name="geographic_level_type")
@NamedQueries({
	@NamedQuery(name="GeographicLevelType.findAll", query="SELECT g FROM GeographicLevelType g"),
	@NamedQuery(name = "GeographicLevelType.findById", query = "SELECT g FROM GeographicLevelType g WHERE g.geographicLevelType = :geographicLevelTypeId")})
public class GeographicLevelType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="geographic_level_type")
	private String geographicLevelType;

	private String metadata;

	//bi-directional many-to-one association to GeographicArea
	@OneToMany(mappedBy="geographicLevelTypeBean")
	private List<GeographicArea> geographicAreas;

	public GeographicLevelType() {
	}

	public String getGeographicLevelType() {
		return this.geographicLevelType;
	}

	public void setGeographicLevelType(String geographicLevelType) {
		this.geographicLevelType = geographicLevelType;
	}

	public String getMetadata() {
		return this.metadata;
	}

	public void setMetadata(String metadata) {
		this.metadata = metadata;
	}

	public List<GeographicArea> getGeographicAreas() {
		return this.geographicAreas;
	}

	public void setGeographicAreas(List<GeographicArea> geographicAreas) {
		this.geographicAreas = geographicAreas;
	}

	public GeographicArea addGeographicArea(GeographicArea geographicArea) {
		getGeographicAreas().add(geographicArea);
		geographicArea.setGeographicLevelTypeBean(this);

		return geographicArea;
	}

	public GeographicArea removeGeographicArea(GeographicArea geographicArea) {
		getGeographicAreas().remove(geographicArea);
		geographicArea.setGeographicLevelTypeBean(null);

		return geographicArea;
	}

}