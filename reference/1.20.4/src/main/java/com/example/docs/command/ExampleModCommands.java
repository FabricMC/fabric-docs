package com.example.docs.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.ArgumentTypeRegistry;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;
import net.minecraft.commands.synchronization.SuggestionProviders;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;

// Class to contain all mod command registrations.
public class ExampleModCommands implements ModInitializer {
	@Override
	public void onInitialize() {
		// :::_1
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(Commands.literal("tater").executes(context -> {
				context.getSource().sendSuccess(() -> Component.literal("Called /tater with no arguments."), false);
				return 1;
			}));
		});
		// :::_1

		// :::11
		ArgumentTypeRegistry.registerArgumentType(
				new ResourceLocation("fabric-docs", "block_pos"),
				BlockPosArgumentType.class,
				SingletonArgumentInfo.contextFree(BlockPosArgumentType::new)
		);
		// :::11

		// :::2
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			if (environment.includeDedicated) {
				dispatcher.register(Commands.literal("dedicatedtater").executes(context -> {
					context.getSource()
							.sendSuccess(() -> Component.literal("Called /dedicatedtater with no arguments."), false);
					return 1;
				}));
			}
		});
		// :::2

		// :::3
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(Commands.literal("requiredtater")
					.requires(source -> source.hasPermission(1))
					.executes(context -> {
						context.getSource()
								.sendSuccess(() -> Component.literal("Called /requiredtater with no arguments."), false);
						return 1;
					}));
		});
		// :::3

		// :::4
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(Commands.literal("argtater1")
					.then(Commands.argument("value", IntegerArgumentType.integer())
							.executes(context -> {
								int value = IntegerArgumentType.getInteger(context, "value");
								context.getSource()
										.sendSuccess(
												() -> Component.literal(
														"Called /argtater1 with value = %s".formatted(value)),
												false);
								return 1;
							})));
		});
		// :::4

		// :::5
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(Commands.literal("argtater2")
					.then(Commands.argument("value1", IntegerArgumentType.integer())
							.executes(context -> {
								int value1 = IntegerArgumentType.getInteger(context, "value1");
								context.getSource()
										.sendSuccess(
												() -> Component.literal(
														"Called /argtater2 with value 1 = %s".formatted(value1)),
												false);
								return 1;
							})
							.then(Commands.argument("value2", IntegerArgumentType.integer())
									.executes(context -> {
										int value1 = IntegerArgumentType.getInteger(context, "value1");
										int value2 = IntegerArgumentType.getInteger(context, "value2");
										context.getSource()
												.sendSuccess(
														() -> Component.literal(
																"Called /argtater2 with value 1 = %s and value 2 = %s"
																		.formatted(value1, value2)),
														false);
										return 1;
									}))));
		});
		// :::5

		// :::6
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(Commands.literal("argtater3")
					.then(Commands.argument("value1", IntegerArgumentType.integer())
							.executes(context ->
									printValues(IntegerArgumentType.getInteger(context, "value1"), 0, context))
							.then(Commands.argument("value2", IntegerArgumentType.integer())
									.executes(context -> printValues(
											IntegerArgumentType.getInteger(context, "value1"),
											IntegerArgumentType.getInteger(context, "value2"),
											context)))));
		});
		// :::6

		// :::7
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(Commands.literal("subtater1")
					.then(Commands.literal("subcommand").executes(context -> {
						context.getSource()
								.sendSuccess(
										() -> Component.literal("Called /subtater1 subcommand with no arguments."), false);
						return 1;
					})));
		});
		// :::7

		// :::8
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(Commands.literal("subtater2")
					.executes(context -> {
						context.getSource()
								.sendSuccess(() -> Component.literal("Called /subtater2 with no arguments."), false);
						return 1;
					})
					.then(Commands.literal("subcommand").executes(context -> {
						context.getSource()
								.sendSuccess(
										() -> Component.literal("Called /subtater2 subcommand with no arguments."), false);
						return 1;
					})));
		});
		// :::8

		// :::9
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(Commands.literal("entity_name").then(
					Commands.argument("entity", EntityArgument.entity())
							.suggests(SuggestionProviders.SUMMONABLE_ENTITIES)
							.executes(context -> {
								EntityType<?> entityType = EntityArgument.getEntity(context, "entity").getType();
								context.getSource().sendSuccess(
										() -> Component.literal("Called /subtater2 with entity: ")
												.append(
														Component.translatable(entityType.getDescriptionId())
												),
										false);
								return 1;
							})
			));
		});
		// :::9

		// :::10
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(Commands.literal("parse_pos").then(
					Commands.argument("pos", new BlockPosArgumentType())
							.executes(context -> {
								BlockPos arg = context.getArgument("pos", BlockPos.class);
								context.getSource().sendSuccess(
										() -> Component.literal("Called /parse_pos with BlockPos: ")
												.append(Component.nullToEmpty(arg.toString())),
										false);
								return 1;
							})
			));
		});
		// :::10

		// :::12
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			final var taterCommandNode = dispatcher.register(Commands.literal("tater4").executes(context -> {
				context.getSource().sendSuccess(() -> Component.literal("Called /tater4 with no arguments."), false);
				return 1;
			}));
			dispatcher.register(Commands.literal("redirect_potato").redirect(taterCommandNode));
		});
		// :::12
	}

	// :::6

	private static int printValues(int value1, int value2, CommandContext<CommandSourceStack> context) {
		context.getSource()
				.sendSuccess(
						() -> Component.literal(
								"Called /argtater3 with value 1 = %s and value 2 = %s".formatted(value1, value2)),
						false);
		return 1;
	}

	// :::6
}
