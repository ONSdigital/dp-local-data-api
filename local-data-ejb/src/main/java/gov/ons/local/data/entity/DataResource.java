package gov.ons.local.data.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the data_resource database table.
 * 
 */
@Entity
@Table(name = "data_resource", schema="onslocal_data")
@NamedQueries({
		@NamedQuery(name = "DataResource.findAll", query = "SELECT d FROM DataResource d"),
		@NamedQuery(name = "DataResource.findById", query = "SELECT d FROM DataResource d WHERE d.dataResource = :dataResourceId"),
		@NamedQuery(name = "DataResource.searchKeyWord", query = "SELECT d FROM DataResource d WHERE LOWER(d.title) LIKE :searchTerm")})
public class DataResource implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "data_resource")
	private String dataResource;

	private String metadata;

	private String title;

	// bi-directional many-to-many association to Taxonomy
	@ManyToMany
	@JoinTable(name = "data_resource_taxonomy", joinColumns = {
			@JoinColumn(name = "data_resource") }, inverseJoinColumns = {
					@JoinColumn(name = "taxonomy") })
	private List<Taxonomy> taxonomies;

	// bi-directional many-to-one association to DimensionalDataSet
	@OneToMany(mappedBy = "dataResourceBean")
	private List<DimensionalDataSet> dimensionalDataSets;

	public DataResource()
	{
	}

	public String getDataResource()
	{
		return this.dataResource;
	}

	public void setDataResource(String dataResource)
	{
		this.dataResource = dataResource;
	}

	public String getMetadata()
	{
		return this.metadata;
	}

	public void setMetadata(String metadata)
	{
		this.metadata = metadata;
	}

	public String getTitle()
	{
		return this.title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public List<Taxonomy> getTaxonomies()
	{
		return this.taxonomies;
	}

	public void setTaxonomies(List<Taxonomy> taxonomies)
	{
		this.taxonomies = taxonomies;
	}

	public List<DimensionalDataSet> getDimensionalDataSets()
	{
		return this.dimensionalDataSets;
	}

	public void setDimensionalDataSets(
			List<DimensionalDataSet> dimensionalDataSets)
	{
		this.dimensionalDataSets = dimensionalDataSets;
	}

	public DimensionalDataSet addDimensionalDataSet(
			DimensionalDataSet dimensionalDataSet)
	{
		getDimensionalDataSets().add(dimensionalDataSet);
		dimensionalDataSet.setDataResourceBean(this);

		return dimensionalDataSet;
	}

	public DimensionalDataSet removeDimensionalDataSet(
			DimensionalDataSet dimensionalDataSet)
	{
		getDimensionalDataSets().remove(dimensionalDataSet);
		dimensionalDataSet.setDataResourceBean(null);

		return dimensionalDataSet;
	}

}