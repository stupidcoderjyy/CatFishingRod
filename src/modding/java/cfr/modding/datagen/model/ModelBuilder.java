package cfr.modding.datagen.model;

import cfr.modding.core.Mod;
import cfr.modding.datagen.model.elements.Structure;
import com.google.common.base.Preconditions;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class ModelBuilder extends ModelFile{
    public final Map<String, ResourceLocation> textures = new HashMap<>();
    private final Map<String, Display> transforms = new HashMap<>();
    private final String pathPrefix;
    private Structure structure;
    private ResourceLocation loaderLoc;
    @Nullable
    private ModelFile parent;

    protected ModelBuilder(String pathPrefix, ResourceLocation location) {
        super(location);
        this.pathPrefix = pathPrefix;
    }

    public ModelBuilder parent(ResourceLocation loc) {
        this.parent = new ModelFile(Mod.expandLoc(pathPrefix, loc));
        return this;
    }

    public ModelBuilder parent(ModelBuilder external) {
        this.parent = external;
        return this;
    }

    public ModelBuilder parent(String fullLoc) {
        return parent(new ResourceLocation(fullLoc));
    }

    public ModelBuilder texture(String key, ResourceLocation loc) {
        loc = Mod.expandLoc(pathPrefix, loc);
        textures.put(key, loc);
        return this;
    }

    public ModelBuilder texture(String key, String loc) {
        return texture(key, new ResourceLocation(loc));
    }

    public ModelBuilder struct(Structure s) {
        Preconditions.checkState(structure == null, "structure already created");
        structure = s;
        return this;
    }

    public ModelBuilder display(Display transform) {
        transforms.put(transform.type, transform);
        return this;
    }

    public ModelBuilder loader(String loaderPath) {
        this.loaderLoc = Mod.modLoc(loaderPath);
        return this;
    }

    protected JsonObject toJson() {
        JsonObject root = new JsonObject();
        if (loaderLoc != null) {
            root.addProperty("loader", loaderLoc.toString());
            return root;
        }
        if (parent != null) {
            root.addProperty("parent", parent.location.toString());
        }
        if (!textures.isEmpty()) {
            JsonObject textureEntries = new JsonObject();
            for (var e : textures.entrySet()) {
                textureEntries.addProperty(e.getKey(), e.getValue().toString());
            }
            root.add("textures", textureEntries);
        }
        if (structure != null) {
            root.add("elements", structure.toJson());
        }
        if (!transforms.isEmpty()) {
            JsonObject displayObj = new JsonObject();
            transforms.forEach((type, transform) -> transform.toJson(displayObj));
            root.add("display", displayObj);
        }
        return root;
    }
}
