package cfr.modding.datagen.blockstate.multipart;

import cfr.modding.util.serialize.ContainerVal;
import com.google.gson.JsonObject;

public class ListPredicate implements ModelPredicate {
    private final ContainerVal list = new ContainerVal();

    public ListPredicate add(String key, String ... options){
        list.newString(key).set(getOptionString(options));
        return this;
    }

    private String getOptionString(String[] options) {
        StringBuilder sb = new StringBuilder();
        switch (options.length) {
            case 0 -> {}
            case 1 -> sb.append(options[0]);
            default -> {
                int i;
                for (i = 0; i < options.length - 1; i++) {
                    sb.append(options[i]).append('|');
                }
                sb.append(options[i]);
            }
        }
        return sb.toString();
    }

    @Override
    public JsonObject toJson() {
        return (JsonObject) list.toJson(null);
    }

    @Override
    public String toString() {
        return list.toString();
    }
}
