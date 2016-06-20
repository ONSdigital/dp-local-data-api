package gov.ons.local.data.session.dimensionaldataset;

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
}
