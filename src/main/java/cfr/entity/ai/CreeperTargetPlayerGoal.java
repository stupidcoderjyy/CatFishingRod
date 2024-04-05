package cfr.entity.ai;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;

public class CreeperTargetPlayerGoal extends NearestAttackableTargetGoal<Player> {
    public CreeperTargetPlayerGoal(Mob mob) {
        super(mob, Player.class, true);
    }

    @Override
    public boolean canUse() {
        return super.canUse() && !CreeperAvoidPlayerGoal.isHoldingCfr((Player) target);
    }
}
