package org.cool.encounteryourdoom;

public class TestDataHelper {


    public static String getCreatureJson(){
        return
                """
                        {
                          "id": "b7a9c1e2-4f3d-4e2a-9c1b-7a9c1e24f3d4",
                          "initiative": 12,
                          "name": "Phantasmasaurus",
                          "HP": 222,
                          "AC": 21,
                          "creatureDescription": "A shimmering, ethereal being that wanders between dimensions, leaving rainbows with every step.",
                          "statBlock": {
                            "strength": 18,
                            "dexterity": 22,
                            "constitution": 16,
                            "intelligence": 14,
                            "wisdom": 20,
                            "charisma": 25
                          },
                          "statusEffects": [
                            "Invisible",
                            "Frightened"
                          ],
                          "resistances": [
                            "Fire",
                            "Psychic"
                          ],
                          "immunities": [
                            "Poison"
                          ],
                          "weaknesses": [
                            "Cold"
                          ],
                          "region": [
                            "Forest"
                          ],
                          "rarity": [
                           "Legendary"
                          ],
                          "cr": "18",
                          "speed": 60,
                          "traits": [
                            "Dimensional Gate",
                            "Rainbow Breath",
                            "Mind Shield"
                          ]
                        }
                        """;
    }

    public static String getBrokenCreatureJson(){
        return
                """
                        {
                          "id": "b7a9c1e2-4f3d-4e2a-9c1b-7a9c1e24f3d4",
                          "initiative": 12000,
                          "name": "Phantasmasaurus",
                          "HP": 100000000000000000000000000000,
                          "AC": -50,
                          "creatureDescription": "A shimmering, ethereal being that wanders between dimensions, leaving rainbows with every step.",
                          "statBlock": {
                            "strength": 18,
                            "dexterity": 22,
                            "constitution": 16,
                            "intelligence": 14,
                            "wisdom": 20,
                            "charisma": 25
                          },
                          "statusEffects": [
                            "Invisible",
                            "Frightened"
                          ],
                          "resistances": [
                            "Fire",
                            "Psychic"
                          ],
                          "immunities": [
                            "Poison"
                          ],
                          "weaknesses": [
                            "Cold"
                          ],
                          "region": [
                            "Forest"
                          ],
                          "rarity": [
                           "Legendary"
                          ],
                          "cr": "500000",
                          "speed": 60,
                          "traits": [
                            "Dimensional Gate",
                            "Rainbow Breath",
                            "Mind Shield"
                          ]
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