package com.example.docs.sound.instance;

import com.example.docs.sound.AbstractDynamicSoundInstance;

// #region sound-instance-callback
public interface SoundInstanceCallback {
	// deliver the custom SoundInstance, from which this signal originates,
	// using the method parameters
	<T extends AbstractDynamicSoundInstance> void onFinished(T soundInstance);
}
// #endregion sound-instance-callback
