package cfr.mixins;

import cfr.entity.ai.CreeperAvoidPlayerGoal;
import net.minecraft.world.entity.ai.goal.SwellGoal;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SwellGoal.class)
public class SwellGoalMixin {
    @Final
    @Shadow
    private Creeper creeper;

    @Inject(method = "canUse", at = @At("RETURN"), cancellable = true)
    private void hookCanUse(CallbackInfoReturnable<Boolean> cir) {
        Player p = (Player) creeper.getTarget();
        if (p != null) {
            cir.setReturnValue(!creeper.isVehicle()
                    && cir.getReturnValue()
                    && !CreeperAvoidPlayerGoal.isHoldingCfr(p));
        }
    }
}
