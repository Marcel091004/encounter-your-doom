package org.cool.encounteryourdoom;

import java.util.List;

public class TestDataHelper {


	public static String getCreatureJson() {
		return
				"""
						{
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
						  }
						
						""";
	}

	public static List<String> getListOfCreatureJson() {
		return List.of("""
						{
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
						  }
						
						""",
				"""
						{
						    "Id": "660e8400-e29b-41d4-a716-446655440000",
						    "initiative": 15,
						    "name": "Goblin",
						    "HP": 30,
						    "AC": 15,
						    "CreatureDescription": "A small, green humanoid.",
						    "statBlock": {
						      "Str": 8,
						      "Dex": 14,
						      "Con": 10,
						      "Int": 10,
						      "Wis": 8,
						      "Cha": 8
						    },
						    "statusEffects": [],
						    "resistances": [],
						    "immunities": [],
						    "weaknesses": [],
						    "region": ["Arctic"],
						    "rarity": ["Common"],
						    "cr": "1/4",
						    "Speed": 30,
						    "traits": ["Nimble Escape"],
						    "attack": []
						  }
						""",
				"""
						{
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
						  }
						""",
				"""
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
						""");
	}

	public static String getBrokenCreatureJson() {
		return
				"""
						{
						    "Id": "550e8400-e29b-41d4-a716-446655440000",
						    "initiative": 56787657657,
						    "name": "Phantasmasaurus",
						    "HP": 100000000000000000000000000000000,
						    "AC": -567656765765,
						    "CreatureDescription": "Beschreibung...",
						    "statBlock": {
						      "Str": 40,
						      "Dex": -789878789768768767876545677654678765587654,
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
						    "cr": "4000000 Millionen",
						    "Speed": 30,
						    "traits": ["Dimensional Gate", "Rainbow Breath", "Mind Shield"],
						    "attack": []
						  }
						
						""";
	}

	public static String getEncounterJson() {
		return
				"""
						{
						  "name": "plötzlich war da eine Wand 3",
						  "Id": "239327ca-862f-4488-b245-e9f299cd77cd",
						  "creatures": [
						  	"b7a9c1e2-4f3d-4e2a-9c1b-7a9c1e24f3d4"
						  ],
						  "region": "Forest",
						  "partyLevel": 5,
						  "difficultyLevel": "Hard",
						  "description": "aus Backsteinen und wirklich echt hart 😥",
						  "reward": "gebrochene Nase"
						}
						""";
	}

	public static String getBrokenEncounterJson() {
		return
				"""
						{
						  "name": "plötzlich war da eine Wand 3",
						  "Id": "239327ca-862f-4488-b245-e9f299cd77cd",
						  "creatures": [
						  	"b7a9c1e2-4f3d-4e2a-9c1b-7a9c1e24f3d4"
						  ],
						  "region": "Wald",
						  "partyLevel": -5000000,
						  "difficultyLevel": "Mission Impossible",
						  "description": "aus Backsteinen und wirklich echt hart 😥",
						  "reward": "gebrochene Nase"
						}
						""";
	}

	public static String getPrivateEncounterJson() {
		//Die userId darf NICHT verändert werden, da sie sonst in den Tests nicht mehr gefunden wird
		return
				"""
						{
							"userId": "2511c53f-3e19-4c31-b153-ece0817eb2b8",
							"name": "plötzlich war da eine Wand 3",
							"Id": "239327ca-862f-4488-b245-e9f299cd77cd",
							"creatures": [
								"b7a9c1e2-4f3d-4e2a-9c1b-7a9c1e24f3d4"
							],
							"region": "Forest",
							"partyLevel": -5000000,
							"difficultyLevel": "Hard",
							"description": "aus Backsteinen und wirklich echt hart 😥",
							"reward": "gebrochene Nase"
						}
						""";
	}

}
