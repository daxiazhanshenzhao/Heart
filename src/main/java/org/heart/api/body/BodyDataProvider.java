package org.heart.api.body;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.heart.api.init.CapabilityInit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BodyDataProvider implements ICapabilitySerializable<CompoundTag> {


    private final IBodyCapability bodyData;
    private final LazyOptional<IBodyCapability> lazyOptional;


    public BodyDataProvider(Player player) {
        this.bodyData = new BodyData(player);
        this.lazyOptional = LazyOptional.of(() -> bodyData);
    }


    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return CapabilityInit.BODY_DATA.orEmpty(cap, lazyOptional);
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();

        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {

    }
}
