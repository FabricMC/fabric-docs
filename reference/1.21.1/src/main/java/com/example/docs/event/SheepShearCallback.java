package com.example.docs.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.player.Player;
/**
 * Callback for shearing a sheep.
 * Called before the sheep is sheared, items are dropped, and items are damaged.
 * Upon return:
 * - SUCCESS cancels further processing and continues with normal shearing behavior.
 * - PASS falls back to further processing and defaults to SUCCESS if no other listeners are available
 * - FAIL cancels further processing and does not shear the sheep.
 */

// :::
public interface SheepShearCallback {
	Event<SheepShearCallback> EVENT = EventFactory.createArrayBacked(SheepShearCallback.class,
			(listeners) -> (player, sheep) -> {
				for (SheepShearCallback listener : listeners) {
					InteractionResult result = listener.interact(player, sheep);

					if (result != InteractionResult.PASS) {
						return result;
					}
				}

				return InteractionResult.PASS;
			});

	InteractionResult interact(Player player, Sheep sheep);
}
// :::
