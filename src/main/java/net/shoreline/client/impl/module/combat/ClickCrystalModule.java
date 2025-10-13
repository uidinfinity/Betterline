package net.shoreline.client.impl.module.combat;

import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.item.EndCrystalItem;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.shoreline.client.api.config.Config;
import net.shoreline.client.api.config.setting.BooleanConfig;
import net.shoreline.client.api.config.setting.NumberConfig;
import net.shoreline.client.api.module.ModuleCategory;
import net.shoreline.client.impl.event.network.PacketEvent;
import net.shoreline.client.impl.event.network.PlayerTickEvent;
import net.shoreline.client.impl.event.world.AddEntityEvent;
import net.shoreline.client.impl.event.world.RemoveEntityEvent;
import net.shoreline.client.api.module.RotationModule;
import net.shoreline.client.util.player.RotationUtil;
import net.shoreline.eventbus.annotation.EventListener;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class ClickCrystalModule extends RotationModule {

    private Config<Float> breakDelayConfig;
    private Config<Float> randomDelayConfig;
    private Config<Boolean> rotateConfig;
    private Config<Boolean> randomRotateConfig;

    private final Set<BlockPos> placedCrystals = new HashSet<>();
    private final Map<EndCrystalEntity, Long> spawnedCrystals = new LinkedHashMap<>();
    private float randomDelay = -1;

    public ClickCrystalModule() {
        super("ClickCrystal", "Automatically breaks placed crystals", ModuleCategory.COMBAT);

        breakDelayConfig = new NumberConfig<>("SpawnDelay", "Speed to break crystals after spawning", 0.0f, 0.0f, 20.0f);
        randomDelayConfig = new NumberConfig<>("RandomDelay", "Randomized break delay", 0.0f, 0.0f, 5.0f);
        rotateConfig = new BooleanConfig("Rotate", "Rotate before breaking", false);
        randomRotateConfig = new BooleanConfig("RotateJitter", "Slightly randomizes rotations", false, () -> rotateConfig.getValue());

        register(breakDelayConfig);
        register(randomDelayConfig);
        register(rotateConfig);
        register(randomRotateConfig);
    }

    @EventListener
    public void onPlayerTick(PlayerTickEvent event) {
        if (spawnedCrystals.isEmpty()) {
            return;
        }

        Map.Entry<EndCrystalEntity, Long> entry = spawnedCrystals.entrySet().iterator().next();
        EndCrystalEntity crystalEntity = entry.getKey();
        Long spawnTime = entry.getValue();

        if (randomDelay == -1) {
            float maxRandom = randomDelayConfig.getValue();
            randomDelay = (maxRandom <= 0.0f) ? 0.0f : RANDOM.nextFloat(maxRandom * 25.0f);
        }

        float breakDelay = breakDelayConfig.getValue() * 50.0f + randomDelay;
        double distSq = mc.player.getEyePos().squaredDistanceTo(crystalEntity.getPos());

        if (distSq <= 12.25 && System.currentTimeMillis() - spawnTime >= breakDelay) {
            if (rotateConfig.getValue()) {
                Vec3d targetPos = crystalEntity.getPos();
                if (randomRotateConfig.getValue()) {
                    Box bb = crystalEntity.getBoundingBox();
                    targetPos = new Vec3d(
                            RANDOM.nextDouble(bb.minX, bb.maxX),
                            RANDOM.nextDouble(bb.minY, bb.maxY),
                            RANDOM.nextDouble(bb.minZ, bb.maxZ)
                    );
                }
                float[] rotations = RotationUtil.getRotationsTo(mc.player.getEyePos(), targetPos);
                setRotation(rotations[0], rotations[1]);
            }

            if (mc.interactionManager != null) {
                mc.interactionManager.attackEntity(mc.player, crystalEntity);
                mc.player.swingHand(Hand.MAIN_HAND);
            }

            spawnedCrystals.remove(crystalEntity);
            randomDelay = -1;
        }
    }

    @EventListener
    public void onPacketOutbound(PacketEvent.Outbound event) {
        if (event.getPacket() instanceof PlayerInteractBlockC2SPacket packet) {
            if (!event.isClientPacket() && mc.player != null) {
                if (mc.player.getStackInHand(packet.getHand()).getItem() instanceof EndCrystalItem) {
                    placedCrystals.add(packet.getBlockHitResult().getBlockPos());
                }
            }
        }
    }

    @EventListener
    public void onAddEntity(AddEntityEvent event) {
        if (event.getEntity() instanceof EndCrystalEntity crystal) {
            BlockPos basePos = crystal.getBlockPos().down();
            if (placedCrystals.contains(basePos)) {
                spawnedCrystals.put(crystal, System.currentTimeMillis());
                placedCrystals.remove(basePos);
            }
        }
    }

    @EventListener
    public void onRemoveEntity(RemoveEntityEvent event) {
        if (event.getEntity() instanceof EndCrystalEntity crystal) {
            spawnedCrystals.remove(crystal);
        }
    }
}