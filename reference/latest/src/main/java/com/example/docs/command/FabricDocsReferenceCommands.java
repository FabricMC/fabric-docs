package com.example.docs.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;

import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.serialize.ConstantArgumentSerializer;
import net.minecraft.command.suggestion.SuggestionProviders;
import net.minecraft.entity.EntityType;
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
	@Override
	public void onInitialize() {
		// :::_1
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(CommandManager.literal("tater").executes(context -> {
				context.getSource().sendFeedback(() -> Text.literal("Called /tater with no arguments."), false);
				return 1;
			}));
		});
		// :::_1

		// :::11
		ArgumentTypeRegistry.registerArgumentType(
				new Identifier("fabric-docs", "block_pos"),
				BlockPosArgumentType.class,
				ConstantArgumentSerializer.of(BlockPosArgumentType::new)
		);
		// :::11

		// :::2
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			if (environment.dedicated) {
				dispatcher.register(CommandManager.literal("dedicatedtater").executes(context -> {
					context.getSource()
							.sendFeedback(() -> Text.literal("Called /dedicatedtater with no arguments."), false);
					return 1;
				}));
			}
		});
		// :::2

		// :::3
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(CommandManager.literal("requiredtater")
					.requires(source -> source.hasPermissionLevel(1))
					.executes(context -> {
						context.getSource()
								.sendFeedback(() -> Text.literal("Called /requiredtater with no arguments."), false);
						return 1;
					}));
		});
		// :::3

		// :::4
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(CommandManager.literal("argtater1")
					.then(CommandManager.argument("value", IntegerArgumentType.integer())
							.executes(context -> {
								int value = IntegerArgumentType.getInteger(context, "value");
								context.getSource()
										.sendFeedback(
												() -> Text.literal(
														"Called /argtater1 with value = %s".formatted(value)),
												false);
								return 1;
							})));
		});
		// :::4

		// :::5
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(CommandManager.literal("argtater2")
					.then(CommandManager.argument("value1", IntegerArgumentType.integer())
							.executes(context -> {
								int value1 = IntegerArgumentType.getInteger(context, "value1");
								context.getSource()
										.sendFeedback(
												() -> Text.literal(
														"Called /argtater2 with value 1 = %s".formatted(value1)),
												false);
								return 1;
							})
							.then(CommandManager.argument("value2", IntegerArgumentType.integer())
									.executes(context -> {
										int value1 = IntegerArgumentType.getInteger(context, "value1");
										int value2 = IntegerArgumentType.getInteger(context, "value2");
										context.getSource()
												.sendFeedback(
														() -> Text.literal(
																"Called /argtater2 with value 1 = %s and value 2 = %s"
																		.formatted(value1, value2)),
														false);
										return 1;
									}))));
		});
		// :::5

		// :::6
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(CommandManager.literal("argtater3")
					.then(CommandManager.argument("value1", IntegerArgumentType.integer())
							.executes(context ->
									printValues(IntegerArgumentType.getInteger(context, "value1"), 0, context))
							.then(CommandManager.argument("value2", IntegerArgumentType.integer())
									.executes(context -> printValues(
											IntegerArgumentType.getInteger(context, "value1"),
											IntegerArgumentType.getInteger(context, "value2"),
											context)))));
		});
		// :::6

		// :::7
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(CommandManager.literal("subtater1")
					.then(CommandManager.literal("subcommand").executes(context -> {
						context.getSource()
								.sendFeedback(
										() -> Text.literal("Called /subtater1 subcommand with no arguments."), false);
						return 1;
					})));
		});
		// :::7

		// :::8
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(CommandManager.literal("subtater2")
					.executes(context -> {
						context.getSource()
								.sendFeedback(() -> Text.literal("Called /subtater2 with no arguments."), false);
						return 1;
					})
					.then(CommandManager.literal("subcommand").executes(context -> {
						context.getSource()
								.sendFeedback(
										() -> Text.literal("Called /subtater2 subcommand with no arguments."), false);
						return 1;
					})));
		});
		// :::8

		// :::9
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(CommandManager.literal("entity_name").then(
					CommandManager.argument("entity", EntityArgumentType.entity())
							.suggests(SuggestionProviders.SUMMONABLE_ENTITIES)
							.executes(context -> {
								EntityType<?> entityType = EntityArgumentType.getEntity(context, "entity").getType();
								context.getSource().sendFeedback(
										() -> Text.literal("Called /subtater2 with entity: ")
												.append(
														Text.translatable(entityType.getTranslationKey())
												),
										false);
								return 1;
							})
			));
		});
		// :::9

		// :::10
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(CommandManager.literal("parse_pos").then(
					CommandManager.argument("pos", new BlockPosArgumentType())
							.executes(context -> {
								BlockPos arg = context.getArgument("pos", BlockPos.class);
								context.getSource().sendFeedback(
										() -> Text.literal("Called /parse_pos with BlockPos: ")
												.append(Text.of(arg.toString())),
										false);
								return 1;
							})
			));
		});
		// :::10

		// :::12
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			final var taterCommandNode = dispatcher.register(CommandManager.literal("tater4").executes(context -> {
				context.getSource().sendFeedback(() -> Text.literal("Called /tater4 with no arguments."), false);
				return 1;
			}));
			dispatcher.register(CommandManager.literal("redirect_potato").redirect(taterCommandNode));
		});
		// :::12
	}

	// :::6

	private static int printValues(int value1, int value2, CommandContext<ServerCommandSource> context) {
		context.getSource()
				.sendFeedback(
						() -> Text.literal(
								"Called /argtater3 with value 1 = %s and value 2 = %s".formatted(value1, value2)),
						false);
		return 1;
	}

	// :::6
}
