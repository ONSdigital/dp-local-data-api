package gov.ons.local.data.session.variable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import gov.ons.local.data.VariableDTO;
import gov.ons.local.data.entity.Category;
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
	
	public List<VariableDTO> findByIds(Set<Long> ids)
	{
		logger.log(Level.INFO,
				"findByIds: ids = " + ids);

		@SuppressWarnings("unchecked")
		List<Variable> results = (List<Variable>) getEntityManager()
				.createNamedQuery("Variable.findByIds")
				.setParameter("variables", ids).getResultList();
		
		List<VariableDTO> variables = new ArrayList<>();
		List<String> conceptSystems;
		
		for (Variable v : results)
		{
			conceptSystems = new ArrayList<>();
			
			for (Category c : v.getCategories())
			{
				conceptSystems.add(c.getConceptSystemBean().getConceptSystem());
			}
			
			variables.add(new VariableDTO(v.getVariableId(), conceptSystems));
		}

		return variables;
	}

	public Collection<Category> getConceptSystemByVariables(List<Variable> variables)
	{
		logger.log(Level.INFO,
				"getConceptSystemByVariables: variables = " + variables);
		
		Set<Long> ids = new HashSet<>();
		
		for (Variable v : variables)
		{
			ids.add(v.getVariableId());
		}
		
		@SuppressWarnings("unchecked")
		List<Variable> results = (List<Variable>) getEntityManager()
				.createNamedQuery("Variable.findByIds")
				.setParameter("variables", ids).getResultList();
		
		Map<Long, Category> categories = new HashMap<>();
		
		for (Variable v : results)
		{
			for (Category c : v.getCategories())
			{
				categories.put(c.getCategoryId(), c);
			}
		}
		
		return categories.values();
	}
}
