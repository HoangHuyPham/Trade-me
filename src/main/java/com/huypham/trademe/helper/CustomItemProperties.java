package com.huypham.trademe.helper;

import com.huypham.trademe.Main;
import com.huypham.trademe.item.Items;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class CustomItemProperties {
    public static void register() {
        makeCustomBow();
    }

    static void makeCustomBow(){
        ItemProperties.register(Items.MYSTIC_BOW_ITEM.get(), new ResourceLocation(Main.MODID, "pull"), ((pStack, pLevel, pEntity, pSeed) -> {
            if (pEntity == null) {
                return 0.0F;
            } else {
                return pEntity.getUseItem() != pStack ? 0.0F : (float) (pStack.getUseDuration() - pEntity.getUseItemRemainingTicks()) / 20.0F;
            }
        }));

        ItemProperties.register(Items.MYSTIC_BOW_ITEM.get(), new ResourceLocation(Main.MODID,"pulling"), (pStack, pLevel, pEntity, pSeed) -> pEntity != null && pEntity.isUsingItem() && pEntity.getUseItem() == pStack ? 1.0F : 0.0F);

        ItemProperties.register(Items.MYSTIC_BOW_ITEM.get(), new ResourceLocation(Main.MODID, "firework"), (pStack, pLevel, pEntity, pSeed) -> {
            if (pEntity instanceof Player player)
                return player.getProjectile(pStack).is(net.minecraft.world.item.Items.FIREWORK_ROCKET) ? 1.0F : 0.0F;
            else
                return 0f;
        });
    }
}
