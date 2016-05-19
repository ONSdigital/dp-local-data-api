package gov.ons.local.data.session.geographicarea;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import gov.ons.local.data.entity.DataResource;
import gov.ons.local.data.entity.GeographicArea;
import gov.ons.local.data.entity.GeographicLevelType;
import gov.ons.local.data.entity.Variable;
import gov.ons.local.data.session.AbstractFacade;

public class GeographicAreaFacade extends AbstractFacade<GeographicArea>
{
	@PersistenceContext(unitName = "LocalDataEJB")
	private EntityManager em;

	private Logger logger = Logger
			.getLogger(GeographicAreaFacade.class.getSimpleName());

	public GeographicAreaFacade()
	{
		super(GeographicArea.class);
	}

	@Override
	protected EntityManager getEntityManager()
	{
		return em;
	}

	public GeographicArea findByExtCodeLevel(DataResource dataResource,
			GeographicLevelType geographicLevelType, String extCode)
	{
		logger.log(Level.INFO,
				"findByExtCodeLevel: dataResource = "
						+ dataResource.getDataResource() + " geographicLevelType = "
						+ geographicLevelType.getGeographicLevelType() + " extCode = "
						+ extCode);

		GeographicArea geographicArea = (GeographicArea) getEntityManager()
				.createNamedQuery("GeographicArea.findByExtCodeLevel")
				.setParameter("dataResource", dataResource)
				.setParameter("extCode", extCode)
				.setParameter("geographicLevelType", geographicLevelType)
				.getSingleResult();

		return geographicArea;
	}
	
	public List<String> findByDataResource(DataResource dataResource)
	{
		logger.log(Level.INFO,
				"findByDataResource: dataResource = " + dataResource.getDataResource());

		@SuppressWarnings("unchecked")
		List<String> results = (List<String>) getEntityManager()
				.createNamedQuery("GeographicArea.findByDataResource")
				.setParameter("dataResource", dataResource).getResultList();
		
		return results;
	}
}
