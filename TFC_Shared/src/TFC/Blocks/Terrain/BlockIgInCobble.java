package TFC.Blocks.Terrain;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityFallingSand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import TFC.API.Constant.Global;
import TFC.API.Util.Helper;
import TFC.Blocks.BlockTerra;
import TFC.Core.TFC_Sounds;
import TFC.Items.Tools.ItemChisel;
import TFC.Items.Tools.ItemHammer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockIgInCobble extends BlockCobble
{
	public BlockIgInCobble(int i, Material material) {
		super(i, material);
		fallInstantly = false;
        names = Global.STONE_IGIN;
		icons = new Icon[names.length];
	}
}
