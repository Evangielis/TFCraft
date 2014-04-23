package TFC.API;

import java.util.Random;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import TFC.API.Events.ItemMeltEvent;

public class HeatIndex
{
	public int ticksToCook;
	public boolean keepNBT;

	private ItemStack output;
	private int outputMin = 0;
	private int outputMax = 0;

	private ItemStack morph;
	public ItemStack input;

	public HeatIndex(ItemStack in, int ticks)
	{
		input = in;
		ticksToCook = ticks;
		output = null;
	}

	public HeatIndex(ItemStack in, int ticks, ItemStack out)
	{
		this(in, ticks);
		output = out;
	}

	public HeatIndex(ItemStack in, HeatRaw raw)
	{
		input = in;
		ticksToCook = raw.ticksToCook;
		output = null;
	}

	public HeatIndex(ItemStack in, HeatRaw raw, ItemStack out)
	{
		this(in, raw);
		output = out;
	}

	public HeatIndex setKeepNBT(boolean k)
	{
		keepNBT = k;
		return this;
	}

	public boolean hasOutput(){
		return output != null;
	}

	public Item getOutputItem()
	{
		if(output!= null)
			return output.getItem();
		else return null;
	}

	public int getOutputDamage()
	{
		if(output!= null)
			return output.getItemDamage();
		else return 0;
	}

	public HeatIndex setMinMax(int min, int max)
	{
		outputMin = min;
		outputMax = max;
		return this;
	}

	public HeatIndex setMinMax(int amt)
	{
		outputMin = amt;
		outputMax = amt;
		return this;
	}

	public HeatIndex setMorph(ItemStack is)
	{
		morph = is;
		return this;
	}

	public ItemStack getMorph()
	{
		return morph;
	}

	public ItemStack getOutput(Random R)
	{
		int rand = 0;
		if(outputMax - outputMin > 0) 
		{
			rand = outputMin + R.nextInt(outputMax - outputMin);
			return new ItemStack(getOutputItem(),output.stackSize, 100-rand);
		}
		else 
		{
			return new ItemStack(getOutputItem(),output.stackSize, outputMin);
		}
	}

	public ItemStack getOutput(ItemStack in, Random R)
	{
		ItemStack is = getOutput(R);
		if(this.keepNBT)
		{
			if(is.hasTagCompound())
			{
				NBTTagCompound nbt = is.getTagCompound();
				for(Object o : in.getTagCompound().getTags())
				{
					NBTBase n = (NBTBase)o;
					if(nbt.hasKey(n.getName()))
						nbt.removeTag(n.getName());

					nbt.getTags().add(o);
				}
			}
			else
			{
				is.setTagCompound(in.stackTagCompound);
				//This if check should be removed in 79. It remains in place to save old worlds.
				if(TFC_ItemHeat.HasTemp(is))
					TFC_ItemHeat.SetTemp(is, (short)(TFC_ItemHeat.GetTemp(is)*0.9));
			}
		}
		ItemMeltEvent eventMelt = new ItemMeltEvent(in, is);
		MinecraftForge.EVENT_BUS.post(eventMelt);
		return eventMelt.result;
	}

	public boolean matches(ItemStack is)
	{
		if(is != null)
		{
			boolean b = is.getItem().getHasSubtypes();
			if(is.getItem() != input.getItem())
				return false;
			else if(is.getItem().getHasSubtypes() && 
					(input.getItemDamage() != 32767 && 
					is.getItemDamage() != input.getItemDamage()))
				return false;
		}
		else return false;
		return true;
	}
}
