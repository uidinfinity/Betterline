package net.shoreline.client.api.config.setting;

import net.minecraft.block.Block;
import net.shoreline.client.api.config.Config;
import java.util.Arrays;
import java.util.List;

/**
 * @author uidinfinity
 * @since 1.0
 */

public class BlockListConfig extends Config<List<Block>> {
    public BlockListConfig(String name, String description, Block... blocks) {
        super(name, description, Arrays.asList(blocks));
    }
}