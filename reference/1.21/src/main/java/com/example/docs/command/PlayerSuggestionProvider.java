package com.example.docs.command;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;

import net.minecraft.server.command.ServerCommandSource;

// :::1
public class PlayerSuggestionProvider implements SuggestionProvider<ServerCommandSource> {
	@Override
	public CompletableFuture<Suggestions> getSuggestions(CommandContext<ServerCommandSource> context, SuggestionsBuilder builder) throws CommandSyntaxException {
		ServerCommandSource source = context.getSource();

		// Thankfully, the ServerCommandSource has a method to get a list of player names.
		Collection<String> playerNames = source.getPlayerNames();

		// Add all player names to the builder.
		for (String playerName : playerNames) {
			builder.suggest(playerName);
		}

		// Lock the suggestions after we've modified them.
		return builder.buildFuture();
	}
}
// :::1
