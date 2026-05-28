package com.example.docs.event;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.animal.sheep.Sheep;
import net.minecraft.world.entity.player.Player;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

// #region javadoc-comment
/**
 * Callback for shearing a sheep.
 * Called before the sheep is sheared, items are dropped, and items are damaged.
 * Upon return:
 * - SUCCESS cancels further processing and continues with normal shearing behavior.
 * - PASS falls back to further processing and defaults to SUCCESS if no other listeners are available
 * - FAIL cancels further processing and does not shear the sheep.
 */
// #endregion javadoc-comment

// #region sheep-shear-callback
public interface SheepShearCallback {
	Event<SheepShearCallback> EVENT = EventFactory.createArrayBacked(SheepShearCallback.class,
			// #region listener-iterator
			(listeners) -> (player, sheep) -> {
				for (SheepShearCallback listener : listeners) {
					// #endregion sheep-shear-callback
					// ...
					// #region sheep-shear-callback
					// #endregion listener-iterator
					InteractionResult result = listener.interact(player, sheep);

					// #region return-value
					if (result != InteractionResult.PASS) {
						return result;
					}
					// #endregion return-value
					// #region listener-iterator
				}
				// #endregion listener-iterator

				// #region return-value
				return InteractionResult.PASS;
				// #endregion return-value
				// #region listener-iterator
			}
	// #endregion listener-iterator
	);

	// #region interact-method
	InteractionResult interact(Player player, Sheep sheep);
	// #endregion interact-method
}
// #endregion sheep-shear-callback
