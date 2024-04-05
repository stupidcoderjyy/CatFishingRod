package cfr.core;

import cfr.modding.core.Mod;
import cfr.modding.element.item.ItemDef;
import cfr.registry.ModCreativeTabs;
import cfr.registry.ModItems;

public class CatFishingRod extends Mod {
    public static final String MOD_ID = "cfr";

    protected CatFishingRod() {
        super(MOD_ID);
    }

    @Override
    protected void buildElements() {
        ItemDef.pushTab(ModCreativeTabs.MAIN);
        ModItems.build();
    }
}
