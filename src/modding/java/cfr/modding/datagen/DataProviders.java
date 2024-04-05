package cfr.modding.datagen;

import cfr.modding.datagen.blockstate.BlockStateProvider;
import cfr.modding.datagen.lang.LocalizationProvider;
import cfr.modding.datagen.model.ModelBuilder;
import cfr.modding.datagen.model.ModelProvider;
import cfr.modding.datagen.recipe.RecipeProvider;
import cfr.modding.datagen.tag.BlockTag;
import cfr.modding.datagen.tag.TagsProvider;
import net.minecraft.resources.ResourceLocation;

/**
 * 模组数据提供器
 */
public class DataProviders {
    /**
     * 物品模型文件的生成器
     * <p>使用{@link ModelProvider#model}创建一个新的模型文件
     *
     * @see ModelBuilder
     */
    public static final ModelProvider MODEL_ITEM = new ModelProvider("item");

    /**
     * 方块模型文件的生成器
     * <p>使用{@link ModelProvider#model}创建一个新的模型文件
     * @see ModelBuilder
     */
    public static final ModelProvider MODEL_BLOCK = new ModelProvider("block");

    /**
     * 方块状态文件生成器
     * <p>使用{@link BlockStateProvider#variants(ResourceLocation)}创建一个枚举方块状态文件
     */
    public static final BlockStateProvider BLOCK_STATE = new BlockStateProvider();

    public static final RecipeProvider RECIPE = new RecipeProvider();

    public static final LocalizationProvider LOCALIZATION = new LocalizationProvider("zh_cn");

    public static final TagsProvider<BlockTag> BLOCK_TAGS = new TagsProvider<>("blocks", BlockTag::new);

    static void init() {
    }
}
