package gov.ons.local.data.session.variable;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import gov.ons.local.data.entity.DataResource;
import gov.ons.local.data.entity.Variable;
import gov.ons.local.data.session.AbstractFacade;

public class VariableFacade extends AbstractFacade<Variable>
{
	@PersistenceContext(unitName = "LocalDataEJB")
	private EntityManager em;

	private Logger logger = Logger
			.getLogger(VariableFacade.class.getSimpleName());

	public VariableFacade()
	{
		super(Variable.class);
	}

	@Override
	protected EntityManager getEntityManager()
	{
		return em;
	}

	public List<Variable> findByDataResource(DataResource dataResource)
	{
		logger.log(Level.INFO,
				"findByDataResource: dataResource = " + dataResource);

		@SuppressWarnings("unchecked")
		List<Variable> results = (List<Variable>) getEntityManager()
				.createNamedQuery("Variable.findByDataResource")
				.setParameter("dataResource", dataResource).getResultList();

		return results;
	}

}
