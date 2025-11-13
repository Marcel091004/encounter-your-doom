package org.cool.encounteryourdoom;

public class TestDataHelper {


    public static String getCreatureJson(){
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

    public static String getBrokenCreatureJson(){
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

    public static String getEncounterJson(){
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

    public static String getBrokenEncounterJson(){
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

}