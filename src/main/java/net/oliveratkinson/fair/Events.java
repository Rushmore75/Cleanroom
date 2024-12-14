package net.oliveratkinson.fair;

import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Arrays;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = Tags.MOD_ID)
public class Events {

    // This event doesn't cover entering a GUI, this is just about the item in the player's hand
    @SubscribeEvent
    public static void handler(PlayerInteractEvent event) {
        if (event.isCancelable() && !event.isCanceled()) {
            var player = event.getEntityPlayer();
            var uuid = player.getGameProfile().getId();

            var itemCancel = false;
            var blockCancel = false;

            // ITEM logic
            var item = event.getItemStack().getItem().getRegistryName();
            if (item != null) {
                var namespace = item.getNamespace();
                itemCancel = shouldCancel(uuid, namespace);
            }

            // BLOCK logic
            var block = event.getWorld().getBlockState(event.getPos()).getBlock().getRegistryName();
            if (block != null) {
                var namespace = block.getNamespace();
                blockCancel = shouldCancel(uuid, namespace);
            }

            event.setCanceled(blockCancel || itemCancel);
            Main.LOGGER.info(blockCancel || itemCancel);
        }
    }

    public static boolean shouldCancel(UUID uuid, String namespace) {
        // If the player isn't in a team, we basically put them in adventure mode.
        // This will force them to use the system.
        var team = Conf.teamAssignments.get(uuid.toString());
        if (team == null) {
            return true;
        }

        // Mod not found, should've configured it. Cancel event.
        var mods = Conf.selectMods.get(team);
        if (mods == null) {
            return true;
        }

        // If either array contains the value then don't cancel
        return !(
                Arrays.asList(Conf.universalMods).contains(namespace)
                        || Arrays.asList(mods).contains(namespace)
        );
    }
}
