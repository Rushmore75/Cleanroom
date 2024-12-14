package net.oliveratkinson.fair;

import net.minecraftforge.common.config.Config.*;
import net.minecraftforge.common.config.Config;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Config(modid = Tags.MOD_ID)
public class Conf {
    // TODO should test time difference of an array vs a hashmap

    @Comment("Mods that all teams get. (whitelist)")
    @Name("Universal Mods")
    @RequiresMcRestart
    public static String[] universalMods = {"minecraft"};

    @Comment("Mods that only a specific team gets. (whitelist per team)")
    @Name("Team Mods")
    @RequiresMcRestart
    public static Map<String, String[]> selectMods = new HashMap<>();

    static {
        selectMods.put("team1", new String[]{"enderio", "othermod"});
        selectMods.put("team2", new String[]{"optb", "optc"});
    }

    @Comment("Player -> Team mapping")
    @Name("Team Assignments")
    @RequiresMcRestart
    public static Map<String, String> teamAssignments = new HashMap<>();

    static {
        teamAssignments.put("31c4910d-9b69-4725-8969-9ed53ac8a7dc", "team2");
    }

}
