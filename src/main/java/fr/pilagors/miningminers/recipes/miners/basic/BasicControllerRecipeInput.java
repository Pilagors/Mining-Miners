package fr.pilagors.miningminers.recipes.miners.basic;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public class BasicControllerRecipeInput implements RecipeInput {
    
    @Override
    public ItemStack getItem(int _i) {
        return ItemStack.EMPTY;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
