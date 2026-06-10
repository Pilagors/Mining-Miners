package fr.pilagors.miningminers;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class BasicControllerMenu extends AbstractContainerMenu {
    private final ContainerData data;
    // SERVER
    public BasicControllerMenu(int containerId, Inventory playerInventory, BasicControllerBlockEntity blockEntity) {
        super(MiningMiners.BASIC_CONTROLLER_MENU.get(), containerId);
        setupSlots(playerInventory, blockEntity.inventory);
        
        data = blockEntity.data;
        addDataSlots(data);
    }

    // CLIENT
    public BasicControllerMenu(int containerId, Inventory playerInventory) {
        super(MiningMiners.BASIC_CONTROLLER_MENU.get(), containerId);
        setupSlots(playerInventory, new ItemStackHandler(4));

        data = new SimpleContainerData(3);
        addDataSlots(data);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack res = ItemStack.EMPTY;

        Slot slot = slots.get(index);

        if (slot.hasItem()) {
            ItemStack slotStack = slot.getItem();
            res = slotStack.copy();

            if (index < 4) {
                if (!this.moveItemStackTo(slotStack, 4, slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (!this.moveItemStackTo(slotStack, 0, 4, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (slotStack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }
        
        return res;
    }

    @Override
    public boolean stillValid(Player p_38874_) {
        return true;
    }

    private void setupSlots(Inventory playerInventory, IItemHandler machineInventory) {

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                addSlot(new SlotItemHandler(machineInventory, i * 2 + j, 71 + i * 18, 26 + j * 18));
            }
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int i = 0; i < 9; i++) {
            addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }
    
    public int getProgress() {
        return data.get(0);
    }

    public int getMaxProgress() {
        return data.get(1);
    }

    public boolean isRunning() {
        return data.get(2) != 0;
    }
}
