package gov.ons.local.data.session.presentation;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import gov.ons.local.data.entity.DataResource;
import gov.ons.local.data.entity.Presentation;
import gov.ons.local.data.session.AbstractFacade;

public class PresentationFacade extends AbstractFacade<Presentation>
{
	@PersistenceContext(unitName = "LocalDataEJB")
	private EntityManager em;

	private Logger logger = Logger
			.getLogger(PresentationFacade.class.getSimpleName());

	public PresentationFacade()
	{
		super(Presentation.class);
	}

	@Override
	protected EntityManager getEntityManager()
	{
		return em;
	}

	public List<Presentation> findByDataResource(DataResource dataResource)
	{
		logger.log(Level.INFO,
				"findByDataResource: dataResource = " + dataResource);

		@SuppressWarnings("unchecked")
		List<Presentation> results = (List<Presentation>) getEntityManager()
				.createNamedQuery("Presentation.findByDataResource")
				.setParameter("dataResource", dataResource).getResultList();

		return results;
	}
}
