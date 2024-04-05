package cfr.registry;

import cfr.elements.CatFishingRodItem;
import cfr.modding.element.item.ItemDef;

public class ModItems {
    public static final ItemDef<?> CAT_FISHING_ROD;

    static {
        CAT_FISHING_ROD = new CatFishingRodItem.Def("cat_fishing_rod", "猫钓竿");
    }

    public static void build() {
    }
}
