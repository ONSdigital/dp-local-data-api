package gov.ons.local.data;

import java.math.BigDecimal;

public class DataDTO
{
	private Long geographicAreaId;
	private String extCode;
	private String name;
	private String geographicLevelType;
	private Long variableId;
	private String valueDomain;
	private String unitType;
	private String variableName;
	private BigDecimal value;

	public DataDTO(Long geographicAreaId, String extCode, String name,
			String geographicLevelType, Long variableId, String valueDomain,
			String unitType, String variableName, BigDecimal value)
	{
		super();
		this.geographicAreaId = geographicAreaId;
		this.extCode = extCode;
		this.name = name;
		this.geographicLevelType = geographicLevelType;
		this.variableId = variableId;
		this.valueDomain = valueDomain;
		this.unitType = unitType;
		this.variableName = variableName;
		this.value = value;
	}

	public String getValueDomain()
	{
		return valueDomain;
	}

	public void setValueDomain(String valueDomain)
	{
		this.valueDomain = valueDomain;
	}

	public String getUnitType()
	{
		return unitType;
	}

	public void setUnitType(String unitType)
	{
		this.unitType = unitType;
	}

	public Long getGeographicAreaId()
	{
		return geographicAreaId;
	}

	public void setGeographicAreaId(Long geographicAreaId)
	{
		this.geographicAreaId = geographicAreaId;
	}

	public String getExtCode()
	{
		return extCode;
	}

	public void setExtCode(String extCode)
	{
		this.extCode = extCode;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getGeographicLevelType()
	{
		return geographicLevelType;
	}

	public void setGeographicLevelType(String geographicLevelType)
	{
		this.geographicLevelType = geographicLevelType;
	}

	public Long getVariableId()
	{
		return variableId;
	}

	public void setVariableId(Long variableId)
	{
		this.variableId = variableId;
	}

	public String getVariableName()
	{
		return variableName;
	}

	public void setVariableName(String variableName)
	{
		this.variableName = variableName;
	}

	public BigDecimal getValue()
	{
		return value;
	}

	public void setValue(BigDecimal value)
	{
		this.value = value;
	}
}
