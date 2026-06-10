package fr.pilagors.miningminers.recipes.miners.basic;

import fr.pilagors.miningminers.MiningMiners;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;

import com.mojang.serialization.MapCodec;

public record BasicControllerRecipe(
    ItemStack output,
    int processingTime
)  implements Recipe<BasicControllerRecipeInput> {

    @Override
    public ItemStack assemble(BasicControllerRecipeInput input, Provider p) {
        return output.copy();
    }

    @Override
    public boolean matches(BasicControllerRecipeInput input, Level level) {
        return true;
    }

    @Override
    public RecipeType<? extends Recipe<BasicControllerRecipeInput>> getType() {
        return MiningMiners.BASIC_CONTROLLER_RECIPE_TYPE.get();
    }

    @Override
    public RecipeSerializer<? extends Recipe<BasicControllerRecipeInput>> getSerializer() {
        return MiningMiners.BASIC_CONTROLLER_RECIPE_SERIALIZER.get();
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.create(List.of());
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return null;
    }

    public static final MapCodec<BasicControllerRecipe> CODEC = RecordCodecBuilder.mapCodec(instance ->
        instance.group(
            ItemStack.CODEC.fieldOf("output").forGetter(BasicControllerRecipe::output),
            Codec.INT.fieldOf("processingTime").forGetter(BasicControllerRecipe::processingTime)
        ).apply(instance, BasicControllerRecipe::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, BasicControllerRecipe> STREAM_CODEC = StreamCodec.composite(
        ItemStack.STREAM_CODEC, BasicControllerRecipe::output,
        ByteBufCodecs.VAR_INT, BasicControllerRecipe::processingTime,
        BasicControllerRecipe::new
    );

}


