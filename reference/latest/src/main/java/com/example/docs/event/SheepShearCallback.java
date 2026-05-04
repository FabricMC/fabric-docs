package com.example.docs.event;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.animal.sheep.Sheep;
import net.minecraft.world.entity.player.Player;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

// #region javadoc_comment
/**
 * Callback for shearing a sheep.
 * Called before the sheep is sheared, items are dropped, and items are damaged.
 * Upon return:
 * - SUCCESS cancels further processing and continues with normal shearing behavior.
 * - PASS falls back to further processing and defaults to SUCCESS if no other listeners are available
 * - FAIL cancels further processing and does not shear the sheep.
 */
// #endregion javadoc_comment

// #region sheep_shear_callback
public interface SheepShearCallback {
	Event<SheepShearCallback> EVENT = EventFactory.createArrayBacked(SheepShearCallback.class,
			// #region listener_iterator
			(listeners) -> (player, sheep) -> {
				for (SheepShearCallback listener : listeners) {
			    // #endregion listener_iterator
					InteractionResult result = listener.interact(player, sheep);

					// #region return_value
					if (result != InteractionResult.PASS) {
						return result;
					}
				}

				return InteractionResult.PASS;
				// #endregion return_value
			});

	// #region interact_method
	InteractionResult interact(Player player, Sheep sheep);
	// #endregion interact_method
}
// #endregion sheep_shear_callback
