package gov.ons.local.data;

import java.util.List;

public class VariableDTO
{
	private Long id;
	private List<String> conceptSystems;
	
	public VariableDTO(Long id, List<String> conceptSystems)
	{
		super();
		this.id = id;
		this.conceptSystems = conceptSystems;
	}
	
	public Long getId()
	{
		return id;
	}
	
	public void setId(Long id)
	{
		this.id = id;
	}
	
	public List<String> getConceptSystems()
	{
		return conceptSystems;
	}
	
	public void setConceptSystems(List<String> conceptSystems)
	{
		this.conceptSystems = conceptSystems;
	}
}
