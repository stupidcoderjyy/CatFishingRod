package cfr.entity.ai;

import cfr.registry.ModItems;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.player.Player;

public class CreeperAvoidPlayerGoal extends AvoidEntityGoal<Player> {
    public boolean prevCanUse;

    public CreeperAvoidPlayerGoal(PathfinderMob creeper) {
        super(creeper, Player.class, 6.0f, 1.0, 1.2);
    }

    @Override
    public boolean canUse() {
        prevCanUse = super.canUse() && isHoldingCfr(toAvoid);
        return prevCanUse;
    }

    public static boolean isHoldingCfr(Player p) {
        return p.getMainHandItem().getItem() == ModItems.CAT_FISHING_ROD.item
                || p.getOffhandItem().getItem() == ModItems.CAT_FISHING_ROD.item;
    }
}
