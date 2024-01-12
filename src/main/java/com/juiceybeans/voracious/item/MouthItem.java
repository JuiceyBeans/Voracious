package com.juiceybeans.voracious.item;

import com.juiceybeans.voracious.Voracious;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

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

        if (player.getWorld().isClient()) {
            if (player.getItemCooldownManager().isCoolingDown(ModItems.MOUTH)) {
                player.sendMessage(Text.translatable("message.voracious.chew"));
            }
            if (!(player.getItemCooldownManager().isCoolingDown(ModItems.MOUTH))) {
                if ((target instanceof IronGolemEntity) || (target instanceof TurtleEntity) || (target instanceof AbstractSkeletonEntity) || (target instanceof SkeletonHorseEntity)) {
                    player.sendMessage(Text.translatable("message.voracious.ow"));
                } else if (target instanceof BatEntity) {
                    player.sendMessage(Text.translatable("message.voracious.bat"));
                } else {
                    player.sendMessage(Text.translatable("message.voracious.nom"));
                }
            }
        }

        if (!(player.getWorld().isClient())) {
            if (!(player.getItemCooldownManager().isCoolingDown(ModItems.MOUTH))) {
                hp += target.getHealth();

                if ((target instanceof IronGolemEntity) || (target instanceof TurtleEntity) || (target instanceof AbstractSkeletonEntity) || !(target instanceof SkeletonHorseEntity)) {
                    player.damage(player.getDamageSources().generic(), 1F);
                    target.damage(player.getDamageSources().playerAttack(player), 2F);
                } else if (hp > 8F) {
                    target.damage(player.getDamageSources().playerAttack(player), 8F);
                }

                if ((target instanceof HostileEntity) && !(target instanceof AbstractSkeletonEntity) && !(target instanceof SkeletonHorseEntity)) {
                    player.heal(hp / 4);
                } else if (!(target instanceof HostileEntity) && !(target instanceof SkeletonHorseEntity)) {
                    player.heal(hp / 2);
                }

                player.getWorld().playSound(player, player.getBlockPos(), SoundEvents.ENTITY_GENERIC_EAT, SoundCategory.PLAYERS);
                player.getItemCooldownManager().set(this, 20);

                if ((target instanceof ZombieEntity) || (target instanceof SpiderEntity) || (target instanceof SkeletonEntity) || (target instanceof ZoglinEntity) || (target instanceof ZombieHorseEntity) || (target instanceof BatEntity) || (target instanceof ChickenEntity) || (target instanceof ParrotEntity)) {
                    Voracious.LOGGER.info("[DEBUG] Applying poison");
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 200, 1), target);
                }
                if (target instanceof PufferfishEntity) {
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 300, 0), target);
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 300, 1), target);
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 300, 2), target);
                }
                if (target instanceof WitherSkeletonEntity) {
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 200, 0), target);
                }
                if (target instanceof WitherEntity) {
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 200, 2), target);
                }
            }
        }
        return ActionResult.PASS;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("tooltip.voracious.mouth"));
        super.appendTooltip(stack, world, tooltip, context);
    }
}