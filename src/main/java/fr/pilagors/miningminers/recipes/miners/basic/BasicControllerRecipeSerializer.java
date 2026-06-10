package fr.pilagors.miningminers.recipes.miners.basic;

import com.mojang.serialization.MapCodec;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class BasicControllerRecipeSerializer implements RecipeSerializer<BasicControllerRecipe> {

    @Override
    public MapCodec<BasicControllerRecipe> codec() {
        return BasicControllerRecipe.CODEC;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, BasicControllerRecipe> streamCodec() {
        return BasicControllerRecipe.STREAM_CODEC;
    }
    
}
