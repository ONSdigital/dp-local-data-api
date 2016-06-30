package gov.ons.local.data.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the dimensional_data_set database table.
 * 
 */
@Entity
@Table(name = "dimensional_data_set", schema = "onslocal_data")
@NamedQueries({
		@NamedQuery(name = "DimensionalDataSet.findAll", query = "SELECT d FROM DimensionalDataSet d"),
		@NamedQuery(name = "DimensionalDataSet.findById", query = "SELECT d FROM DimensionalDataSet d WHERE d.dimensionalDataSetId = :dimensionalDataSetId"),
		@NamedQuery(name = "DimensionalDataSet.findByDataResource", query = "SELECT d FROM DimensionalDataSet d "
				+ "WHERE d.dataResourceBean=:dataResource")})
public class DimensionalDataSet implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "dimensional_data_set_id")
	private Long dimensionalDataSetId;

	@Column(name = "authentication_role")
	private String authenticationRole;

	private String description;

	private String distribution;

	private String frequency;

	private String identifier;

	private String issued;

	@Column(name = "json_metadata")
	private String jsonMetadata;

	private String keyword;

	private String landingpage;

	private String language;

	private String license;

	private String metadata;

	private String modified;

	private String publisher;

	private String references;

	private String spatial;

	private String temporal;

	private String theme;

	private String title;

	private String source;

	private String contact;

	@Column(name = "release_date")
	private String releaseDate;

	@Column(name = "next_release")
	private String nextRelease;

	// bi-directional many-to-one association to DimensionalDataPoint
	@OneToMany(mappedBy = "dimensionalDataSet")
	private List<DimensionalDataPoint> dimensionalDataPoints;

	// bi-directional many-to-one association to DataResource
	@ManyToOne
	@JoinColumn(name = "data_resource")
	private DataResource dataResourceBean;

	// bi-directional many-to-one association to Presentation
	@OneToMany(mappedBy = "dimensionalDataSet")
	private List<Presentation> presentations;

	public DimensionalDataSet()
	{
	}

	public Long getDimensionalDataSetId()
	{
		return this.dimensionalDataSetId;
	}

	public void setDimensionalDataSetId(Long dimensionalDataSetId)
	{
		this.dimensionalDataSetId = dimensionalDataSetId;
	}

	public String getAuthenticationRole()
	{
		return this.authenticationRole;
	}

	public void setAuthenticationRole(String authenticationRole)
	{
		this.authenticationRole = authenticationRole;
	}

	public String getDescription()
	{
		return this.description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getDistribution()
	{
		return this.distribution;
	}

	public void setDistribution(String distribution)
	{
		this.distribution = distribution;
	}

	public String getFrequency()
	{
		return this.frequency;
	}

	public void setFrequency(String frequency)
	{
		this.frequency = frequency;
	}

	public String getIdentifier()
	{
		return this.identifier;
	}

	public void setIdentifier(String identifier)
	{
		this.identifier = identifier;
	}

	public String getIssued()
	{
		return this.issued;
	}

	public void setIssued(String issued)
	{
		this.issued = issued;
	}

	public String getJsonMetadata()
	{
		return this.jsonMetadata;
	}

	public void setJsonMetadata(String jsonMetadata)
	{
		this.jsonMetadata = jsonMetadata;
	}

	public String getKeyword()
	{
		return this.keyword;
	}

	public void setKeyword(String keyword)
	{
		this.keyword = keyword;
	}

	public String getLandingpage()
	{
		return this.landingpage;
	}

	public void setLandingpage(String landingpage)
	{
		this.landingpage = landingpage;
	}

	public String getLanguage()
	{
		return this.language;
	}

	public void setLanguage(String language)
	{
		this.language = language;
	}

	public String getLicense()
	{
		return this.license;
	}

	public void setLicense(String license)
	{
		this.license = license;
	}

	public String getMetadata()
	{
		return this.metadata;
	}

	public void setMetadata(String metadata)
	{
		this.metadata = metadata;
	}

	public String getModified()
	{
		return this.modified;
	}

	public void setModified(String modified)
	{
		this.modified = modified;
	}

	public String getPublisher()
	{
		return this.publisher;
	}

	public void setPublisher(String publisher)
	{
		this.publisher = publisher;
	}

	public String getReferences()
	{
		return this.references;
	}

	public void setReferences(String references)
	{
		this.references = references;
	}

	public String getSpatial()
	{
		return this.spatial;
	}

	public void setSpatial(String spatial)
	{
		this.spatial = spatial;
	}

	public String getTemporal()
	{
		return this.temporal;
	}

	public void setTemporal(String temporal)
	{
		this.temporal = temporal;
	}

	public String getTheme()
	{
		return this.theme;
	}

	public void setTheme(String theme)
	{
		this.theme = theme;
	}

	public String getTitle()
	{
		return this.title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public List<DimensionalDataPoint> getDimensionalDataPoints()
	{
		return this.dimensionalDataPoints;
	}

	public void setDimensionalDataPoints(
			List<DimensionalDataPoint> dimensionalDataPoints)
	{
		this.dimensionalDataPoints = dimensionalDataPoints;
	}

	public DimensionalDataPoint addDimensionalDataPoint(
			DimensionalDataPoint dimensionalDataPoint)
	{
		getDimensionalDataPoints().add(dimensionalDataPoint);
		dimensionalDataPoint.setDimensionalDataSet(this);

		return dimensionalDataPoint;
	}

	public DimensionalDataPoint removeDimensionalDataPoint(
			DimensionalDataPoint dimensionalDataPoint)
	{
		getDimensionalDataPoints().remove(dimensionalDataPoint);
		dimensionalDataPoint.setDimensionalDataSet(null);

		return dimensionalDataPoint;
	}

	public DataResource getDataResourceBean()
	{
		return this.dataResourceBean;
	}

	public void setDataResourceBean(DataResource dataResourceBean)
	{
		this.dataResourceBean = dataResourceBean;
	}

	public List<Presentation> getPresentations()
	{
		return this.presentations;
	}

	public void setPresentations(List<Presentation> presentations)
	{
		this.presentations = presentations;
	}

	public Presentation addPresentation(Presentation presentation)
	{
		getPresentations().add(presentation);
		presentation.setDimensionalDataSet(this);

		return presentation;
	}

	public Presentation removePresentation(Presentation presentation)
	{
		getPresentations().remove(presentation);
		presentation.setDimensionalDataSet(null);

		return presentation;
	}

	public String getSource()
	{
		return source;
	}

	public void setSource(String source)
	{
		this.source = source;
	}

	public String getContact()
	{
		return contact;
	}

	public void setContact(String contact)
	{
		this.contact = contact;
	}

	public String getReleaseDate()
	{
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate)
	{
		this.releaseDate = releaseDate;
	}

	public String getNextRelease()
	{
		return nextRelease;
	}

	public void setNextRelease(String nextRelease)
	{
		this.nextRelease = nextRelease;
	}
}