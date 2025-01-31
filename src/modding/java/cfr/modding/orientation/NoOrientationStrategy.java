package cfr.modding.orientation;

import cfr.modding.datagen.DataProviders;
import cfr.modding.datagen.blockstate.Model;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.Property;

import java.util.Collection;
import java.util.List;

class NoOrientationStrategy implements IOrientationStrategy{
    @Override
    public Collection<Property<?>> properties() {
        return List.of();
    }

    @Override
    public void provideBlockState(ResourceLocation loc) {
        DataProviders.BLOCK_STATE.variants(loc)
                .condition(state -> new Model(loc));
    }
}
