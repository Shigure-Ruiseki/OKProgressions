package ruiseki.okprogressions.common.block.machine.user;

import net.minecraft.block.material.Material;

import ruiseki.okprogressions.common.block.machine.BlockMachine;

public class BlockUser extends BlockMachine {

    public BlockUser() {
        super(Material.iron, TEBlockUser.class);
        this.isDirection = true;
    }
}
