package gov.ons.local.data.session.data;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import gov.ons.local.data.DataDTO;

@Stateless
public class DataFacade
{
	@PersistenceContext(unitName = "LocalDataEJB")
	private EntityManager em;

	private Logger logger = Logger.getLogger(DataFacade.class.getSimpleName());

	// private static String VARIABLE_VALUE_QUERY = "SELECT ga.geographicAreaId,
	// ga.extCode, ga.name, ga.geographicLevelTypeBean.geographicLevelType,
	// v.variableId, v.valueDomainBean.valueDomain, v.unitTypeBean.unitType,
	// v.name, d.value FROM DimensionalDataPoint d LEFT JOIN d.population p LEFT
	// JOIN
	// p.geographicArea ga LEFT JOIN d.variable v WHERE ga.extCode = :extCode AND
	// ga.geographicLevelTypeBean.geographicLevelType = :geographicLevelTypeId
	// AND d.population.timePeriod.timePeriodId = :timePeriodId AND EXISTS
	// (SELECT dds.dimensionalDataSetId FROM DimensionalDataSet dds
	// JOIN dds.dimensionalDataPoints ddp WHERE
	// ddp.population.id.geographicAreaId = ga.geographicAreaId AND
	// dds.dataResourceBean.dataResource = :dataResource) AND EXISTS (SELECT
	// dds.dimensionalDataSetId FROM DimensionalDataSet dds
	// JOIN dds.dimensionalDataPoints ddp WHERE ddp.id.variableId = v.variableId
	// AND dds.dataResourceBean.dataResource = :dataResource)";

	private static String VARIABLE_VALUE_QUERY = "SELECT ga.geographic_area_id, ga.ext_code, ga.name, ga.geographic_level_type, v.variable_id, v.value_domain, "
			+ "v.unit_type, v.name AS variable_name, d.value "
			+ "FROM geographic_area ga, dimensional_data_point d, variable v "
			+ "WHERE ga.geographic_area_id = d.geographic_area_id and d.variable_id = v.variable_id "
			+ "AND EXISTS (SELECT 1 FROM dimensional_data_set dds, dimensional_data_point ddp "
			+ "WHERE dds.dimensional_data_set_id = ddp.dimensional_data_set_id "
			+ "AND ddp.geographic_area_id = ga.geographic_area_id AND dds.data_resource = ?1) "
			+ "AND EXISTS (SELECT 1 FROM dimensional_data_set dds, dimensional_data_point ddp "
			+ "WHERE dds.dimensional_data_set_id = ddp.dimensional_data_set_id "
			+ "AND ddp.variable_id = v.variable_id AND dds.data_resource = ?2) "
			+ "AND ga.ext_code = ?3 AND ga.geographic_level_type = ?4 AND d.time_period_id = ?5 ORDER BY v.variable_id";

	protected EntityManager getEntityManager()
	{
		return em;
	}

	public List<DataDTO> getVariableValues(String extCode,
			String geographicLevelType, String timePeriodId, String dataResourceId)
	{
		@SuppressWarnings("unchecked")
		List<DataDTO> results = (List<DataDTO>) getEntityManager()
				.createNativeQuery(VARIABLE_VALUE_QUERY, "DataTableResult")
				.setParameter(1, dataResourceId).setParameter(2, dataResourceId)
				.setParameter(3, extCode).setParameter(4, geographicLevelType)
				.setParameter(5, new Long(timePeriodId)).getResultList();
		
		return results;
	}
}