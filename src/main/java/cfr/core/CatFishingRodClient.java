package cfr.core;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class CatFishingRodClient extends CatFishingRod implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
    }
}
