package cfr.elements;

import cfr.modding.datagen.DataProviders;
import cfr.modding.element.item.ItemDef;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class CatFishingRodItem extends Item {
    public CatFishingRodItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult interactLivingEntity(
            ItemStack itemStack, Player player,
            LivingEntity entity, InteractionHand interactionHand) {
        if (!player.level().isClientSide && !player.isPassenger() && entity instanceof Creeper) {
            player.startRiding(entity);
            entity.playSound(SoundEvents.PIG_SADDLE, 1.0f, 1.0f);
        }
        return super.interactLivingEntity(itemStack, player, entity, interactionHand);
    }

    public static class Def extends ItemDef<CatFishingRodItem> {
        public Def(String id, String name) {
            super(id, name, new CatFishingRodItem(new Properties()));
        }

        @Override
        protected void provideModel() {
            DataProviders.MODEL_ITEM.model(loc)
                    .parent("minecraft:item/handheld_rod")
                    .texture("layer0", loc);
        }
    }
}
