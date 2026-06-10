package fr.pilagors.miningminers;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.crafting.RecipeHolder;
import java.util.Optional;
import fr.pilagors.miningminers.recipes.miners.basic.BasicControllerRecipe;
import fr.pilagors.miningminers.recipes.miners.basic.BasicControllerRecipeInput;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;

public class BasicControllerBlockEntity extends BlockEntity implements MenuProvider {

    private int progress = 0;
    private int maxProgress = 100;
    private boolean isRunning = true;
    
    public final ItemStackHandler inventory = new ItemStackHandler(4);

    public final ContainerData data = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> progress;
                case 1 -> maxProgress;
                case 2 -> isRunning ? 1 : 0;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> progress = value;
                case 2 -> isRunning = value != 0;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    };

    public BasicControllerBlockEntity(BlockPos pos, BlockState state) {
        super(MiningMiners.BASIC_CONTROLLER_BLOCK_ENTITY.get(), pos, state);
    }
    
    @Override
    public Component getDisplayName() {
        return Component.translatable("container.miningminers.basic_controller");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
        return new BasicControllerMenu(containerId, inventory, this);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, BasicControllerBlockEntity blockEntity) {
        if (level.isClientSide()) {
            return;
        }

        Optional<RecipeHolder<BasicControllerRecipe>> recipe = ((ServerLevel) level).recipeAccess().getRecipeFor(MiningMiners.BASIC_CONTROLLER_RECIPE_TYPE.get(), new BasicControllerRecipeInput(), level);

        if (recipe.isEmpty()) {
            blockEntity.isRunning = false;
            return;
        }
        
        blockEntity.isRunning = true;

        if (blockEntity.isRunning) {
            blockEntity.maxProgress = recipe.get().value().processingTime();
            
            if (blockEntity.inventory.insertItem(0, recipe.get().value().output(), true).isEmpty()) {
                blockEntity.progress++;
            } else {
                return;
            }

            if (blockEntity.progress >= blockEntity.maxProgress) {
                blockEntity.inventory.insertItem(0, recipe.get().value().output(), false);
                blockEntity.progress = 0;
            }
        }
    }
}
