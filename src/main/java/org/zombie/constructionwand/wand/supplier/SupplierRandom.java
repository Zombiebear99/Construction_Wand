package org.zombie.constructionwand.wand.supplier;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import org.zombie.constructionwand.basics.WandUtil;
import org.zombie.constructionwand.basics.option.WandOptions;
import org.zombie.constructionwand.basics.pool.RandomPool;

import javax.annotation.Nullable;
import java.util.LinkedHashMap;

public class SupplierRandom extends SupplierInventory
{
    public SupplierRandom(Player player, WandOptions options) {
        super(player, options);
    }

    @Override
    public void getSupply(@Nullable BlockItem target) {
        itemCounts = new LinkedHashMap<>();

        // Random mode -> add all items from hotbar
        itemPool = new RandomPool<>(player.getRandom());

        for(ItemStack stack : WandUtil.getHotbarWithOffhand(player)) {
            if(stack.getItem() instanceof BlockItem) addBlockItem((BlockItem) stack.getItem());
        }
    }
}
