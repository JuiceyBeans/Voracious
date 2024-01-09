package com.juiceybeans.voracious;

import com.juiceybeans.voracious.item.ModItems;
import net.fabricmc.api.ModInitializer;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class Voracious implements ModInitializer {

    public static final String MOD_ID = "voracious";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    @Override
    public void onInitialize() {
        ModItems.registerModItems();
    }
}
