package com.huypham.trademe.sound;

import net.minecraft.world.level.block.SoundType;

public class SoundTypes {
        public static final SoundType LUCKY_BLOCK_SOUND = new SoundType(1f, 1f,
                SoundType.GLASS.getBreakSound(),
                SoundType.METAL.getStepSound(),
                SoundType.GLASS.getPlaceSound(),
                SoundType.METAL.getHitSound(),
                SoundType.METAL.getFallSound()
        );

}
