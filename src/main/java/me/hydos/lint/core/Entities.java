package me.hydos.lint.core;

import me.hydos.lint.entities.boss.KingTater;
import me.hydos.lint.entities.boss.TaterMinion;
import me.hydos.lint.entities.liltaterbattery.LilTaterBattery;
import net.fabricmc.fabric.api.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public interface Entities {

    EntityType<LilTaterBattery> LIL_TATER =
            Registry.register(Registry.ENTITY_TYPE, new Identifier("lint", "lil_tater"), FabricEntityTypeBuilder.create(EntityCategory.AMBIENT, (LilTaterBattery::new))
                    .size(EntityDimensions.fixed(0.3f, 0.4f))
                    .build());
    EntityType<KingTater> KING_TATER =
            Registry.register(Registry.ENTITY_TYPE, new Identifier("lint", "king_tater"), FabricEntityTypeBuilder.create(EntityCategory.MONSTER, KingTater::new)
                    .size(EntityDimensions.changing(2f, 2f))
                    .build());
    EntityType<TaterMinion> MINION =
            Registry.register(Registry.ENTITY_TYPE, new Identifier("lint", "minion"), FabricEntityTypeBuilder.create(EntityCategory.MONSTER, (EntityType<TaterMinion> type, World world) -> new TaterMinion(type, world, null))
                    .size(LIL_TATER.getDimensions())
                    .build());

    static void onInitialize() {
    }
}
