package fr.pilagors.miningminers;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.MenuProvider;

public class BasicControllerBlock extends Block implements EntityBlock {
    public BasicControllerBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new BasicControllerBlockEntity(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level,
            BlockState state, BlockEntityType<T> type) {
                if (type == MiningMiners.BASIC_CONTROLLER_BLOCK_ENTITY.get()) {
                        return (BlockEntityTicker<T>) (BlockEntityTicker<BasicControllerBlockEntity>) BasicControllerBlockEntity::tick;
                    }
                    return null;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player,
            BlockHitResult result) {
                if (level.isClientSide()) {
                    return InteractionResult.SUCCESS;
                }

                BlockEntity blockEntity = level.getBlockEntity(pos);

                if (blockEntity instanceof MenuProvider menuProvider) {
                    player.openMenu(menuProvider);
                }

                return InteractionResult.SUCCESS;
    }

}
