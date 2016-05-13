package gov.ons.local.data.session.dimensionaldatapoint;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import gov.ons.local.data.entity.DataResource;
import gov.ons.local.data.entity.DimensionalDataPoint;
import gov.ons.local.data.entity.Population;
import gov.ons.local.data.entity.Variable;
import gov.ons.local.data.session.AbstractFacade;
import gov.ons.local.data.session.dataresource.DataResourceFacade;

public class DimensionalDataPointFacade extends AbstractFacade<DimensionalDataPoint>
{

	@PersistenceContext(unitName = "LocalDataEJB")
	private EntityManager em;

	private Logger logger = Logger
			.getLogger(DimensionalDataPointFacade.class.getSimpleName());

	public DimensionalDataPointFacade()
	{
		super(DimensionalDataPoint.class);
	}

	@Override
	protected EntityManager getEntityManager()
	{
		return em;
	}
	
	public Map<Long, DimensionalDataPoint> findByVarGeoTime(Population population, List<Variable> variables)
	{
		logger.log(Level.INFO, "findByVarGeoTime: population = " + population);

		@SuppressWarnings("unchecked")
		List<DimensionalDataPoint> results = (List<DimensionalDataPoint>) getEntityManager()
				.createNamedQuery("DimensionalDataPoint.findByVarGeoTime")
				.setParameter("population", population)
				.setParameter("variables", variables).getResultList();
		
		Map<Long, DimensionalDataPoint> resultMap = new HashMap<>();
		
		for (DimensionalDataPoint ddp : results)
		{
			resultMap.put(ddp.getVariable().getVariableId(), ddp);
		}

		return resultMap;
	}
}
