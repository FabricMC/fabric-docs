package com.example.docs.particle;

import net.minecraft.util.Ease;

import org.jspecify.annotations.NonNull;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;

public class SparklySparkleParticle extends SingleQuadParticle {
	public final SpriteSet sprites;
	public boolean animate = true;

	public boolean flipRoll = false;
	public int shrinkTicks = 0;
	public float maxQuadSize = 0f;

	protected SparklySparkleParticle(ClientLevel level, double x, double y, double z, double xa, double ya, double za, SpriteSet sprites) {
		// Even though we change the texture shortly after, we need to
		// use sprites.first() in the super constructor as a fallback texture
		super(level, x, y, z, xa, ya, za, sprites.first());
		this.sprites = sprites;

		// Move the particle in a random direction
		this.xd = (this.random.nextFloat() - 0.5f) * 0.1f; // -0.1f - 0.1f
		this.yd = (this.random.nextFloat() - 0.5f) * 0.1f; // -0.1f - 0.1f
		this.zd = (this.random.nextFloat() - 0.5f) * 0.1f; // -0.1f - 0.1f
		this.friction = 0.95f;

		this.lifetime = this.random.nextIntBetweenInclusive(80, 100); // 80 - 100
		this.shrinkTicks = 20;
		this.maxQuadSize = this.quadSize;
		this.flipRoll = this.random.nextBoolean();


		this.animate = this.random.nextBoolean();
		if (this.animate) {
			// Set the texture based on the particle's age (from here, it'll be the first texture)
			this.setSpriteFromAge(this.sprites);
		} else {
			// Set the texture based on a random sprite
			this.setSprite(this.sprites.get(this.random));
		}
	}

	@Override
	public void tick() {
		// Remove the particle if it has fully shrunk
		if (this.quadSize <= 0f) {
			this.remove();
			return;
		}

		this.oRoll = this.roll; // Update the oRoll (previous roll) value
		super.tick();
		if (this.animate) {
			this.setSpriteFromAge(this.sprites);
		}

		float distance = (float) Math.sqrt(this.xd * this.xd + this.yd * this.yd + this.zd * this.zd);
		this.roll += (distance * 0.5f) * (this.flipRoll ? -1f : 1f);

		if (this.age >= this.lifetime - this.shrinkTicks) {
			float ageDelta = (float) (this.age - this.lifetime + this.shrinkTicks) / this.shrinkTicks;
			float easeAmount = Ease.inSine(ageDelta); // 0f to 1f, but eased in instead of being linear
			this.quadSize = this.maxQuadSize * (1f - easeAmount); // Reverse easeAmount to go from 1f to 0f
		}
	}

	@Override
	protected @NonNull Layer getLayer() {
		// We're using Layer.OPAQUE because our particle's textures are not be semi-transparent
		// Use Layer.TRANSLUCENT if your particle's texture has semi-transparent pixels
		return Layer.OPAQUE;
	}

	public record Provider(SpriteSet sprites) implements ParticleProvider<SimpleParticleType> {
		@Override
		public @NonNull Particle createParticle(SimpleParticleType options, @NonNull ClientLevel level, double x, double y, double z, double xAux, double yAux, double zAux, @NonNull RandomSource random) {
			return new SparklySparkleParticle(level, x, y, z, xAux, yAux, zAux, this.sprites);
		}
	}
}
