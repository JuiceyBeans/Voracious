package com.juiceybeans.voracious.item;

import com.juiceybeans.voracious.Voracious;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.LiteralTextContent;
import net.minecraft.text.Text;
import net.minecraft.text.TextContent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TranslatableOption;
import net.minecraft.util.Util;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.List;

public class MouthItem extends Item {
    public MouthItem(Settings settings) {
        super(settings);
    }

    float hp = 0F;
    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity player, LivingEntity target, Hand hand) {

        Voracious.LOGGER.info("[DEBUG] Right clicked an entity", hp);

        /*if (stack.hasNbt()) {
            hp = stack.getNbt().getFloat("voracious.stored_hp");
            stack.setNbt(new NbtCompound());
        };*/

        if ((target instanceof LivingEntity) & (player.getWorld().isClient())) {
            if (player.getItemCooldownManager().isCoolingDown(ModItems.MOUTH)) {
                player.sendMessage(Text.translatable("message.voracious.chew"));
            }
            if (!(player.getItemCooldownManager().isCoolingDown(ModItems.MOUTH))) {
                player.sendMessage(Text.translatable("message.voracious.nom"));
            }
        }

        if ((target instanceof LivingEntity) & !(player.getItemCooldownManager().isCoolingDown(ModItems.MOUTH))) {
            hp += target.getHealth();
            target.kill();
            player.getServer().getCommandManager().execute(, "kill @e[type=item]");
            player.heal(hp/2);
            player.getWorld().playSound(player, player.getBlockPos(), SoundEvents.ENTITY_GENERIC_EAT, SoundCategory.PLAYERS);
            player.getItemCooldownManager().set(this, 1200);
        }
        return ActionResult.PASS;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("tooltip.voracious.mouth"));
        super.appendTooltip(stack, world, tooltip, context);
    }

    /*String HP = Float.toString(hp);
    private void addNbtToMouth(PlayerEntity player, ItemStack stack) {
        NbtCompound nbtData = new NbtCompound();
        nbtData.putString("voracious.stored_hp", "HP stored: " + HP);

    }*/

    int stored_hp = 0;
    public final FoodComponent MouthItem = new FoodComponent.Builder()
            .hunger(stored_hp)
            .build();
}
