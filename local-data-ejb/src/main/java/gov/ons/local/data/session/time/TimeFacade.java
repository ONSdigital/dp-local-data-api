package gov.ons.local.data.session.time;

import java.math.BigInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import gov.ons.local.data.entity.DataResource;

@Stateless
public class TimeFacade
{
	@PersistenceContext(unitName = "LocalDataEJB")
	private EntityManager em;

	private Logger logger = Logger.getLogger(TimeFacade.class.getSimpleName());

	private static String TIME_PERIOD_QUERY = "SELECT t.time_period_id FROM time_period t "
			+ "WHERE t.end_date = " + "(SELECT MAX(t.end_date) FROM "
			+ "(SELECT DISTINCT(ddp.time_period_id) FROM dimensional_data_set dds, dimensional_data_point ddp "
			+ "WHERE dds.dimensional_data_set_id = ddp.dimensional_data_set_id "
			+ "AND dds.data_resource = ?1) x, time_period t "
			+ "WHERE x.time_period_id = t.time_period_id)";

	protected EntityManager getEntityManager()
	{
		return em;
	}

	public BigInteger findLatestTimeByDataResource(DataResource dataResource)
	{
		logger.log(Level.INFO,
				"findLatestTimeByDataResource: dataResource = dataResource");
		
		BigInteger result = (BigInteger) getEntityManager()
				.createNativeQuery(TIME_PERIOD_QUERY)
				.setParameter(1, dataResource.getDataResource()).getSingleResult();

		return result;
	}
}