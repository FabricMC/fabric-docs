package com.example.docs.gamerule.custom;

import com.example.docs.gamerule.ModGamerules;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.gamerules.GameRules;

public class BadVisionGamerule {

	public static void initialize() {
		// :::badvision
		// In your mod's onInitialize():
		ServerTickEvents.END_WORLD_TICK.register(serverworld -> {
			// runs every tick on the server
			// :::badvision
			// :::access
			boolean gameruleState = serverworld.getGameRules().get(ModGamerules.BADVISION_BOOLEAN_GAMERULE);
			// :::access
			// :::vanilla
			boolean doMobGriefing = serverworld.getGameRules().get(GameRules.MOB_GRIEFING);
			// :::vanilla
			// :::badvision
			// Check for the state of the gamerule
			boolean badvisionGameruleStatus = serverworld.getGameRules().get(ModGamerules.BADVISION_BOOLEAN_GAMERULE);

			if (badvisionGameruleStatus) {
				// If the gamerule is true
				for (Player player : serverworld.getPlayers(p -> true)) {
					// Apply blindness to every player
					player.addEffect(new MobEffectInstance(
									MobEffects.BLINDNESS,
									40, // Duration in ticks (each tick is 1/20th of a second)
									1, // Amplifier
									false, // isAmbient; ambient particles are less obtrusive, common with beacons
									false, // showParticles; overrides `isAmbient` and removes particles entirely
									false // showIcon in HUD; shows the icon in the top right of the screen, common with beacons
					));
				}
			}
		});
		// :::badvision
	}
}
