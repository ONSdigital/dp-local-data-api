package gov.ons.local.data.session.category;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import gov.ons.local.data.entity.Category;
import gov.ons.local.data.entity.DataResource;
import gov.ons.local.data.session.AbstractFacade;

public class CategoryFacade extends AbstractFacade<Category>
{

	@PersistenceContext(unitName = "LocalDataEJB")
	private EntityManager em;

	private Logger logger = Logger
			.getLogger(CategoryFacade.class.getSimpleName());

	public CategoryFacade()
	{
		super(Category.class);
	}

	@Override
	protected EntityManager getEntityManager()
	{
		return em;
	}

	public List<Category> getConceptSytemByDataResource(
			DataResource dataResource)
	{
		logger.log(Level.INFO,
				"getConceptSytemByDataResource: dataResource = " + dataResource);

		@SuppressWarnings("unchecked")
		List<Category> results = (List<Category>) getEntityManager()
				.createNamedQuery("Category.conceptSytemByDataResource")
				.setParameter("dataResource", dataResource).getResultList();

		return results;
	}
}
