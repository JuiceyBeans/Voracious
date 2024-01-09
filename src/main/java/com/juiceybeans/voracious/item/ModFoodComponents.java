package com.juiceybeans.voracious.item;

import net.minecraft.item.FoodComponent;

public class ModFoodComponents {
    public static final FoodComponent MOUTH = new FoodComponent.Builder()
            .hunger(3).saturationModifier(1F).meat().build();
}
