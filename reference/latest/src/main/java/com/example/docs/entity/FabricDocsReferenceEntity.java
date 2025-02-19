package com.example.docs.entity;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.fabricmc.api.ModInitializer;
import com.example.docs.entity.FabricDocsReferanceCustomEntity;


public class FabricDocsReferenceEntity implements ModInitializer {
 
	public static final String MOD_ID = "fabric-docs-reference";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		FabricDocsReferanceCustomEntity.registerCustomEntity();
	}
}
