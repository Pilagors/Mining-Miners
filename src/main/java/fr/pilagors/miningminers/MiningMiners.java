package fr.pilagors.miningminers;

import com.mojang.logging.LogUtils;

import fr.pilagors.miningminers.recipes.miners.basic.BasicControllerRecipe;
import fr.pilagors.miningminers.recipes.miners.basic.BasicControllerRecipeSerializer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.state.*;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.RecipeSerializer;
import java.util.Set;
import org.slf4j.Logger;

@Mod(MiningMiners.MODID)
public final class MiningMiners {
    public static final String MODID = "miningminers";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MODID);
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, MODID);
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, MODID);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MODID);

    public static final RegistryObject<Block> BASIC_CONTROLLER = BLOCKS.register("basic_controller", () -> new BasicControllerBlock(BlockBehaviour.Properties.of().setId(BLOCKS.key("basic_controller")).strength(2f)));
    public static final RegistryObject<Item> BASIC_CONTROLLER_ITEM = ITEMS.register("basic_controller", () -> new BlockItem(BASIC_CONTROLLER.get(), new Item.Properties().setId(ITEMS.key("basic_controller"))));
    public static final RegistryObject<CreativeModeTab> MINING_MINERS_TAB = CREATIVE_MODE_TABS.register("mining_miners_tab", () -> CreativeModeTab.builder().icon(() -> new ItemStack(BASIC_CONTROLLER_ITEM.get())).title(Component.translatable("itemGroup.miningminers")).build());
    public static final RegistryObject<BlockEntityType<BasicControllerBlockEntity>> BASIC_CONTROLLER_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register("basic_controller_block_entity", () -> new BlockEntityType<>(BasicControllerBlockEntity::new, Set.of(BASIC_CONTROLLER.get())));
    public static final RegistryObject<MenuType<BasicControllerMenu>> BASIC_CONTROLLER_MENU = MENU_TYPES.register("basic_controller_menu", () -> IForgeMenuType.create((windowId, inv, data) -> new BasicControllerMenu(windowId, inv)));
    public static final RegistryObject<RecipeType<BasicControllerRecipe>> BASIC_CONTROLLER_RECIPE_TYPE = RECIPE_TYPES.register("basic_controller", () -> RecipeType.simple(Identifier.fromNamespaceAndPath(MODID, "basic_controller")));
    public static final RegistryObject<RecipeSerializer<BasicControllerRecipe>> BASIC_CONTROLLER_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("basic_controller", BasicControllerRecipeSerializer::new);

    public MiningMiners(FMLJavaModLoadingContext context) {
        var modBusGroup = context.getModBusGroup();

        FMLCommonSetupEvent.getBus(modBusGroup).addListener(this::commonSetup);
        BuildCreativeModeTabContentsEvent.getBus(modBusGroup).addListener(MiningMiners::addToCreativeTabs);

        BLOCKS.register(modBusGroup);
        ITEMS.register(modBusGroup);
        CREATIVE_MODE_TABS.register(modBusGroup);
        BLOCK_ENTITY_TYPES.register(modBusGroup);
        MENU_TYPES.register(modBusGroup);
        RECIPE_TYPES.register(modBusGroup);
        RECIPE_SERIALIZERS.register(modBusGroup);

        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("Mining Miners - Common Setup");
    }

    private static void addToCreativeTabs(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == MINING_MINERS_TAB.getKey()) {
            event.accept(BASIC_CONTROLLER_ITEM);
        }
    }

    @Mod.EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            LOGGER.info("Mining Miners - Client Setup");

            MenuScreens.register(MiningMiners.BASIC_CONTROLLER_MENU.get(), BasicControllerScreen::new);
        }
    }
}
