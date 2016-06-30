package gov.ons.local.data.session.dimensionaldataset;

import java.math.BigInteger;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import gov.ons.local.data.entity.DataResource;
import gov.ons.local.data.entity.DimensionalDataSet;
import gov.ons.local.data.session.AbstractFacade;

public class DimensionalDataSetFacade extends AbstractFacade<DimensionalDataSet>
{

	@PersistenceContext(unitName = "LocalDataEJB")
	private EntityManager em;

	private Logger logger = Logger
			.getLogger(DimensionalDataSetFacade.class.getSimpleName());
	
	private static String LATEST_DIMENSIONAL_DATASET = "SELECT DISTINCT(dds.dimensional_data_set_id) "
			+ "FROM dimensional_data_set dds, dimensional_data_point ddp "
			+ "WHERE dds.dimensional_data_set_id = ddp.dimensional_data_set_id "
			+ "AND dds.data_resource = ?1 "
			+ "AND ddp.time_period_id  = ?2";

	public DimensionalDataSetFacade()
	{
		super(DimensionalDataSet.class);
	}

	@Override
	protected EntityManager getEntityManager()
	{
		return em;
	}
	
	public List<DimensionalDataSet> findByDataResource(DataResource dataResource)
	{
		logger.log(Level.INFO, "findByDataResource: dataResource = " + dataResource);

		@SuppressWarnings("unchecked")
		List<DimensionalDataSet> results = (List<DimensionalDataSet>) getEntityManager()
				.createNamedQuery("DimensionalDataSet.findByDataResource")
				.setParameter("dataResource", dataResource).getResultList();

		return results;
	}
	
	public DimensionalDataSet findLatestByDataResource(DataResource dataResource, BigInteger timePeriod)
	{
		logger.log(Level.INFO,
				"findLatestDataResource: dataResource = dataResource");
		
		BigInteger dimensionalDataSetId = (BigInteger) getEntityManager()
				.createNativeQuery(LATEST_DIMENSIONAL_DATASET)
				.setParameter(1, dataResource.getDataResource())
				.setParameter(2, timePeriod).getSingleResult();
		
		DimensionalDataSet dimensionalDataSet;
		
		if (dimensionalDataSetId != null)
		{
			dimensionalDataSet = (DimensionalDataSet) getEntityManager()
					.createNamedQuery("DimensionalDataSet.findById")
					.setParameter("dimensionalDataSetId", dimensionalDataSetId.longValue()).getSingleResult();
			
			return dimensionalDataSet;
		}
		else
		{
			return null;
		}	
	} 
}
