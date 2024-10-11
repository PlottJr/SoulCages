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
            ItemStack stack = player.getStackInHand(hand);

            if (entity instanceof LivingEntity targetEntity) {

                if (stack.getItem() instanceof SoulCageReusableItem reusableSoulCage) {
                    ActionResult result = reusableSoulCage.captureEntity(stack, player, targetEntity, hand);
                    if (result == ActionResult.SUCCESS) {
                        return ActionResult.SUCCESS;
                    }
                } else if (stack.getItem() instanceof SoulCageSingleUseItem singleUseSoulCage) {
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
