package dev.aika.taczjs;

import dev.latvian.mods.kubejs.recipe.filter.RecipeFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public final class TaCZJS {
    public static final String MOD_ID = "taczjs";
    public static final String MOD_NAME = "TaCZ JS";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static List<RecipeFilter> RecipeFilter = new ArrayList<>();

    public static void init() {
    }
}
