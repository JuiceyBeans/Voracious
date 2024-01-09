package com.juiceybeans.voracious.item;

import com.juiceybeans.voracious.Voracious;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

public class ModItems {
    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(Voracious.MOD_ID, name), item);
    }

    public static final Item MOUTH = registerItem("mouth", new MouthItem(new FabricItemSettings().maxCount(1).food(ModFoodComponents.MOUTH)));

    public static void addToItemGroup(RegistryKey<ItemGroup> group, Item item) {
        ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.add(item));
    }
    public static void registerModItems() {
        Voracious.LOGGER.info("[Voracious] You have been given a mouth.");
        addToItemGroup(ItemGroups.TOOLS, MOUTH);
    }
}
