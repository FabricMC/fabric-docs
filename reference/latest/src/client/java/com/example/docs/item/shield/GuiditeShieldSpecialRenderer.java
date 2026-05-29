package com.example.docs.item.shield;

import java.util.Objects;
import java.util.function.Consumer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Transformation;
import com.mojang.serialization.MapCodec;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.jspecify.annotations.Nullable;

import net.minecraft.client.model.object.equipment.ShieldModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BannerRenderer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.client.resources.model.sprite.SpriteGetter;
import net.minecraft.client.resources.model.sprite.SpriteId;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.Unit;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BannerPatternLayers;

// #region renderer
public class GuiditeShieldSpecialRenderer implements SpecialModelRenderer<DataComponentMap> {
	// The offset applied to the model by default
	public static final Transformation DEFAULT_TRANSFORMATION = new Transformation(null, null, new Vector3f(1.0F, -1.0F, -1.0F), null);

	// What sprites should be used.
	private final SpriteGetter sprites;
	// What model should be used.
	private final ShieldModel model;

	public GuiditeShieldSpecialRenderer(final SpriteGetter sprites, final ShieldModel model) {
		this.sprites = sprites;
		this.model = model;
	}
	// #endregion renderer

	@Override
	// #region extract-argument
	public @Nullable DataComponentMap extractArgument(final ItemStack stack) {
		return stack.immutableComponents();
	}
	// #endregion extract-argument

	@Override
	// #region submit
	public void submit(final @Nullable DataComponentMap components, final PoseStack poseStack, final SubmitNodeCollector submitNodeCollector, final int lightCoords, final int overlayCoords, final boolean hasFoil, final int outlineColor) {
		BannerPatternLayers patterns = components != null ? components.getOrDefault(DataComponents.BANNER_PATTERNS, BannerPatternLayers.EMPTY) : BannerPatternLayers.EMPTY;
		DyeColor baseColor = components != null ? components.get(DataComponents.BASE_COLOR) : null;
		boolean hasPatterns = !patterns.layers().isEmpty() || baseColor != null;
		SpriteId base = hasPatterns ? GuiditeShieldLayers.GUIDITE_SHIELD_BASE : GuiditeShieldLayers.GUIDITE_SHIELD_BASE_NO_PATTERN;
		submitNodeCollector.submitModel(this.model, Unit.INSTANCE, poseStack, lightCoords, overlayCoords, -1, base, this.sprites, outlineColor, null);
		if (hasPatterns) {
			BannerRenderer.submitPatterns(this.sprites, poseStack, submitNodeCollector, lightCoords, overlayCoords, this.model, Unit.INSTANCE, false, Objects.requireNonNullElse(baseColor, DyeColor.WHITE), patterns, null);
		}

		if (hasFoil) {
			submitNodeCollector.submitModel(this.model, Unit.INSTANCE, poseStack, RenderTypes.entityGlint(), lightCoords, overlayCoords, -1, this.sprites.get(base), 0, null);
		}

	}
	// #endregion submit

	@Override
	// #region extents
	public void getExtents(final Consumer<Vector3fc> output) {
		PoseStack poseStack = new PoseStack();
		this.model.root().getExtentsForGui(poseStack, output);
	}
	// #endregion extents

	// #region unbaked
	public record Unbaked() implements SpecialModelRenderer.Unbaked<DataComponentMap> {
		public static final Unbaked INSTANCE = new Unbaked();
		public static final MapCodec<Unbaked> MAP_CODEC = MapCodec.unit(INSTANCE);

		public MapCodec<Unbaked> type() {
			return MAP_CODEC;
		}

		public GuiditeShieldSpecialRenderer bake(final SpecialModelRenderer.BakingContext context) {
			return new GuiditeShieldSpecialRenderer(context.sprites(), new ShieldModel(context.entityModelSet().
							bakeLayer(GuiditeShieldLayers.GUIDITE_SHIELD)));
		}
	}
	// #endregion unbaked
// #region renderer
}
// #endregion renderer
