---
title: Fabric API 26.1 移植指南
description: 将你的模组更新到 Minecraft 26.1 的 Fabric API 的指南。
authors:
  - modmuss50
---

因为现在 Minecraft 26.1 没有混淆了，所以许多开发者现在都会使用 Mojang 的官方映射。 此前，Fabric API 会使用 Yarn 映射构建，但迁移到官方映射后，API 的名称也随之更新，以在合适的地方匹配官方的名称。 这些改变没有向后兼容，所以你会需要更新你的模组以使用新的名称。 这些 API 的更名不会由迁移映射工具处理，如果你的模组是基于 Yarn 映射构建的，非常推荐你先[迁移映射](../../migrating-mappings/)。

下面是 Fabric API 26.1 中所有更名的类的列表、方法和字段，使用此列表的最好方式是在此页面中查找（<kbd>⌘/CTRL</kbd>+<kbd>F</kbd>）旧名称并替换为新名称。

一个 IntelliJ IDEA 迁移映射也可用于帮助自动化类的重命名。 下载后按照 [JetBrains 文档](https://www.jetbrains.com/help/idea/migrate.html)的指引以应用在你的项目中。

<DownloadEntry downloadURL="/assets/develop/porting/fabric-api-26-1-migration-map.xml">类迁移映射</DownloadEntry>

## 重命名列表{#renames}

- `net/fabricmc/fabric/api/biome/v1/BiomeModificationContext$EffectsContext`
  - `setFoliageColor` → `setFoliageColorOverride`
  - `clearFoliageColor` → `clearFoliageColorOverride`
  - `setDryFoliageColor` → `setDryFoliageColorOverride`
  - `clearDryFoliageColor` → `clearDryFoliageColorOverride`
  - `setGrassColor` → `setGrassColorOverride`
  - `clearGrassColor` → `clearGrassColorOverride`

- `net/fabricmc/fabric/api/biome/v1/BiomeModificationContext$SpawnSettingsContext` → `BiomeModificationContext$MobSpawnSettingsContext`
  - `setCreatureSpawnProbability` → `setCreatureGenerationProbability`
  - `getSpawnEntries` → `getMobs`
  - `setSpawnCost` → `addMobCharge`
  - `clearSpawnCost` → `clearMobCharge`

- `net/fabricmc/fabric/api/biome/v1/BiomeModificationContext`
  - `getSpawnSettings` → `getMobSpawnSettings`

- `net/fabricmc/fabric/api/biome/v1/BiomeSelectionContext`
  - `getBiomeRegistryEntry` → `getBiomeHolder`

- `net/fabricmc/fabric/api/client/command/v2/ClientCommandManager` → `ClientCommands`

- `net/fabricmc/fabric/api/client/command/v2/FabricClientCommandSource`
  - `getWorld` → `getLevel`

- `net/fabricmc/fabric/api/client/datagen/v1/builder/SoundTypeBuilder$EntryBuilder` → `SoundTypeBuilder$RegistrationBuilder`

- `net/fabricmc/fabric/api/client/datagen/v1/builder/SoundTypeBuilder`
  - `category` → `source`

- `net/fabricmc/fabric/api/client/event/lifecycle/v1/ClientTickEvents$EndWorldTick` → `ClientTickEvents$EndLevelTick`

- `net/fabricmc/fabric/api/client/event/lifecycle/v1/ClientTickEvents$StartWorldTick` → `ClientTickEvents$StartLevelTick`

- `net/fabricmc/fabric/api/client/event/lifecycle/v1/ClientTickEvents`
  - `START_WORLD_TICK` → `START_LEVEL_TICK`
  - `END_WORLD_TICK` → `END_LEVEL_TICK`

- `net/fabricmc/fabric/api/client/event/lifecycle/v1/ClientWorldEvents$AfterClientWorldChange` → `ClientLevelEvents$AfterClientLevelChange`
  - `afterWorldChange` → `afterLevelChange`

- `net/fabricmc/fabric/api/client/event/lifecycle/v1/ClientWorldEvents` → `ClientLevelEvents`
  - `AFTER_CLIENT_WORLD_CHANGE` → `AFTER_CLIENT_LEVEL_CHANGE`

- `net/fabricmc/fabric/api/client/itemgroup/v1/FabricCreativeInventoryScreen` → `net/fabricmc/fabric/api/client/creativetab/v1/FabricCreativeModeInventoryScreen`
  - `getItemGroupsOnPage` → `getTabsOnPage`
  - `getSelectedItemGroup` → `getSelectedTab`
  - `setSelectedItemGroup` → `setSelectedTab`

- `net/fabricmc/fabric/api/client/itemgroup/v1/package-info` → `net/fabricmc/fabric/api/client/creativetab/v1/package-info`

- `net/fabricmc/fabric/api/client/keybinding/v1/KeyBindingHelper` → `net/fabricmc/fabric/api/client/keymapping/v1/KeyMappingHelper`
  - `registerKeyBinding` → `registerKeyMapping`

- `net/fabricmc/fabric/api/client/keybinding/v1/package-info` → `net/fabricmc/fabric/api/client/keymapping/v1/package-info`

- `net/fabricmc/fabric/api/client/model/loading/v1/FabricBakedModelManager` → `FabricModelManager`

- `net/fabricmc/fabric/api/client/model/loading/v1/ModelModifier$AfterBakeItem$Context`
  - `bakeContext` → `bakingContext`

- `net/fabricmc/fabric/api/client/model/loading/v1/ModelModifier$BeforeBakeItem$Context`
  - `bakeContext` → `bakingContext`

- `net/fabricmc/fabric/api/client/model/loading/v1/wrapper/WrapperUnbakedGroupedBlockStateModel` → `WrapperUnbakedRootBlockStateModel`

- `net/fabricmc/fabric/api/client/networking/v1/C2SConfigurationChannelEvents$Register` → `ServerboundConfigurationChannelEvents$Register`

- `net/fabricmc/fabric/api/client/networking/v1/C2SConfigurationChannelEvents$Unregister` → `ServerboundConfigurationChannelEvents$Unregister`

- `net/fabricmc/fabric/api/client/networking/v1/C2SConfigurationChannelEvents` → `ServerboundConfigurationChannelEvents`

- `net/fabricmc/fabric/api/client/networking/v1/C2SPlayChannelEvents$Register` → `ServerboundPlayChannelEvents$Register`

- `net/fabricmc/fabric/api/client/networking/v1/C2SPlayChannelEvents$Unregister` → `ServerboundPlayChannelEvents$Unregister`

- `net/fabricmc/fabric/api/client/networking/v1/C2SPlayChannelEvents` → `ServerboundPlayChannelEvents`

- `net/fabricmc/fabric/api/client/networking/v1/ClientConfigurationNetworking$Context`
  - `networkHandler` → `packetListener`

- `net/fabricmc/fabric/api/client/networking/v1/ClientPlayNetworking`
  - `createC2SPacket` → `createServerboundPacket`

- `net/fabricmc/fabric/api/client/particle/v1/FabricSpriteProvider` → `FabricSpriteSet`

- `net/fabricmc/fabric/api/client/particle/v1/ParticleFactoryRegistry$PendingParticleFactory` → `ParticleProviderRegistry$PendingParticleProvider`

- `net/fabricmc/fabric/api/client/particle/v1/ParticleFactoryRegistry` → `ParticleProviderRegistry`

- `net/fabricmc/fabric/api/client/particle/v1/ParticleRenderEvents$AllowBlockDustTint` → `ParticleRenderEvents$AllowTerrainParticleTint`
  - `allowBlockDustTint` → `allowTerrainParticleTint`

- `net/fabricmc/fabric/api/client/particle/v1/ParticleRenderEvents`
  - `ALLOW_BLOCK_DUST_TINT` → `ALLOW_TERRAIN_PARTICLE_TINT`

- `net/fabricmc/fabric/api/client/particle/v1/ParticleRendererRegistry` → `ParticleGroupRegistry`
  - `getParticleTextureSheet` → `getParticleRenderType`

- `net/fabricmc/fabric/api/client/rendering/v1/AtlasSourceRegistry` → `SpriteSourceRegistry`

- `net/fabricmc/fabric/api/client/rendering/v1/BlockRenderLayerMap` → `ChunkSectionLayerMap`

- `net/fabricmc/fabric/api/client/rendering/v1/ColorProviderRegistry.BLOCK` → `BlockColorRegistry`

- `net/fabricmc/fabric/api/client/rendering/v1/DrawItemStackOverlayCallback` → `RenderItemDecorationsCallback`
  - `onDrawItemStackOverlay` → `onRenderItemDecorations`

- `net/fabricmc/fabric/api/client/rendering/v1/EntityModelLayerRegistry$TexturedEquipmentModelDataProvider` → `ModelLayerRegistry$TexturedArmorModelSetProvider`
  - `createEquipmentModelData` → `createArmorModelSet`

- `net/fabricmc/fabric/api/client/rendering/v1/EntityModelLayerRegistry$TexturedModelDataProvider` → `ModelLayerRegistry$TexturedLayerDefinitionProvider`
  - `createModelData` → `createLayerDefinition`

- `net/fabricmc/fabric/api/client/rendering/v1/EntityModelLayerRegistry` → `ModelLayerRegistry`
  - `registerEquipmentModelLayers` → `registerArmorModelLayers`

- `net/fabricmc/fabric/api/client/rendering/v1/LivingEntityFeatureRendererRegistrationCallback$RegistrationHelper` → `LivingEntityRenderLayerRegistrationCallback$RegistrationHelper`

- `net/fabricmc/fabric/api/client/rendering/v1/LivingEntityFeatureRendererRegistrationCallback` → `LivingEntityRenderLayerRegistrationCallback`
  - `registerRenderers` → `registerLayers`

- `net/fabricmc/fabric/api/client/rendering/v1/SpecialGuiElementRegistry$Context` → `PictureInPictureRendererRegistry$Context`
  - `vertexConsumers` → `bufferSource`
  - `client` → `minecraft`
  - `orderedRenderCommandQueue` → `submitNodeCollector`

- `net/fabricmc/fabric/api/client/rendering/v1/SpecialGuiElementRegistry$Factory` → `PictureInPictureRendererRegistry$Factory`
  - `createSpecialRenderer` → `createRenderer`

- `net/fabricmc/fabric/api/client/rendering/v1/SpecialGuiElementRegistry` → `PictureInPictureRendererRegistry`

- `net/fabricmc/fabric/api/client/rendering/v1/TooltipComponentCallback` → `ClientTooltipComponentCallback`
  - `getComponent` → `getClientComponent`

- `net/fabricmc/fabric/api/client/rendering/v1/hud/VanillaHudElements`
  - `STATUS_EFFECTS` → `MOB_EFFECTS`

- `net/fabricmc/fabric/api/client/rendering/v1/world/AbstractWorldRenderContext` → `net/fabricmc/fabric/api/client/rendering/v1/level/AbstractLevelRenderContext`
  - `worldRenderer` → `levelRenderer`
  - `worldState` → `levelState`

- `net/fabricmc/fabric/api/client/rendering/v1/world/WorldExtractionContext` → `net/fabricmc/fabric/api/client/rendering/v1/level/LevelExtractionContext`
  - `world` → `level`
  - `tickCounter` → `deltaTracker`

- `net/fabricmc/fabric/api/client/rendering/v1/world/WorldRenderContext` → `net/fabricmc/fabric/api/client/rendering/v1/level/LevelRenderContext`
  - `commandQueue` → `submitNodeCollector`
  - `matrices` → `poseStack`
  - `consumers` → `bufferSource`

- `net/fabricmc/fabric/api/client/rendering/v1/world/WorldRenderEvents$AfterBlockOutlineExtraction` → `net/fabricmc/fabric/api/client/rendering/v1/level/LevelRenderEvents$AfterBlockOutlineExtraction`

- `net/fabricmc/fabric/api/client/rendering/v1/world/WorldRenderEvents$AfterEntities` → `net/fabricmc/fabric/api/client/rendering/v1/level/LevelRenderEvents$AfterEntities`

- `net/fabricmc/fabric/api/client/rendering/v1/world/WorldRenderEvents$BeforeBlockOutline` → `net/fabricmc/fabric/api/client/rendering/v1/level/LevelRenderEvents$BeforeBlockOutline`

- `net/fabricmc/fabric/api/client/rendering/v1/world/WorldRenderEvents$BeforeEntities` → `net/fabricmc/fabric/api/client/rendering/v1/level/LevelRenderEvents$BeforeEntities`

- `net/fabricmc/fabric/api/client/rendering/v1/world/WorldRenderEvents$BeforeTranslucent` → `net/fabricmc/fabric/api/client/rendering/v1/level/LevelRenderEvents$BeforeTranslucent`

- `net/fabricmc/fabric/api/client/rendering/v1/world/WorldRenderEvents$DebugRender` → `net/fabricmc/fabric/api/client/rendering/v1/level/LevelRenderEvents$DebugRender`

- `net/fabricmc/fabric/api/client/rendering/v1/world/WorldRenderEvents$EndExtraction` → `net/fabricmc/fabric/api/client/rendering/v1/level/LevelRenderEvents$EndExtraction`

- `net/fabricmc/fabric/api/client/rendering/v1/world/WorldRenderEvents$EndMain` → `net/fabricmc/fabric/api/client/rendering/v1/level/LevelRenderEvents$EndMain`

- `net/fabricmc/fabric/api/client/rendering/v1/world/WorldRenderEvents$StartMain` → `net/fabricmc/fabric/api/client/rendering/v1/level/LevelRenderEvents$StartMain`

- `net/fabricmc/fabric/api/client/rendering/v1/world/WorldRenderEvents` → `net/fabricmc/fabric/api/client/rendering/v1/level/LevelRenderEvents`

- `net/fabricmc/fabric/api/client/rendering/v1/world/WorldTerrainRenderContext` → `net/fabricmc/fabric/api/client/rendering/v1/level/LevelTerrainRenderContext`
  - `sectionState` → `sectionsToRender`

- `net/fabricmc/fabric/api/client/rendering/v1/world/package-info` → `net/fabricmc/fabric/api/client/rendering/v1/level/package-info`

- `net/fabricmc/fabric/api/client/screen/v1/Screens`
  - `getButtons` → `getWidgets`
  - `getTextRenderer` → `getFont`
  - `getClient` → `getMinecraft`

- `net/fabricmc/fabric/api/command/v2/FabricEntitySelectorReader` → `FabricEntitySelectorParser`

- `net/fabricmc/fabric/api/datagen/v1/FabricDataOutput` → `FabricPackOutput`

- `net/fabricmc/fabric/api/datagen/v1/loot/FabricBlockLootTableGenerator` → `FabricBlockLootSubProvider`

- `net/fabricmc/fabric/api/datagen/v1/loot/FabricEntityLootTableGenerator` → `FabricEntityLootSubProvider`

- `net/fabricmc/fabric/api/datagen/v1/provider/FabricAdvancementProvider`
  - `pathResolver` → `pathProvider`

- `net/fabricmc/fabric/api/datagen/v1/provider/FabricBlockLootTableProvider` → `FabricBlockLootSubProvider`
  - `registryLookupFuture` → `registriesFuture`

- `net/fabricmc/fabric/api/datagen/v1/provider/FabricCodecDataProvider`
  - `pathResolver` → `pathProvider`

- `net/fabricmc/fabric/api/datagen/v1/provider/FabricDynamicRegistryProvider$RegistryEntries`
  - `entries` → `resources`

- `net/fabricmc/fabric/api/datagen/v1/provider/FabricDynamicRegistryProvider`
  - `writeRegistryEntries` → `writeHolders`
  - `lambda$writeRegistryEntries$0` → `lambda$writeHolders$0`

- `net/fabricmc/fabric/api/datagen/v1/provider/FabricEntityLootTableProvider` → `FabricEntityLootSubProvider`
  - `registryLookupFuture` → `registriesFuture`

- `net/fabricmc/fabric/api/datagen/v1/provider/FabricLanguageProvider`
  - `dataOutput` → `packOutput`

- `net/fabricmc/fabric/api/datagen/v1/provider/FabricLootTableProvider` → `FabricLootTableSubProvider`

- `net/fabricmc/fabric/api/datagen/v1/provider/FabricProvidedTagBuilder` → `FabricTagAppender`

- `net/fabricmc/fabric/api/datagen/v1/provider/FabricTagProvider$AliasGroupBuilder` → `FabricTagsProvider$AliasGroupBuilder`

- `net/fabricmc/fabric/api/datagen/v1/provider/FabricTagProvider$BlockEntityTypeTagProvider` → `FabricTagsProvider$BlockEntityTypeTagsProvider`

- `net/fabricmc/fabric/api/datagen/v1/provider/FabricTagProvider$BlockTagProvider` → `FabricTagsProvider$BlockTagsProvider`

- `net/fabricmc/fabric/api/datagen/v1/provider/FabricTagProvider$EntityTypeTagProvider` → `FabricTagsProvider$EntityTypeTagsProvider`

- `net/fabricmc/fabric/api/datagen/v1/provider/FabricTagProvider$FabricValueLookupTagProvider` → `FabricTagsProvider$FabricIntrinsicHolderTagsProvider`

- `net/fabricmc/fabric/api/datagen/v1/provider/FabricTagProvider$FluidTagProvider` → `FabricTagsProvider$FluidTagsProvider`

- `net/fabricmc/fabric/api/datagen/v1/provider/FabricTagProvider$ItemTagProvider` → `FabricTagsProvider$ItemTagsProvider`

- `net/fabricmc/fabric/api/datagen/v1/provider/FabricTagProvider` → `FabricTagsProvider`

- `net/fabricmc/fabric/api/datagen/v1/provider/SimpleFabricLootTableProvider` → `SimpleFabricLootTableSubProvider`
  - `registryLookup` → `registryLookupFuture`
  - `contextType` → `contextParamSet`

- `net/fabricmc/fabric/api/datagen/v1/recipe/FabricRecipeExporter` → `FabricRecipeOutput`

- `net/fabricmc/fabric/api/entity/FakePlayer$FakePlayerKey`
  - `world` → `level`

  - `world` → `level`

- `net/fabricmc/fabric/api/entity/event/v1/ServerEntityWorldChangeEvents$AfterEntityChange` → `ServerEntityLevelChangeEvents$AfterEntityChange`
  - `afterChangeWorld` → `afterChangeLevel`

- `net/fabricmc/fabric/api/entity/event/v1/ServerEntityWorldChangeEvents$AfterPlayerChange` → `ServerEntityLevelChangeEvents$AfterPlayerChange`
  - `afterChangeWorld` → `afterChangeLevel`

- `net/fabricmc/fabric/api/entity/event/v1/ServerEntityWorldChangeEvents` → `ServerEntityLevelChangeEvents`
  - `AFTER_ENTITY_CHANGE_WORLD` → `AFTER_ENTITY_CHANGE_LEVEL`
  - `AFTER_PLAYER_CHANGE_WORLD` → `AFTER_PLAYER_CHANGE_LEVEL`

- `net/fabricmc/fabric/api/event/lifecycle/v1/ServerChunkEvents$LevelTypeChange` → `ServerChunkEvents$FullChunkStatusChange`
  - `onChunkLevelTypeChange` → `onFullChunkStatusChange`

- `net/fabricmc/fabric/api/event/lifecycle/v1/ServerChunkEvents`
  - `CHUNK_LEVEL_TYPE_CHANGE` → `FULL_CHUNK_STATUS_CHANGE`

- `net/fabricmc/fabric/api/event/lifecycle/v1/ServerTickEvents$EndWorldTick` → `ServerTickEvents$EndLevelTick`

- `net/fabricmc/fabric/api/event/lifecycle/v1/ServerTickEvents$StartWorldTick` → `ServerTickEvents$StartLevelTick`

- `net/fabricmc/fabric/api/event/lifecycle/v1/ServerTickEvents`
  - `START_WORLD_TICK` → `START_LEVEL_TICK`
  - `END_WORLD_TICK` → `END_LEVEL_TICK`

- `net/fabricmc/fabric/api/event/lifecycle/v1/ServerWorldEvents$Load` → `ServerLevelEvents$Load`
  - `onWorldLoad` → `onLevelLoad`

- `net/fabricmc/fabric/api/event/lifecycle/v1/ServerWorldEvents$Unload` → `ServerLevelEvents$Unload`
  - `onWorldUnload` → `onLevelUnload`

- `net/fabricmc/fabric/api/event/lifecycle/v1/ServerWorldEvents` → `ServerLevelEvents`

- `net/fabricmc/fabric/api/event/registry/DynamicRegistryView`
  - `asDynamicRegistryManager` → `asRegistryAccess`

- `net/fabricmc/fabric/api/event/registry/FabricRegistryBuilder`
  - `createSimple` → `create`

- `net/fabricmc/fabric/api/gamerule/v1/FabricGameRuleVisitor` → `FabricGameRuleTypeVisitor`

- `net/fabricmc/fabric/api/item/v1/ComponentTooltipAppenderRegistry` → `ItemComponentTooltipProviderRegistry`

- `net/fabricmc/fabric/api/item/v1/CustomDamageHandler`
  - `damage` → `hurtAndBreak`

- `net/fabricmc/fabric/api/item/v1/EquipmentSlotProvider`
  - `getPreferredEquipmentSlot` → `getEquipmentSlotForItem`

- `net/fabricmc/fabric/api/item/v1/FabricItem$Settings` → `FabricItem$Properties`

- `net/fabricmc/fabric/api/item/v1/FabricItem`
  - `getRecipeRemainder` → `getCraftingRemainder`

- `net/fabricmc/fabric/api/item/v1/FabricItemStack`
  - `getRecipeRemainder` → `getCraftingRemainder`

- `net/fabricmc/fabric/api/itemgroup/v1/FabricItemGroup` → `net/fabricmc/fabric/api/creativetab/v1/FabricCreativeModeTab`

- `net/fabricmc/fabric/api/itemgroup/v1/FabricItemGroupEntries` → `net/fabricmc/fabric/api/creativetab/v1/FabricCreativeModeTabOutput`
  - `addAfter` → `insertAfter`
  - `addBefore` → `insertBefore`

- `net/fabricmc/fabric/api/itemgroup/v1/ItemGroupEvents$ModifyEntries` → `net/fabricmc/fabric/api/creativetab/v1/CreativeModeTabEvents$ModifyOutput`
  - `modifyEntries` → `modifyOutput`

- `net/fabricmc/fabric/api/itemgroup/v1/ItemGroupEvents$ModifyEntriesAll` → `net/fabricmc/fabric/api/creativetab/v1/CreativeModeTabEvents$ModifyOutputAll`
  - `modifyEntries` → `modifyOutput`

- `net/fabricmc/fabric/api/itemgroup/v1/ItemGroupEvents` → `net/fabricmc/fabric/api/creativetab/v1/CreativeModeTabEvents`
  - `modifyEntriesEvent` → `modifyOutputEvent`

  - `MODIFY_ENTRIES_ALL` → `MODIFY_OUTPUT_ALL`

- `net/fabricmc/fabric/api/itemgroup/v1/package-info` → `net/fabricmc/fabric/api/creativetab/v1/package-info`

- `net/fabricmc/fabric/api/lookup/v1/block/BlockApiCache`
  - `getWorld` → `getLevel`

- `net/fabricmc/fabric/api/loot/v3/FabricLootPoolBuilder`
  - `with` → `add`
  - `conditionally` → `when`

- `net/fabricmc/fabric/api/networking/v1/FabricServerConfigurationNetworkHandler` → `FabricServerConfigurationPacketListenerImpl`

- `net/fabricmc/fabric/api/networking/v1/PacketByteBufs` → `FriendlyByteBufs`
  - `EMPTY_PACKET_BYTE_BUF` → `EMPTY_FRIENDLY_BYTE_BUF`

- `net/fabricmc/fabric/api/networking/v1/PayloadTypeRegistry`
  - `configurationC2S` → `serverboundConfiguration`
  - `configurationS2C` → `clientboundConfiguration`
  - `playC2S` → `serverboundPlay`
  - `playS2C` → `clientboundPlay`

- `net/fabricmc/fabric/api/networking/v1/PlayerLookup`
  - `world` → `level`

- `net/fabricmc/fabric/api/networking/v1/S2CConfigurationChannelEvents$Register` → `ClientboundConfigurationChannelEvents$Register`

- `net/fabricmc/fabric/api/networking/v1/S2CConfigurationChannelEvents$Unregister` → `ClientboundConfigurationChannelEvents$Unregister`

- `net/fabricmc/fabric/api/networking/v1/S2CConfigurationChannelEvents` → `ClientboundConfigurationChannelEvents`

- `net/fabricmc/fabric/api/networking/v1/S2CPlayChannelEvents$Register` → `ClientboundPlayChannelEvents$Register`

- `net/fabricmc/fabric/api/networking/v1/S2CPlayChannelEvents$Unregister` → `ClientboundPlayChannelEvents$Unregister`

- `net/fabricmc/fabric/api/networking/v1/S2CPlayChannelEvents` → `ClientboundPlayChannelEvents`

- `net/fabricmc/fabric/api/networking/v1/ServerConfigurationNetworking$Context`
  - `networkHandler` → `packetListener`

- `net/fabricmc/fabric/api/networking/v1/ServerConfigurationNetworking`
  - `createS2CPacket` → `createClientboundPacket`

- `net/fabricmc/fabric/api/networking/v1/ServerPlayNetworking`
  - `createS2CPacket` → `createClientboundPacket`

- `net/fabricmc/fabric/api/object/builder/v1/block/entity/FabricBlockEntityType`
  - `addSupportedBlock` → `addValidBlock`

- `net/fabricmc/fabric/api/object/builder/v1/block/type/BlockSetTypeBuilder`
  - `soundGroup` → `soundType`

  - `soundGroup` → `soundType`

- `net/fabricmc/fabric/api/object/builder/v1/block/type/WoodTypeBuilder`
  - `soundGroup` → `soundType`

  - `hangingSignSoundGroup` → `hangingSignSoundType`

  - `soundGroup` → `soundType`

  - `hangingSignSoundGroup` → `hangingSignSoundType`

- `net/fabricmc/fabric/api/object/builder/v1/entity/FabricEntityType$Builder$Mob`
  - `spawnRestriction` → `spawnPlacement`

- `net/fabricmc/fabric/api/object/builder/v1/entity/FabricEntityTypeBuilder$Living`
  - `spawnGroup` → `mobCategory`

- `net/fabricmc/fabric/api/object/builder/v1/entity/FabricEntityTypeBuilder$Mob`
  - `spawnGroup` → `mobCategory`

  - `spawnRestriction` → `spawnPlacement`

  - `spawnLocation` → `spawnPlacementType`

  - `restrictionHeightmap` → `placementHeightmap`

- `net/fabricmc/fabric/api/object/builder/v1/entity/FabricEntityTypeBuilder`
  - `spawnGroup` → `mobCategory`

  - `spawnGroup` → `mobCategory`

- `net/fabricmc/fabric/api/object/builder/v1/entity/FabricTrackedDataRegistry` → `FabricEntityDataRegistry`

- `net/fabricmc/fabric/api/object/builder/v1/world/poi/PointOfInterestHelper` → `PoiHelper`

- `net/fabricmc/fabric/api/particle/v1/FabricBlockStateParticleEffect` → `FabricBlockParticleOption`

- `net/fabricmc/fabric/api/recipe/v1/FabricRecipeManager` → `FabricRecipeAccess`

- `net/fabricmc/fabric/api/recipe/v1/FabricServerRecipeManager` → `FabricRecipeManager`

- `net/fabricmc/fabric/api/recipe/v1/ingredient/CustomIngredient`
  - `getMatchingItems` → `items`
  - `toDisplay` → `display`

- `net/fabricmc/fabric/api/recipe/v1/ingredient/CustomIngredientSerializer`
  - `getPacketCodec` → `getStreamCodec`

- `net/fabricmc/fabric/api/registry/CompostingChanceRegistry` → `CompostableRegistry`

- `net/fabricmc/fabric/api/registry/FabricBrewingRecipeRegistryBuilder$BuildCallback` → `FabricPotionBrewingBuilder$BuildCallback`

- `net/fabricmc/fabric/api/registry/FabricBrewingRecipeRegistryBuilder` → `FabricPotionBrewingBuilder`

- `net/fabricmc/fabric/api/registry/FlammableBlockRegistry$Entry`
  - `getBurnChance` → `getIgniteOdds`

  - `getSpreadChance` → `getBurnOdds`

  - `burn` → `igniteOdds`

  - `spread` → `burnOdds`

- `net/fabricmc/fabric/api/registry/FuelRegistryEvents$BuildCallback` → `FuelValueEvents$BuildCallback`

- `net/fabricmc/fabric/api/registry/FuelRegistryEvents$Context` → `FuelValueEvents$Context`

- `net/fabricmc/fabric/api/registry/FuelRegistryEvents$ExclusionsCallback` → `FuelValueEvents$ExclusionsCallback`

- `net/fabricmc/fabric/api/registry/FuelRegistryEvents` → `FuelValueEvents`

- `net/fabricmc/fabric/api/registry/LandPathNodeTypesRegistry$DynamicPathNodeTypeProvider` → `LandPathTypeRegistry$DynamicPathTypeProvider`
  - `getPathNodeType` → `getPathType`

- `net/fabricmc/fabric/api/registry/LandPathNodeTypesRegistry$PathNodeTypeProvider` → `LandPathTypeRegistry$PathTypeProvider`

- `net/fabricmc/fabric/api/registry/LandPathNodeTypesRegistry$StaticPathNodeTypeProvider` → `LandPathTypeRegistry$StaticPathTypeProvider`
  - `getPathNodeType` → `getPathType`

- `net/fabricmc/fabric/api/registry/LandPathNodeTypesRegistry` → `LandPathTypeRegistry`
  - `getPathNodeType` → `getPathType`

  - `getPathNodeTypeProvider` → `getPathTypeProvider`

  - `NODE_TYPES` → `PATH_TYPES`

- `net/fabricmc/fabric/api/registry/OxidizableBlocksRegistry`
  - `registerOxidizableBlockPair` → `registerNextStage`
  - `registerWaxableBlockPair` → `registerWaxable`
  - `registerCopperBlockSet` → `registerWeatheringCopperBlocks`

- `net/fabricmc/fabric/api/registry/SculkSensorFrequencyRegistry` → `VibrationFrequencyRegistry`

- `net/fabricmc/fabric/api/registry/VillagerInteractionRegistries`
  - `registerCollectable` → `registerGatherableItem`

- `net/fabricmc/fabric/api/renderer/v1/mesh/MutableQuadView`
  - `renderLayer` → `chunkLayer`
  - `glint` → `foilType`

- `net/fabricmc/fabric/api/renderer/v1/mesh/QuadEmitter`
  - `renderLayer` → `chunkLayer`
  - `glint` → `foilType`

- `net/fabricmc/fabric/api/renderer/v1/mesh/QuadView`
  - `renderLayer` → `chunkLayer`
  - `glint` → `foilType`

- `net/fabricmc/fabric/api/renderer/v1/model/FabricBlockModels` → `FabricBlockModelShaper`
  - `getModelParticleSprite` → `getParticleIcon`

- `net/fabricmc/fabric/api/renderer/v1/model/FabricBlockStateModel`
  - `particleSprite` → `particleIcon`

- `net/fabricmc/fabric/api/renderer/v1/model/MeshBakedGeometry` → `MeshQuadCollection`

- `net/fabricmc/fabric/api/renderer/v1/model/ModelBakeSettingsHelper` → `ModelStateHelper`

- `net/fabricmc/fabric/api/renderer/v1/render/BlockVertexConsumerProvider` → `BlockMultiBufferSource`

- `net/fabricmc/fabric/api/renderer/v1/render/FabricBlockModelRenderer` → `FabricModelBlockRenderer`

- `net/fabricmc/fabric/api/renderer/v1/render/FabricBlockRenderManager` → `FabricBlockRenderDispatcher`

- `net/fabricmc/fabric/api/renderer/v1/render/FabricRenderCommandQueue` → `FabricOrderedSubmitNodeCollector`

- `net/fabricmc/fabric/api/renderer/v1/render/RenderLayerHelper` → `ChunkSectionLayerHelper`

- `net/fabricmc/fabric/api/renderer/v1/sprite/FabricErrorCollectingSpriteGetter` → `FabricSpriteGetter`

- `net/fabricmc/fabric/api/renderer/v1/sprite/FabricSpriteAtlasTexture` → `FabricPreparations`

- `net/fabricmc/fabric/api/renderer/v1/sprite/FabricStitchResult` → `FabricTextureAtlas`

- `net/fabricmc/fabric/api/resource/v1/DataResourceLoader`
  - `registerReloader` → `registerReloadListener`

- `net/fabricmc/fabric/api/resource/v1/ResourceLoader`
  - `registerReloader` → `registerReloadListener`

  - `addReloaderOrdering` → `addListenerOrdering`

  - `RELOADER_REGISTRY_LOOKUP_KEY` → `REGISTRY_LOOKUP_KEY`

  - `RELOADER_FEATURE_SET_KEY` → `FEATURE_FLAG_SET_KEY`

- `net/fabricmc/fabric/api/resource/v1/reloader/ResourceReloaderKeys$Client`
  - `BLOCK_ENTITY_RENDERERS` → `BLOCK_ENTITY_RENDER_DISPATCHER`
  - `BLOCK_RENDER_MANAGER` → `BLOCK_RENDER_DISPATCHER`
  - `CLOUD_CELLS` → `CLOUD_RENDERER`
  - `EQUIPMENT_MODELS` → `EQUIPMENT_ASSETS`
  - `ENTITY_RENDERERS` → `ENTITY_RENDER_DISPATCHER`
  - `DRY_FOLIAGE_COLORMAP` → `DRY_FOLIAGE_COLOR`
  - `FOLIAGE_COLORMAP` → `FOLIAGE_COLOR`
  - `GRASS_COLORMAP` → `GRASS_COLOR`
  - `WAYPOINT_STYLE_ASSETS` → `WAYPOINT_STYLE`

- `net/fabricmc/fabric/api/resource/v1/reloader/SimpleResourceReloader` → `SimpleReloadListener`

- `net/fabricmc/fabric/api/screenhandler/v1/ExtendedScreenHandlerFactory` → `net/fabricmc/fabric/api/menu/v1/ExtendedMenuProvider`

- `net/fabricmc/fabric/api/screenhandler/v1/ExtendedScreenHandlerType$ExtendedFactory` → `net/fabricmc/fabric/api/menu/v1/ExtendedMenuType$ExtendedFactory`

- `net/fabricmc/fabric/api/screenhandler/v1/ExtendedScreenHandlerType` → `net/fabricmc/fabric/api/menu/v1/ExtendedMenuType`
  - `getPacketCodec` → `getStreamCodec`

  - `packetCodec` → `streamCodec`

- `net/fabricmc/fabric/api/screenhandler/v1/FabricScreenHandlerFactory` → `net/fabricmc/fabric/api/menu/v1/FabricMenuProvider`

- `net/fabricmc/fabric/api/screenhandler/v1/package-info` → `net/fabricmc/fabric/api/menu/v1/package-info`

- `net/fabricmc/fabric/api/serialization/v1/view/FabricReadView` → `net/fabricmc/fabric/api/serialization/v1/value/FabricValueInput`
  - `keys` → `keySet`

- `net/fabricmc/fabric/api/serialization/v1/view/FabricWriteView` → `net/fabricmc/fabric/api/serialization/v1/value/FabricValueOutput`

- `net/fabricmc/fabric/api/serialization/v1/view/package-info` → `net/fabricmc/fabric/api/serialization/v1/value/package-info`

- `net/fabricmc/fabric/api/transfer/v1/fluid/FluidVariant`
  - `getRegistryEntry` → `typeHolder`
  - `withComponentChanges` → `withComponents`

- `net/fabricmc/fabric/api/transfer/v1/fluid/FluidVariantAttributeHandler`
  - `getLuminance` → `getLightEmission`

- `net/fabricmc/fabric/api/transfer/v1/fluid/base/SingleFluidStorage`
  - `readData` → `readValue`
  - `writeData` → `writeValue`

- `net/fabricmc/fabric/api/transfer/v1/item/InventoryStorage` → `ContainerStorage`

- `net/fabricmc/fabric/api/transfer/v1/item/ItemVariant`
  - `getRegistryEntry` → `typeHolder`
  - `withComponentChanges` → `withComponents`

- `net/fabricmc/fabric/api/transfer/v1/item/base/SingleItemStorage`
  - `readData` → `readValue`
  - `writeData` → `writeValue`

- `net/fabricmc/fabric/api/transfer/v1/storage/StorageUtil`
  - `calculateComparatorOutput` → `getRedstoneSignal`

- `net/fabricmc/fabric/api/transfer/v1/storage/TransferVariant`
  - `getComponents` → `getComponentsPatch`
  - `getComponentMap` → `getComponents`
  - `withComponentChanges` → `withComponents`

- `net/fabricmc/fabric/api/transfer/v1/storage/base/SingleVariantStorage`
  - `readData` → `readValue`
  - `writeData` → `writeValue`
