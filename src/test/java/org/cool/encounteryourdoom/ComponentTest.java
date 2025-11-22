package org.cool.encounteryourdoom;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.cool.encounteryourdoom.model.CreatureEntity;
import org.cool.encounteryourdoom.repository.CreatureRepository;
import org.cool.encounteryourdoom.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.openapitools.model.Rarity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.context.ImportTestcontainers;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.cool.encounteryourdoom.TestDataHelper.getCreatureJson;
import static org.cool.encounteryourdoom.TestDataHelper.getListOfCreatureJson;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@ImportTestcontainers(MongoDBTestContainer.class)
@AutoConfigureMockMvc
public class ComponentTest {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private CreatureRepository creatureRepository;

	@BeforeEach
	void cleanUp() {
		creatureRepository.deleteAll();
		userRepository.deleteAll();
	}


	@Nested
	class User {

		@Test
		void callUserGenerationShouldReturnValidUserId() throws Exception {
			MvcResult result = mockMvc.perform(post("/datev/v1/user/generation"))
					.andExpect(status().isOk())
					.andReturn();

			String contentAsString = result.getResponse().getContentAsString();

			contentAsString = contentAsString.replace("\"", "");

			UUID userId = UUID.fromString(contentAsString);

			Assertions.assertNotNull(userId);
			Assertions.assertFalse(userRepository.findAllByUserId(userId).isEmpty());

		}
	}

	@Nested
	class Creature {

		@Nested
		class getCreature {

			@Test
			void shouldReturn200OkWithAllCreatures() throws Exception {

				getListOfCreatureJson().forEach(creature -> {
					try {
						CreatureEntity creatureEntity = objectMapper.readValue(creature, CreatureEntity.class);
						creatureRepository.save(creatureEntity);
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				});

				MvcResult result = mockMvc.perform(get("/datev/v1/creature"))
						.andExpect(status().isOk())
						.andReturn();

				String contentAsString = result.getResponse().getContentAsString();
				List<CreatureEntity> resultList = objectMapper.readValue(contentAsString, new TypeReference<List<CreatureEntity>>() {
				});

				Assertions.assertEquals(resultList, creatureRepository.findAll());

			}

			@Test
			void shouldReturn200OkWithEmptyList() throws Exception {

				MvcResult result = mockMvc.perform(get("/datev/v1/creature"))
						.andExpect(status().isOk())
						.andReturn();

				String contentAsString = result.getResponse().getContentAsString();

				Assertions.assertEquals("[]", contentAsString);

			}

			@Test
			void shouldReturn200OkWithAllCreaturesWithTheParameterRegionSetToForest() throws Exception {

				getListOfCreatureJson().forEach(creature -> {
					try {
						CreatureEntity creatureEntity = objectMapper.readValue(creature, CreatureEntity.class);
						creatureRepository.save(creatureEntity);
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				});

				MvcResult result = mockMvc.perform(get("/datev/v1/creature?region=FOREST"))
						.andExpect(status().isOk())
						.andReturn();

				String contentAsString = result.getResponse().getContentAsString();
				List<CreatureEntity> resultList = objectMapper.readValue(contentAsString, new TypeReference<List<CreatureEntity>>() {
				});


				String expectedContentAsString = """
						[{
						    "Id": "550e8400-e29b-41d4-a716-446655440000",
						    "initiative": 12,
						    "name": "Phantasmasaurus",
						    "HP": 222,
						    "AC": 21,
						    "CreatureDescription": "Beschreibung...",
						    "statBlock": {
						      "Str": 5,
						      "Dex": 5,
						      "Con": 5,
						      "Int": 5,
						      "Wis": 5,
						      "Cha": 5
						    },
						    "statusEffects": ["Invisible", "Frightened"],
						    "resistances": ["Fire", "Psychic"],
						    "immunities": ["Poison"],
						    "weaknesses": ["Cold"],
						    "region": ["Forest"],
						    "rarity": ["Legendary"],
						    "cr": "18",
						    "Speed": 30,
						    "traits": ["Dimensional Gate", "Rainbow Breath", "Mind Shield"],
						    "attack": []
						  }]
						
						""";

				List<CreatureEntity> expectedList = objectMapper.readValue(expectedContentAsString, new TypeReference<List<CreatureEntity>>() {
				});


				Assertions.assertEquals(expectedList, resultList);

			}

			@Test
			void shouldReturn200OkWithAllCreaturesWithTheParameterRaritySetToLegendary() throws Exception {

				getListOfCreatureJson().forEach(creature -> {
					try {
						CreatureEntity creatureEntity = objectMapper.readValue(creature, CreatureEntity.class);
						creatureRepository.save(creatureEntity);
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				});

				MvcResult result = mockMvc.perform(get("/datev/v1/creature?rarity=LEGENDARY"))
						.andExpect(status().isOk())
						.andReturn();

				String contentAsString = result.getResponse().getContentAsString();
				List<CreatureEntity> resultList = objectMapper.readValue(contentAsString, new TypeReference<List<CreatureEntity>>() {
				});


				String expectedContentAsString = """
						[{
						    "Id": "550e8400-e29b-41d4-a716-446655440000",
						    "initiative": 12,
						    "name": "Phantasmasaurus",
						    "HP": 222,
						    "AC": 21,
						    "CreatureDescription": "Beschreibung...",
						    "statBlock": {
						      "Str": 5,
						      "Dex": 5,
						      "Con": 5,
						      "Int": 5,
						      "Wis": 5,
						      "Cha": 5
						    },
						    "statusEffects": ["Invisible", "Frightened"],
						    "resistances": ["Fire", "Psychic"],
						    "immunities": ["Poison"],
						    "weaknesses": ["Cold"],
						    "region": ["Forest"],
						    "rarity": ["Legendary"],
						    "cr": "18",
						    "Speed": 30,
						    "traits": ["Dimensional Gate", "Rainbow Breath", "Mind Shield"],
						    "attack": []
						  }]
						
						""";

				List<CreatureEntity> expectedList = objectMapper.readValue(expectedContentAsString, new TypeReference<List<CreatureEntity>>() {
				});


				Assertions.assertEquals(expectedList, resultList);

			}

			@Test
			void shouldReturn200OkWithAllCreaturesWithTheParameterCRSetTo10() throws Exception {

				getListOfCreatureJson().forEach(creature -> {
					try {
						CreatureEntity creatureEntity = objectMapper.readValue(creature, CreatureEntity.class);
						creatureRepository.save(creatureEntity);
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				});

				MvcResult result = mockMvc.perform(get("/datev/v1/creature?cr=10"))
						.andExpect(status().isOk())
						.andReturn();

				String contentAsString = result.getResponse().getContentAsString();
				List<CreatureEntity> resultList = objectMapper.readValue(contentAsString, new TypeReference<List<CreatureEntity>>() {
				});


				String expectedContentAsString = """
						[{
						    "Id": "770e8400-e29b-41d4-a716-446655440000",
						    "initiative": 20,
						    "name": "Dragon",
						    "HP": 300,
						    "AC": 19,
						    "CreatureDescription": "A large, fire-breathing reptile.",
						    "statBlock": {
						      "Str": 23,
						      "Dex": 10,
						      "Con": 21,
						      "Int": 16,
						      "Wis": 13,
						      "Cha": 19
						    },
						    "statusEffects": [],
						    "resistances": ["Fire"],
						    "immunities": [],
						    "weaknesses": [],
						    "region": ["Mountain"],
						    "rarity": ["Rare"],
						    "cr": "10",
						    "Speed": 40,
						    "traits": ["Fire Breath", "Legendary Resistance"],
						    "attack": []
						  },
						  {
						   	"Id": "770e8400-e29b-41d4-a716-446655440001",
						   	"initiative": 30,
						   	"name": "Blue Dragon",
						   	"HP": 250,
						   	"AC": 19,
						   	"CreatureDescription": "A large, steaming-hot-water-breathing reptile.",
						   	"statBlock": {
						   	  "Str": 23,
						   	  "Dex": 10,
						   	  "Con": 21,
						   	  "Int": 16,
						   	  "Wis": 13,
						   	  "Cha": 19
						   	},
						   	"statusEffects": [],
						   	"resistances": ["Fire"],
						   	"immunities": [],
						   	"weaknesses": [],
						   	"region": ["Mountain"],
						   	"rarity": ["Rare"],
						   	"cr": "10",
						   	"Speed": 40,
						   	"traits": ["Fire Breath", "Legendary Resistance"],
						   	"attack": []
						    }
						  ]]
						
						""";

				List<CreatureEntity> expectedList = objectMapper.readValue(expectedContentAsString, new TypeReference<List<CreatureEntity>>() {
				});


				Assertions.assertEquals(expectedList, resultList);

			}
		}

		@Test
		void shouldReturn200OkWithAllCreaturesWithMultipleParameters() throws Exception {

			getListOfCreatureJson().forEach(creature -> {
				try {
					CreatureEntity creatureEntity = objectMapper.readValue(creature, CreatureEntity.class);
					creatureRepository.save(creatureEntity);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			});

			MvcResult result = mockMvc.perform(get("/datev/v1/creature?cr=10&region=MOUNTAIN&rarity=RARE"))
					.andExpect(status().isOk())
					.andReturn();

			String contentAsString = result.getResponse().getContentAsString();
			List<CreatureEntity> resultList = objectMapper.readValue(contentAsString, new TypeReference<List<CreatureEntity>>() {
			});


			String expectedContentAsString = """
					[{
					    "Id": "770e8400-e29b-41d4-a716-446655440000",
					    "initiative": 20,
					    "name": "Dragon",
					    "HP": 300,
					    "AC": 19,
					    "CreatureDescription": "A large, fire-breathing reptile.",
					    "statBlock": {
					      "Str": 23,
					      "Dex": 10,
					      "Con": 21,
					      "Int": 16,
					      "Wis": 13,
					      "Cha": 19
					    },
					    "statusEffects": [],
					    "resistances": ["Fire"],
					    "immunities": [],
					    "weaknesses": [],
					    "region": ["Mountain"],
					    "rarity": ["Rare"],
					    "cr": "10",
					    "Speed": 40,
					    "traits": ["Fire Breath", "Legendary Resistance"],
					    "attack": []
					  },
					
					                      {
					                          "Id": "770e8400-e29b-41d4-a716-446655440001",
					                          "initiative": 30,
					                          "name": "Blue Dragon",
					                          "HP": 250,
					                          "AC": 19,
					                          "CreatureDescription": "A large, steaming-hot-water-breathing reptile.",
					                          "statBlock": {
					                            "Str": 23,
					                            "Dex": 10,
					                            "Con": 21,
					                            "Int": 16,
					                            "Wis": 13,
					                            "Cha": 19
					                          },
					                          "statusEffects": [],
					                          "resistances": ["Fire"],
					                          "immunities": [],
					                          "weaknesses": [],
					                          "region": ["Mountain"],
					                          "rarity": ["Rare"],
					                          "cr": "10",
					                          "Speed": 40,
					                          "traits": ["Fire Breath", "Legendary Resistance"],
					                          "attack": []
					                        }
					                      ]
					
					""";

			List<CreatureEntity> expectedList = objectMapper.readValue(expectedContentAsString, new TypeReference<List<CreatureEntity>>() {
			});


			Assertions.assertEquals(expectedList, resultList);

		}
	}

	@Nested
	class getCreatureById {

		@Test
		void shouldReturn200OkWithTheCreature() throws Exception {

			CreatureEntity creatureEntity = objectMapper.readValue(getCreatureJson(), CreatureEntity.class);
			creatureRepository.save(creatureEntity);


			MvcResult result = mockMvc.perform(get("/datev/v1/creature/" + creatureEntity.getId()))
					.andExpect(status().isOk())
					.andReturn();

			String contentAsString = result.getResponse().getContentAsString();
			CreatureEntity resultEntity = objectMapper.readValue(contentAsString, CreatureEntity.class);

			Assertions.assertEquals(resultEntity, creatureRepository.findById(creatureEntity.getId()).orElse(null));

		}

		//TODO this is not really the desired behavior, should return 404
		@Test
		void shouldReturn200OkWithEmptyBody() throws Exception {

			MvcResult result = mockMvc.perform(get("/datev/v1/creature/" + UUID.randomUUID()))
					.andExpect(status().isOk())
					.andReturn();

			String contentAsString = result.getResponse().getContentAsString();

			Assertions.assertEquals("", contentAsString);

		}
	}

	@Nested
	class updateCreatureById {

		@Test
		void shouldReturn204NoContent() throws Exception {

			CreatureEntity creatureEntity = objectMapper.readValue(getCreatureJson(), CreatureEntity.class);

			creatureEntity.setRarity(Collections.singletonList(Rarity.COMMON));

			creatureRepository.save(creatureEntity);

			mockMvc.perform(put("/datev/v1/creature/" + creatureEntity.getId())
							.contentType(MediaType.APPLICATION_JSON)
							.content(getCreatureJson()))
					.andExpect(status().isNoContent());

			CreatureEntity expectedEntity = objectMapper.readValue(getCreatureJson(), CreatureEntity.class);


			Assertions.assertEquals(creatureRepository.findById(creatureEntity.getId()).orElse(null), expectedEntity);
		}
	}

	@Nested
	class createCreature {

		@Test
		void shouldReturn201CreatedWithLocationHeader() throws Exception {

			MvcResult result = mockMvc.perform(post("/datev/v1/creature")
							.contentType(MediaType.APPLICATION_JSON)
							.content(getCreatureJson()))
					.andExpect(status().isCreated()).andReturn();

			String locationHeader = result.getResponse().getHeader("Location");
			Assertions.assertNotNull(locationHeader);

			locationHeader = locationHeader.replace("\"", "");
			locationHeader = locationHeader.replace("/datev/v1/creature/", "");

			UUID creatureId = UUID.fromString(locationHeader);
			Assertions.assertNotNull(creatureId);

			CreatureEntity expectedEntity = objectMapper.readValue(getCreatureJson(), CreatureEntity.class);
			CreatureEntity actualEntity = creatureRepository.findById(creatureId).orElse(null);

			Assertions.assertNotNull(actualEntity);

			//The Id is generated anew, so we need to set it here for the comparison or else the Ids won't match
			expectedEntity.setId(actualEntity.getId());

			Assertions.assertEquals(expectedEntity, actualEntity);
		}
	}
}

