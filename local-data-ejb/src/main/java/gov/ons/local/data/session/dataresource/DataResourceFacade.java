package gov.ons.local.data.session.dataresource;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import gov.ons.local.data.entity.DataResource;
import gov.ons.local.data.session.AbstractFacade;

public class DataResourceFacade extends AbstractFacade<DataResource>
{

	@PersistenceContext(unitName = "LocalDataEJB")
	private EntityManager em;

	private Logger logger = Logger
			.getLogger(DataResourceFacade.class.getSimpleName());

	public DataResourceFacade()
	{
		super(DataResource.class);
	}

	@Override
	protected EntityManager getEntityManager()
	{
		return em;
	}

	public List<DataResource> searchKeyWord(String searchTerm)
	{
		logger.log(Level.INFO, "searchKeyWord: searchTerm = " + searchTerm);

		@SuppressWarnings("unchecked")
		List<DataResource> results = (List<DataResource>) getEntityManager()
				.createNamedQuery("DataResource.searchKeyWord")
				.setParameter("searchTerm", searchTerm).getResultList();

		return results;
	}

	public DataResource findById(String id)
	{
		logger.log(Level.INFO, "findById: id = " + id);
		
		DataResource dataResource;

		try
		{
			dataResource = (DataResource) getEntityManager()
					.createNamedQuery("DataResource.findById")
					.setParameter("dataResourceId", id).getSingleResult();
		}
		catch (NoResultException nre)
		{
			return null;
		}

		return dataResource;
	}
}
