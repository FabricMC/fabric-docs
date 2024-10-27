package com.example.docs.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.command.argument.RegistryEntryReferenceArgumentType;
import net.minecraft.command.argument.serialize.ConstantArgumentSerializer;
import net.minecraft.command.suggestion.SuggestionProviders;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.ArgumentTypeRegistry;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

// Class to contain all mod command registrations.
public class FabricDocsReferenceCommands implements ModInitializer {
	// :::execute_dedicated_command
	private static int executeDedicatedCommand(CommandContext<ServerCommandSource> context) {
		context.getSource().sendFeedback(() -> Text.literal("Called /dedicated_command."), false);
		return 1;
	}

	// :::execute_dedicated_command
	// :::execute_required_command
	private static int executeRequiredCommand(CommandContext<ServerCommandSource> context) {
		context.getSource().sendFeedback(() -> Text.literal("Called /required_command."), false);
		return 1;
	}

	// :::execute_required_command
	// :::execute_sub_command_one
	private static int executeSubCommandOne(CommandContext<ServerCommandSource> context) {
		context.getSource().sendFeedback(() -> Text.literal("Called /command sub_command_one."), false);
		return 1;
	}

	// :::execute_sub_command_one
	// :::execute_command_sub_command_two
	private static int executeCommandTwo(CommandContext<ServerCommandSource> context) {
		context.getSource().sendFeedback(() -> Text.literal("Called /command_two."), false);
		return 1;
	}

	private static int executeSubCommandTwo(CommandContext<ServerCommandSource> context) {
		context.getSource().sendFeedback(() -> Text.literal("Called /sub_command_two."), false);
		return 1;
	}

	// :::execute_command_sub_command_two
	// :::execute_redirected_by
	private static int executeRedirectedBy(CommandContext<ServerCommandSource> context) {
		context.getSource().sendFeedback(() -> Text.literal("Called /redirected_by."), false);
		return 1;
	}

	// :::execute_redirected_by
	// :::execute_command_with_arg
	private static int executeCommandWithArg(CommandContext<ServerCommandSource> context) {
		int value = IntegerArgumentType.getInteger(context, "value");
		context.getSource().sendFeedback(() -> Text.literal("Called /command_with_arg with value = %s".formatted(value)), false);
		return 1;
	}

	// :::execute_command_with_arg
	// :::execute_command_with_two_args
	private static int executeWithOneAre(CommandContext<ServerCommandSource> context) {
		int value1 = IntegerArgumentType.getInteger(context, "value_one");
		context.getSource().sendFeedback(() -> Text.literal("Called /command_with_two_args with value one = %s".formatted(value1)), false);
		return 1;
	}

	private static int executeWithTwoArgs(CommandContext<ServerCommandSource> context) {
		int value1 = IntegerArgumentType.getInteger(context, "value_one");
		int value2 = IntegerArgumentType.getInteger(context, "value_two");
		context.getSource().sendFeedback(() -> Text.literal("Called /argtater2 with value one = %s and value two = %s".formatted(value1, value2)),
				false);
		return 1;
	}

	// :::execute_command_with_two_args
	// :::execute_common
	private static int executeCommon(int value1, int value2, CommandContext<ServerCommandSource> context) {
		context.getSource().sendFeedback(() -> Text.literal("Called /command_with_common_exec with value 1 = %s and value 2 = %s".formatted(value1, value2)), false);
		return 1;
	}

	// :::execute_common
	// :::execute_custom_arg_command
	private static int executeCustomArgCommand(CommandContext<ServerCommandSource> context) {
		BlockPos arg = context.getArgument("block_pos", BlockPos.class);
		context.getSource().sendFeedback(() -> Text.literal("Called /command_with_custom_arg with block pos = %s".formatted(arg)), false);
		return 1;
	}

	// :::execute_custom_arg_command
	// :::execute_command_with_suggestions
	private static int executeCommandWithSuggestions(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		var entityType = RegistryEntryReferenceArgumentType.getSummonableEntityType(context, "entity");
		context.getSource().sendFeedback(() -> Text.literal("Called /command_with_suggestions with entity = %s".formatted(entityType.value().getUntranslatedName())), false);
		return 1;
	}

	// :::execute_command_with_suggestions
	// :::execute_command_with_custom_suggestions
	private static int executeCommandWithCustomSuggestions(CommandContext<ServerCommandSource> context) {
		String name = StringArgumentType.getString(context, "player_name");
		context.getSource().sendFeedback(() -> Text.literal("Called /command_with_custom_suggestions with value = %s".formatted(name)), false);
		return 1;
	}

	// :::execute_command_with_custom_suggestions

	@Override
	public void onInitialize() {
		// :::register_custom_arg
		ArgumentTypeRegistry.registerArgumentType(
				Identifier.of("fabric-docs", "block_pos"),
				BlockPosArgumentType.class,
				ConstantArgumentSerializer.of(BlockPosArgumentType::new)
		);
		// :::register_custom_arg

		// :::test_command
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(CommandManager.literal("test_command").executes(context -> {
				context.getSource().sendFeedback(() -> Text.literal("Called /test_command."), false);
				return 1;
			}));
		});
		// :::test_command

		// :::dedicated_command
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			if (environment.dedicated) {
				dispatcher.register(CommandManager.literal("dedicated_command")
						.executes(FabricDocsReferenceCommands::executeDedicatedCommand));
			}
		});
		// :::dedicated_command

		// :::required_command
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(CommandManager.literal("required_command")
					.requires(source -> source.hasPermissionLevel(1))
					.executes(FabricDocsReferenceCommands::executeRequiredCommand));
		});
		// :::required_command

		// :::sub_command_one
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(CommandManager.literal("command_one")
					.then(CommandManager.literal("sub_command_one").executes(FabricDocsReferenceCommands::executeSubCommandOne)));
		});
		// :::sub_command_one

		// :::sub_command_two
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(CommandManager.literal("command_two")
					.executes(FabricDocsReferenceCommands::executeCommandTwo)
					.then(CommandManager.literal("sub_command_two").executes(FabricDocsReferenceCommands::executeSubCommandTwo)));
		});
		// :::sub_command_two

		// :::redirect_command
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			var redirectedBy = dispatcher.register(CommandManager.literal("redirected_by").executes(FabricDocsReferenceCommands::executeRedirectedBy));
			dispatcher.register(CommandManager.literal("to_redirect").executes(FabricDocsReferenceCommands::executeRedirectedBy).redirect(redirectedBy));
		});
		// :::redirect_command

		// :::command_with_arg
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(CommandManager.literal("command_with_arg")
					.then(CommandManager.argument("value", IntegerArgumentType.integer())
							.executes(FabricDocsReferenceCommands::executeCommandWithArg)));
		});
		// :::command_with_arg

		// :::command_with_two_args
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(CommandManager.literal("command_with_two_args")
					.then(CommandManager.argument("value_one", IntegerArgumentType.integer())
							.executes(FabricDocsReferenceCommands::executeWithOneAre)
							.then(CommandManager.argument("value_two", IntegerArgumentType.integer())
									.executes(FabricDocsReferenceCommands::executeWithTwoArgs))));
		});
		// :::command_with_two_args

		// :::command_with_common_exec
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(CommandManager.literal("command_with_common_exec")
					.then(CommandManager.argument("value_one", IntegerArgumentType.integer())
							.executes(context -> executeCommon(IntegerArgumentType.getInteger(context, "value_one"), 0, context))
							.then(CommandManager.argument("value_two", IntegerArgumentType.integer())
									.executes(context -> executeCommon(
											IntegerArgumentType.getInteger(context, "value_one"),
											IntegerArgumentType.getInteger(context, "value_two"),
											context)))));
		});
		// :::command_with_common_exec

		// :::custom_arg_command
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(CommandManager.literal("command_with_custom_arg").then(
					CommandManager.argument("block_pos", new BlockPosArgumentType())
							.executes(FabricDocsReferenceCommands::executeCustomArgCommand)
			));
		});
		// :::custom_arg_command

		// :::command_with_suggestions
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(CommandManager.literal("command_with_suggestions").then(
					CommandManager.argument("entity", RegistryEntryReferenceArgumentType.registryEntry(registryAccess, RegistryKeys.ENTITY_TYPE))
							.suggests(SuggestionProviders.SUMMONABLE_ENTITIES)
							.executes(FabricDocsReferenceCommands::executeCommandWithSuggestions)
			));
		});
		// :::command_with_suggestions

		// :::command_with_custom_suggestions
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(CommandManager.literal("command_with_custom_suggestions").then(
					CommandManager.argument("player_name", StringArgumentType.string())
							.suggests(new PlayerSuggestionProvider())
							.executes(FabricDocsReferenceCommands::executeCommandWithCustomSuggestions)
			));
		});
		// :::command_with_custom_suggestions
	}
}
