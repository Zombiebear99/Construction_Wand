package org.zombie.constructionwand.items.core;

import net.minecraft.resources.ResourceLocation;
import org.zombie.constructionwand.ConstructionWand;
import org.zombie.constructionwand.api.IWandAction;
import org.zombie.constructionwand.wand.action.ActionAngel;

public class ItemCoreAngel extends ItemCore
{
    public ItemCoreAngel(Properties properties) {
        super(properties);
    }

    @Override
    public int getColor() {
        return 0xE9B115;
    }

    @Override
    public IWandAction getWandAction() {
        return new ActionAngel();
    }

    @Override
    public ResourceLocation getRegistryName() {
        return ConstructionWand.loc("core_angel");
    }
}
