package gov.ons.local.data.session.geographicleveltype;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import gov.ons.local.data.VariableDTO;
import gov.ons.local.data.entity.Category;
import gov.ons.local.data.entity.GeographicLevelType;
import gov.ons.local.data.entity.Variable;
import gov.ons.local.data.session.AbstractFacade;

public class GeographicLevelTypeFacade
		extends AbstractFacade<GeographicLevelType>
{

	@PersistenceContext(unitName = "LocalDataEJB")
	private EntityManager em;

	private Logger logger = Logger
			.getLogger(GeographicLevelTypeFacade.class.getSimpleName());

	public GeographicLevelTypeFacade()
	{
		super(GeographicLevelType.class);
	}

	@Override
	protected EntityManager getEntityManager()
	{
		return em;
	}

	public GeographicLevelType findById(String id)
	{
		logger.log(Level.INFO, "findById: id = " + id);

		GeographicLevelType geographicLevelType = (GeographicLevelType) getEntityManager()
				.createNamedQuery("GeographicLevelType.findById")
				.setParameter("geographicLevelTypeId", id).getSingleResult();

		return geographicLevelType;
	}
	
	public List<GeographicLevelType> findByIds(Set<String> ids)
	{
		logger.log(Level.INFO,
				"findByIds: ids = " + ids);

		@SuppressWarnings("unchecked")
		List<GeographicLevelType> results = (List<GeographicLevelType>) getEntityManager()
				.createNamedQuery("GeographicLevelType.findByIds")
				.setParameter("geographicLevelTypeIds", ids).getResultList();

		return results;
	}

}
