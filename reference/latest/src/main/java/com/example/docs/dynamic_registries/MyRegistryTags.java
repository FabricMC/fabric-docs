package com.example.docs.dynamic_registries;

import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tags.TagKey;


public class MyRegistryTags {

		// #region tag
		public static final TagKey<MyModRegistryEntry> MY_TAG_KEY = TagKey.create(MyRegistries.MY_REGISTRY_KEY, Identifier.fromNamespaceAndPath("example-mod","mytag"));

		public static boolean isPresentInMyTag(MinecraftServer server, Identifier entryId) {
				var registry = server.registryAccess().lookup(MyRegistries.MY_REGISTRY_KEY).orElse(null);
				if (registry == null) return false;

				var reference = registry.get(entryId).orElse(null);

				return reference != null && reference.is(MY_TAG_KEY);
		}
		// #endregion tag
}
