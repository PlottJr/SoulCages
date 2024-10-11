package com.plott.soul_cage.items;

import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

public class SoulCageEventHandler {

    public static void registerEntityInteractionListener() {
        UseEntityCallback.EVENT.register((PlayerEntity player, World world, Hand hand, Entity entity, EntityHitResult hitResult) -> {
            // Get the item in the player's hand
            ItemStack stack = player.getStackInHand(hand);

            // Check if the target entity is a player entity, and if so, ignore the interaction
            if (entity instanceof PlayerEntity) {
                return ActionResult.PASS; // Do nothing if the target is a player
            }

            // Continue if the entity is a LivingEntity and not a player
            if (entity instanceof LivingEntity targetEntity) {

                // Check if the item is a reusable SoulCage
                if (stack.getItem() instanceof SoulCageReusableItem reusableSoulCage) {
                    ActionResult result = reusableSoulCage.captureEntity(stack, player, targetEntity, hand);
                    if (result == ActionResult.SUCCESS) {
                        return ActionResult.SUCCESS;
                    }
                }
                // Check if the item is a single-use SoulCage
                else if (stack.getItem() instanceof SoulCageSingleUseItem singleUseSoulCage) {
                    ActionResult result = singleUseSoulCage.captureEntity(stack, player, targetEntity, hand);
                    if (result == ActionResult.SUCCESS) {
                        return ActionResult.SUCCESS;
                    }
                }
            }

            return ActionResult.PASS;
        });
    }
}
