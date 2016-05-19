package gov.ons.local.data.resources;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import gov.ons.local.data.DataDTO;
import gov.ons.local.data.VariableDTO;
import gov.ons.local.data.entity.Category;
import gov.ons.local.data.entity.DataResource;
import gov.ons.local.data.entity.GeographicArea;
import gov.ons.local.data.entity.GeographicLevelType;
import gov.ons.local.data.entity.Variable;
import gov.ons.local.data.session.data.DataFacade;
import gov.ons.local.data.session.dataresource.DataResourceFacade;
import gov.ons.local.data.session.geographicarea.GeographicAreaFacade;
import gov.ons.local.data.session.geographicleveltype.GeographicLevelTypeFacade;
import gov.ons.local.data.session.variable.VariableFacade;

@Path("local-data")
public class Resource
{

	private Logger logger = Logger.getLogger(Resource.class.getSimpleName());

	@Inject
	private GeographicLevelTypeFacade geographicLevelTypeFacade;

	@Inject
	private DataResourceFacade dataResourceFacade;

	@Inject
	private VariableFacade variableFacade;

	@Inject
	private GeographicAreaFacade geographicAreaFacade;

	@Inject
	private DataFacade dataFacade;

	@GET
	@Path("/keywordsearch")
	@Produces({ MediaType.APPLICATION_JSON })
	public String keyWordSearch(@QueryParam("searchTerm") String searchTerm)
	{
		// e.g.
		// http://localhost:8080/local-data-web/rs/local-data/keywordsearch?searchTerm=house
		// http://ec2-52-25-128-99.us-west-2.compute.amazonaws.com/local-data-web/rs/local-data/keywordsearch?searchTerm=house

		if (searchTerm == null || searchTerm.length() == 0)
			return "";

		logger.log(Level.INFO, "keyWordSearch: searchTerm = " + searchTerm);

		List<DataResource> results = dataResourceFacade
				.searchKeyWord("%" + searchTerm.toLowerCase().trim() + "%");

		JsonArrayBuilder arrBuilder = Json.createArrayBuilder();

		for (DataResource dr : results)
		{
			arrBuilder.add(Json.createObjectBuilder()
					.add("data_resource", dr.getDataResource())
					.add("title", dr.getTitle()).add("metadata",
							dr.getMetadata() != null ? dr.getMetadata() : ""));
		}

		JsonObject output = Json.createObjectBuilder()
				.add("data_resources", arrBuilder.build()).build();

		return output.toString();
	}

	@GET
	@Path("/dataresource")
	@Produces({ MediaType.APPLICATION_JSON })
	public String findByDataResource(
			@QueryParam("dataResource") String dataResource)
	{
		// e.g.
		// http://localhost:8080/local-data-web/rs/local-data/dataresource?dataResource=G39
		// http://ec2-52-25-128-99.us-west-2.compute.amazonaws.com/local-data-web/rs/local-data/dataresource?dataResource=G39

		// Find the DataResource
		DataResource dr = dataResourceFacade.findById(dataResource);

		if (dr != null)
		{
			logger.log(Level.INFO,
					"findByDataResource: dataResource = " + dataResource);

			List<Variable> results = variableFacade.findByDataResource(dr);

			JsonArrayBuilder arrBuilder = Json.createArrayBuilder();

			for (Variable v : results)
			{
				arrBuilder.add(Json.createObjectBuilder()
						.add("variable_id", v.getVariableId())
						.add("name", v.getName())
						.add("value_domain", v.getValueDomainBean().getValueDomain())
						.add("unit_type", v.getUnitTypeBean().getUnitType()));
			}

			JsonObject output = Json.createObjectBuilder()
					.add("variables", arrBuilder.build()).build();

			return output.toString();
		} else
		{
			return "";
		}
	}

	@GET
	@Path("/extcode")
	@Produces({ MediaType.APPLICATION_JSON })
	public String findByExtCodeLevel(
			@QueryParam("dataResource") String dataResource,
			@QueryParam("extCode") String extCode,
			@QueryParam("geographicLevelType") String geographicLevelType)
	{
		// e.g.
		// http://localhost:8080/local-data-web/rs/local-data/data?dataResource=G39&extCode=E07000087&geographicLevelType=LA&timePeriod=19
		// http://ec2-52-25-128-99.us-west-2.compute.amazonaws.com/local-data-web/rs/local-data/extcode?dataResource=G39&extCode=E07000087&geographicLevelType=LA

		// Find the DataResource
		DataResource dr = dataResourceFacade.findById(dataResource);

		// Find the GeographicLevelType
		GeographicLevelType glt = geographicLevelTypeFacade
				.findById(geographicLevelType);

		if (dr != null && glt != null && extCode != null && extCode.length() > 0)
		{
			GeographicArea result = geographicAreaFacade.findByExtCodeLevel(dr,
					glt, extCode);

			JsonObject output = Json.createObjectBuilder()
					.add("geographic_area_id", result.getGeographicAreaId())
					.add("ext_code", result.getExtCode())
					.add("name", result.getName())
					.add("geographic_level_type", result.getGeographicLevelTypeBean()
							.getGeographicLevelType())
					.build();

			return output.toString();
		} else
		{
			return "";
		}
	}

	@GET
	@Path("/data")
	@Produces({ MediaType.APPLICATION_JSON })
	public String findByExtCodeLevel(
			@QueryParam("dataResource") String dataResource,
			@QueryParam("extCode") String extCode,
			@QueryParam("geographicLevelType") String geographicLevelType,
			@QueryParam("timePeriod") String timePeriod)
	{
		// e.g.
		// http://localhost:8080/local-data-web/rs/local-data/data?dataResource=G39&extCode=E07000087&geographicLevelType=LA&timePeriod=19
		// http://ec2-52-25-128-99.us-west-2.compute.amazonaws.com/local-data-web/rs/local-data/data?dataResource=G39&extCode=E07000087&geographicLevelType=LA&timePeriod=19

		// Check input parameters
		if ((dataResource != null && dataResource.length() > 0)
				&& (extCode != null && extCode.length() > 0)
				&& (geographicLevelType != null && geographicLevelType.length() > 0)
				&& (timePeriod != null && timePeriod.length() > 0))
		{
			List<DataDTO> results = dataFacade.getVariableValues(extCode,
					geographicLevelType, timePeriod, dataResource);

			Map<Long, List<String>> varConceptSysMap = new HashMap<>();

			// Add the variable ids to the Map
			for (DataDTO d : results)
			{
				varConceptSysMap.put(d.getVariableId(), new ArrayList<String>());
			}

			// Get the conceptSystems related to these variables
			List<VariableDTO> variables = variableFacade
					.findByIds(varConceptSysMap.keySet());

			for (VariableDTO v : variables)
			{
				varConceptSysMap.put(v.getId(), v.getConceptSystems());
			}

			if (results != null && results.size() > 0)
			{
				JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
				JsonArrayBuilder conSysArrBuilder = Json.createArrayBuilder();

				for (DataDTO d : results)
				{
					String value = d.getValue() != null ? d.getValue().toString()
							: "";

					for (String s : varConceptSysMap.get(d.getVariableId()))
					{
						conSysArrBuilder.add(s);
					}

					arrBuilder.add(Json.createObjectBuilder()
							.add("variable_id", d.getVariableId())
							.add("name", d.getName())
							.add("value_domain", d.getValueDomain())
							.add("unit_type", d.getUnitType())
							.add("variable_name", d.getVariableName())
							.add("value", value)
							.add("concept_systems", conSysArrBuilder.build()));
				}

				JsonObject output = Json.createObjectBuilder()
						.add("ext_code", results.get(0).getExtCode())
						.add("geographic_area_id",
								results.get(0).getGeographicAreaId())
						.add("variables", arrBuilder.build()).build();

				return output.toString();
			}
		}

		return "";
	}

	@GET
	@Path("/geolevels")
	@Produces({ MediaType.APPLICATION_JSON })
	public String findGeoAreaByDataResource(
			@QueryParam("dataResource") String dataResource)
	{
		// e.g.
		// http://localhost:8080/local-data-web/rs/local-data/geolevels?dataResource=G39
		// http://ec2-52-25-128-99.us-west-2.compute.amazonaws.com/local-data-web/rs/local-data/geolevels?dataResource=G39

		// Find the DataResource
		DataResource dr = dataResourceFacade.findById(dataResource);

		if (dr != null)
		{
			List<String> results = geographicAreaFacade
					.findByDataResource(dr);

			JsonArrayBuilder arrBuilder = Json.createArrayBuilder();

			for (String s : results)
			{
				arrBuilder.add(s);
			}
			
			JsonObject output = Json.createObjectBuilder()
					.add("geographic_level_type", arrBuilder.build()).build();

			return output.toString();
		} else
		{
			return "";
		}
	}
}
