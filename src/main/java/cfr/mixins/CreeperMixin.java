package cfr.mixins;

import cfr.entity.ai.CreeperAvoidPlayerGoal;
import cfr.entity.ai.CreeperTargetPlayerGoal;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PowerableMob;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.Ocelot;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Creeper.class)
public abstract class CreeperMixin extends Monster implements PowerableMob {
    @Final @Shadow private static EntityDataAccessor<Boolean> DATA_IS_IGNITED;
    @Shadow public abstract void setSwellDir(int i);
    @Shadow private int swell;

    @Unique
    private boolean isRiding;
    @Unique
    private CreeperAvoidPlayerGoal goalAvoidPlayer;

    protected CreeperMixin(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "registerGoals", at = @At(value = "HEAD"), cancellable = true)
    private void hookRegisterGoal(CallbackInfo ci) {
        goalAvoidPlayer = new CreeperAvoidPlayerGoal(this);
        goalSelector.addGoal(0, goalAvoidPlayer);
        goalSelector.addGoal(1, new FloatGoal(this));
        goalSelector.addGoal(2, new SwellGoal((Creeper)(Object) this));
        goalSelector.addGoal(3, new AvoidEntityGoal<>(this, Ocelot.class, 6.0f, 1.0, 1.2));
        goalSelector.addGoal(3, new AvoidEntityGoal<>(this, Cat.class, 6.0f, 1.0, 1.2));
        goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0, false));
        goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8));
        goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0f));
        goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        targetSelector.addGoal(1, new CreeperTargetPlayerGoal(this));
        targetSelector.addGoal(2, new HurtByTargetGoal(this));
        ci.cancel();
    }

    @Inject(method = "tick()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/monster/Creeper;isAlive()Z", shift = At.Shift.AFTER), cancellable = true)
    private void hookNoExplodeWhenRiding(CallbackInfo ci) {
        Entity p = getFirstPassenger();
        boolean playerRiding = isVehicle() && p instanceof Player;
        boolean isClient = level().isClientSide;
        if (playerRiding) {
            if (!isClient && !isRiding) {
                isRiding = true;
            }
            goalSelector.disableControlFlag(Goal.Flag.MOVE);
            goalSelector.disableControlFlag(Goal.Flag.TARGET);
            goalSelector.disableControlFlag(Goal.Flag.LOOK);
            entityData.set(DATA_IS_IGNITED, false);
            setSwellDir(-1);
            swell = 0;
            if (!isClient) {
                Vec3 look = p.getLookAngle();
                double tx = getX() - look.x;
                double ty = getY();
                double tz = getZ() - look.z;
                getMoveControl().setWantedPosition(tx, ty, tz, 1.5);
                getLookControl().setLookAt(tx, ty, tz);
            }
        } else if (!isClient && isRiding) {
            isRiding = false;
            goalSelector.enableControlFlag(Goal.Flag.MOVE);
            goalSelector.enableControlFlag(Goal.Flag.TARGET);
            goalSelector.enableControlFlag(Goal.Flag.LOOK);
        }
        if (goalAvoidPlayer != null && goalAvoidPlayer.prevCanUse) {
            entityData.set(DATA_IS_IGNITED, false);
            setSwellDir(-1);
            swell = 0;
        }
    }
}
