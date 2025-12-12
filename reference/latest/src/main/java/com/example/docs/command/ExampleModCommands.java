package com.example.docs.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.ResourceArgument;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;
import net.minecraft.commands.synchronization.SuggestionProviders;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.permissions.Permissions;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.ArgumentTypeRegistry;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

// Class to contain all mod command registrations.
public class ExampleModCommands implements ModInitializer {
	// :::execute_dedicated_command
	private static int executeDedicatedCommand(CommandContext<CommandSourceStack> context) {
		context.getSource().sendSuccess(() -> Component.literal("Called /dedicated_command."), false);
		return 1;
	}

	// :::execute_dedicated_command
	// :::execute_required_command
	private static int executeRequiredCommand(CommandContext<CommandSourceStack> context) {
		context.getSource().sendSuccess(() -> Component.literal("Called /required_command."), false);
		return 1;
	}

	// :::execute_required_command
	// :::execute_sub_command_one
	private static int executeSubCommandOne(CommandContext<CommandSourceStack> context) {
		context.getSource().sendSuccess(() -> Component.literal("Called /command sub_command_one."), false);
		return 1;
	}

	// :::execute_sub_command_one
	// :::execute_command_sub_command_two
	private static int executeCommandTwo(CommandContext<CommandSourceStack> context) {
		context.getSource().sendSuccess(() -> Component.literal("Called /command_two."), false);
		return 1;
	}

	private static int executeSubCommandTwo(CommandContext<CommandSourceStack> context) {
		context.getSource().sendSuccess(() -> Component.literal("Called /sub_command_two."), false);
		return 1;
	}

	// :::execute_command_sub_command_two
	// :::execute_redirected_by
	private static int executeRedirectedBy(CommandContext<CommandSourceStack> context) {
		context.getSource().sendSuccess(() -> Component.literal("Called /redirected_by."), false);
		return 1;
	}

	// :::execute_redirected_by
	// :::execute_command_with_arg
	private static int executeCommandWithArg(CommandContext<CommandSourceStack> context) {
		int value = IntegerArgumentType.getInteger(context, "value");
		context.getSource().sendSuccess(() -> Component.literal("Called /command_with_arg with value = %s".formatted(value)), false);
		return 1;
	}

	// :::execute_command_with_arg
	// :::execute_command_with_two_args
	private static int executeWithOneArg(CommandContext<CommandSourceStack> context) {
		int value1 = IntegerArgumentType.getInteger(context, "value_one");
		context.getSource().sendSuccess(() -> Component.literal("Called /command_with_two_args with value one = %s".formatted(value1)), false);
		return 1;
	}

	private static int executeWithTwoArgs(CommandContext<CommandSourceStack> context) {
		int value1 = IntegerArgumentType.getInteger(context, "value_one");
		int value2 = IntegerArgumentType.getInteger(context, "value_two");
		context.getSource().sendSuccess(() -> Component.literal("Called /argtater2 with value one = %s and value two = %s".formatted(value1, value2)),
				false);
		return 1;
	}

	// :::execute_command_with_two_args
	// :::execute_common
	private static int executeCommon(int value1, int value2, CommandContext<CommandSourceStack> context) {
		context.getSource().sendSuccess(() -> Component.literal("Called /command_with_common_exec with value 1 = %s and value 2 = %s".formatted(value1, value2)), false);
		return 1;
	}

	// :::execute_common
	// :::execute_custom_arg_command
	private static int executeCustomArgCommand(CommandContext<CommandSourceStack> context) {
		BlockPos arg = context.getArgument("block_pos", BlockPos.class);
		context.getSource().sendSuccess(() -> Component.literal("Called /command_with_custom_arg with block pos = %s".formatted(arg)), false);
		return 1;
	}

	// :::execute_custom_arg_command
	// :::execute_command_with_suggestions
	private static int executeCommandWithSuggestions(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
		var entityType = ResourceArgument.getSummonableEntityType(context, "entity");
		context.getSource().sendSuccess(() -> Component.literal("Called /command_with_suggestions with entity = %s".formatted(entityType.value().toShortString())), false);
		return 1;
	}

	// :::execute_command_with_suggestions
	// :::execute_command_with_custom_suggestions
	private static int executeCommandWithCustomSuggestions(CommandContext<CommandSourceStack> context) {
		String name = StringArgumentType.getString(context, "player_name");
		context.getSource().sendSuccess(() -> Component.literal("Called /command_with_custom_suggestions with value = %s".formatted(name)), false);
		return 1;
	}

	// :::execute_command_with_custom_suggestions

	@Override
	public void onInitialize() {
		// :::register_custom_arg
		ArgumentTypeRegistry.registerArgumentType(
				Identifier.fromNamespaceAndPath("fabric-docs", "block_pos"),
				BlockPosArgumentType.class,
				SingletonArgumentInfo.contextFree(BlockPosArgumentType::new)
		);
		// :::register_custom_arg

		// :::test_command
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(Commands.literal("test_command").executes(context -> {
				context.getSource().sendSuccess(() -> Component.literal("Called /test_command."), false);
				return 1;
			}));
		});
		// :::test_command

		// :::dedicated_command
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			if (environment.includeDedicated) {
				dispatcher.register(Commands.literal("dedicated_command")
						.executes(ExampleModCommands::executeDedicatedCommand));
			}
		});
		// :::dedicated_command

		// :::required_command
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(Commands.literal("required_command")
					.requires(source -> source.permissions().hasPermission(Permissions.COMMANDS_MODERATOR))
					.executes(ExampleModCommands::executeRequiredCommand));
		});
		// :::required_command

		// :::sub_command_one
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(Commands.literal("command_one")
					.then(Commands.literal("sub_command_one").executes(ExampleModCommands::executeSubCommandOne)));
		});
		// :::sub_command_one

		// :::sub_command_two
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(Commands.literal("command_two")
					.executes(ExampleModCommands::executeCommandTwo)
					.then(Commands.literal("sub_command_two").executes(ExampleModCommands::executeSubCommandTwo)));
		});
		// :::sub_command_two

		// :::redirect_command
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			var redirectedBy = dispatcher.register(Commands.literal("redirected_by").executes(ExampleModCommands::executeRedirectedBy));
			dispatcher.register(Commands.literal("to_redirect").executes(ExampleModCommands::executeRedirectedBy).redirect(redirectedBy));
		});
		// :::redirect_command

		// :::command_with_arg
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(Commands.literal("command_with_arg")
					.then(Commands.argument("value", IntegerArgumentType.integer())
							.executes(ExampleModCommands::executeCommandWithArg)));
		});
		// :::command_with_arg

		// :::command_with_two_args
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(Commands.literal("command_with_two_args")
					.then(Commands.argument("value_one", IntegerArgumentType.integer())
							.executes(ExampleModCommands::executeWithOneArg)
							.then(Commands.argument("value_two", IntegerArgumentType.integer())
									.executes(ExampleModCommands::executeWithTwoArgs))));
		});
		// :::command_with_two_args

		// :::command_with_common_exec
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(Commands.literal("command_with_common_exec")
					.then(Commands.argument("value_one", IntegerArgumentType.integer())
							.executes(context -> executeCommon(IntegerArgumentType.getInteger(context, "value_one"), 0, context))
							.then(Commands.argument("value_two", IntegerArgumentType.integer())
									.executes(context -> executeCommon(
											IntegerArgumentType.getInteger(context, "value_one"),
											IntegerArgumentType.getInteger(context, "value_two"),
											context)))));
		});
		// :::command_with_common_exec

		// :::custom_arg_command
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(Commands.literal("command_with_custom_arg").then(
					Commands.argument("block_pos", new BlockPosArgumentType())
							.executes(ExampleModCommands::executeCustomArgCommand)
			));
		});
		// :::custom_arg_command

		// :::command_with_suggestions
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(Commands.literal("command_with_suggestions").then(
					Commands.argument("entity", ResourceArgument.resource(registryAccess, Registries.ENTITY_TYPE))
							.suggests(SuggestionProviders.cast(SuggestionProviders.SUMMONABLE_ENTITIES))
							.executes(ExampleModCommands::executeCommandWithSuggestions)
			));
		});
		// :::command_with_suggestions

		// :::command_with_custom_suggestions
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(Commands.literal("command_with_custom_suggestions").then(
					Commands.argument("player_name", StringArgumentType.string())
							.suggests(new PlayerSuggestionProvider())
							.executes(ExampleModCommands::executeCommandWithCustomSuggestions)
			));
		});
		// :::command_with_custom_suggestions
	}
}
