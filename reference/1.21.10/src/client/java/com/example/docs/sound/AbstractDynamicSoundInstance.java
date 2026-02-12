package com.example.docs.sound;

import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;

import com.example.docs.sound.instance.SoundInstanceCallback;

// :::1
public abstract class AbstractDynamicSoundInstance extends AbstractTickableSoundInstance {
	protected final DynamicSoundSource soundSource;                 // Entities, BlockEntities, ...
	protected TransitionState transitionState;                      // current TransitionState of the SoundInstance

	protected final int startTransitionTicks, endTransitionTicks;   // duration of starting and ending phases

	// possible volume range when adjusting sound values
	protected final float maxVolume;                                // only max value since the minimum is always 0
	// possible pitch range when adjusting sound values
	protected final float minPitch, maxPitch;

	protected int currentTick = 0, transitionTick = 0;              // current tick values for the instance

	protected final SoundInstanceCallback callback;                 // callback for soundInstance states

	// ...
	// :::1

	// :::2
	// ...

	// set up default settings of the SoundInstance in this constructor
	protected AbstractDynamicSoundInstance(DynamicSoundSource soundSource, SoundEvent soundEvent, SoundSource soundCategory,
										int startTransitionTicks, int endTransitionTicks, float maxVolume, float minPitch, float maxPitch,
										SoundInstanceCallback callback) {
		super(soundEvent, soundCategory, SoundInstance.createUnseededRandom());

		// store important references to other objects
		this.soundSource = soundSource;
		this.callback = callback;

		// store the limits for the SoundInstance
		this.maxVolume = maxVolume;
		this.minPitch = minPitch;
		this.maxPitch = maxPitch;
		this.startTransitionTicks = startTransitionTicks;    // starting phase duration
		this.endTransitionTicks = endTransitionTicks;        // ending phase duration

		// set start values
		this.volume = 0.0f;
		this.pitch = minPitch;
		this.looping = true;
		this.transitionState = TransitionState.STARTING;
		this.setPositionToEntity();
	}

	// ...
	// :::2

	// :::3
	@Override
	public boolean canStartSilent() {
		// override to true, so that the SoundInstance can start
		// or add your own condition to the SoundInstance, if necessary
		return true;
	}

	// :::3

	// :::4
	@Override
	public void tick() {
		// handle states where sound might be actually stopped instantly
		if (this.soundSource == null) {
			this.callback.onFinished(this);
		}

		// basic tick behaviour
		this.currentTick++;
		this.setPositionToEntity();

		// SoundInstance phase switching
		switch (this.transitionState) {
			case STARTING -> {
				this.transitionTick++;

				// go into next phase if starting phase finished its duration
				if (this.transitionTick > this.startTransitionTicks) {
					this.transitionTick = 0;	// reset tick for future ending phase
					this.transitionState = TransitionState.RUNNING;
				}
			}
			case ENDING -> {
				this.transitionTick++;

				// set SoundInstance as finished if ending phase finished its duration
				if (this.transitionTick > this.endTransitionTicks) {
					this.callback.onFinished(this);
				}
			}
		}

		// apply volume and pitch modulation here,
		// if you use a normal SoundInstance class
	}

	// :::4

	// :::5
	// increase or decrease volume and pitch based on the current phase of the sound
	protected void modulateSoundForTransition() {
		float normalizedTick = switch (transitionState) {
			case STARTING -> (float) this.transitionTick / this.startTransitionTicks;
			case ENDING -> 1.0f - ((float) this.transitionTick / this.endTransitionTicks);
			default -> 1.0f;
		};

		this.volume = Mth.lerp(normalizedTick, 0.0f, this.maxVolume);
	}

	// increase or decrease pitch based on the sound source's stress value
	protected void modulateSoundForStress() {
		this.pitch = Mth.lerp(this.soundSource.getNormalizedStress(), this.minPitch, this.maxPitch);
	}

	// :::5

	// :::6
	// moves the sound instance position to the sound source's position
	protected void setPositionToEntity() {
		this.x = soundSource.getPosition().x();
		this.y = soundSource.getPosition().y();
		this.z = soundSource.getPosition().z();
	}

	// Sets the SoundInstance into its ending phase.
	// This is especially useful for external access to this SoundInstance
	public void end() {
		this.transitionState = TransitionState.ENDING;
	}

	// :::6
	// :::1
}
// :::1
