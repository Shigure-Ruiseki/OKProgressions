package ruiseki.okprogressions.common.block.compressed;

import ruiseki.okcore.config.extendedconfig.BlockConfig;
import ruiseki.okprogressions.OKProgressions;

public class BlockBoneConfig extends BlockConfig {

    /**
     * The unique instance.
     */
    public static BlockBoneConfig _instance;

    /**
     * Make a new instance.
     */
    public BlockBoneConfig() {
        super(OKProgressions._instance, true, "bone_block", null, config -> new BlockBone());
    }

    @Override
    public String getOreDictionaryId() {
        return "blockBone";
    }
}
