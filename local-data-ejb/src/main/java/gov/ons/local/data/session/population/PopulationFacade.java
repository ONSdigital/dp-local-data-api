package gov.ons.local.data.session.population;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import gov.ons.local.data.entity.Population;
import gov.ons.local.data.entity.PopulationPK;
import gov.ons.local.data.session.AbstractFacade;

public class PopulationFacade extends AbstractFacade<Population>
{

	@PersistenceContext(unitName = "LocalDataEJB")
	private EntityManager em;

	private Logger logger = Logger
			.getLogger(PopulationFacade.class.getSimpleName());

	public PopulationFacade()
	{
		super(Population.class);
	}

	@Override
	protected EntityManager getEntityManager()
	{
		return em;
	}

	public Population findById(Long timePeriodId, Long geographicAreaId)
	{
		logger.log(Level.INFO, "findById: timePeriodId = " + timePeriodId
				+ " geographicAreaId = " + geographicAreaId);

		PopulationPK id = new PopulationPK();
		id.setGeographicAreaId(geographicAreaId);
		id.setTimePeriodId(timePeriodId);

		Population population = (Population) getEntityManager()
				.createNamedQuery("Population.findById").setParameter("id", id)
				.getSingleResult();

		return population;
	}

}
