package me.tom.sparse.bukkit;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;
import java.util.stream.Collectors;

public class ItemBuilder
{
	private Material material;
	private int metadata;
	private int amount;
	private String name;
	private List<String> lore;
	private HashMap<Enchantment, Integer> enchantments;
	private Set<ItemFlag> flags;
	
	/**
	 * Creates a blank ItemBuilder (everything is null)
	 */
	public ItemBuilder()
	{
		this(null);
	}
	
	/**
	 * Creates an ItemBuilder with initial values.
	 * @param material The material.
	 */
	public ItemBuilder(Material material)
	{
		this(material, 0);
	}
	
	/**
	 * Creates an ItemBuilder with initial values.
	 * @param material The material.
	 * @param metadata The metadata or durability.
	 */
	public ItemBuilder(Material material, int metadata)
	{
		this(material, metadata, 1);
	}
	
	/**
	 * Creates an ItemBuilder with initial values.
	 * @param material The material.
	 * @param metadata The metadata or durability.
	 * @param amount The amount of items in the stack.
	 */
	public ItemBuilder(Material material, int metadata, int amount)
	{
		this(material, metadata, amount, null);
	}
	
	/**
	 * Creates an ItemBuilder with initial values.
	 * @param material The material.
	 * @param metadata The metadata or durability.
	 * @param amount The amount of items in the stack.
	 * @param name The display name of the item.
	 */
	public ItemBuilder(Material material, int metadata, int amount, String name)
	{
		this(material, metadata, amount, name, null);
	}
	
	/**
	 * Creates an ItemBuilder with initial values.
	 * @param material The material.
	 * @param metadata The metadata or durability.
	 * @param amount The amount of items in the stack.
	 * @param name The display name of the item.
	 * @param lore The lore of the item (the ItemBuilder will be backed by the provided list).
	 */
	public ItemBuilder(Material material, int metadata, int amount, String name, List<String> lore)
	{
		this(material, metadata, amount, name, lore, null);
	}
	
	/**
	 * Creates an ItemBuilder with initial values.
	 * @param material The material.
	 * @param metadata The metadata or durability.
	 * @param amount The amount of items in the stack.
	 * @param name The display name of the item.
	 * @param lore The lore of the item (the ItemBuilder will be backed by the provided list).
	 * @param enchantments A {@link java.util.Map} containing the enchantments to add. {@link org.bukkit.enchantments.Enchantment} as key, level as value.
	 */
	public ItemBuilder(Material material, int metadata, int amount, String name, List<String> lore, Map<Enchantment, Integer> enchantments)
	{
		this(material, metadata, amount, name, lore, enchantments, null);
	}
	
	/**
	 * Creates an ItemBuilder with initial values.
	 * @param material The material.
	 * @param metadata The metadata or durability.
	 * @param amount The amount of items in the stack.
	 * @param name The display name of the item.
	 * @param lore The lore of the item (the ItemBuilder will be backed by the provided list).
	 * @param enchantments A map containing the enchantments to add. {@link org.bukkit.enchantments.Enchantment} as key, level as value.
	 * @param flags A {@link java.util.Set} containing all the {@link org.bukkit.inventory.ItemFlag}s to apply to this item.
	 */
	public ItemBuilder(Material material, int metadata, int amount, String name, List<String> lore, Map<Enchantment, Integer> enchantments, Set<ItemFlag> flags)
	{
		this.material = material;
		this.metadata = metadata;
		this.amount = amount;
		this.name = name;
		this.lore = lore;
		this.enchantments = new HashMap<>(enchantments);
		this.flags = flags;
	}
	
	/**
	 * Sets the material.
	 * @param material
	 * @return this
	 */
	public ItemBuilder material(Material material)
	{
		this.material = material;
		return this;
	}
	
	/**
	 * Sets the metadata or durability.
	 * @param metadata
	 * @return this
	 */
	public ItemBuilder metadata(int metadata)
	{
		this.metadata = metadata;
		return this;
	}
	
	/**
	 * Sets the amount of items in the stack.
	 * @param amount
	 * @return this
	 */
	public ItemBuilder amount(int amount)
	{
		this.amount = amount;
		return this;
	}
	
	/**
	 * Sets the display name of this item.
	 * @param name
	 * @return this
	 */
	public ItemBuilder name(String name)
	{
		this.name = name;
		return this;
	}
	
	/**
	 * Sets the display name of this item and converts '&' color codes in the provided name.
	 * @param name
	 * @return this
	 */
	public ItemBuilder colorName(String name)
	{
		this.name = BukkitUtils.color(name);
		return this;
	}
	
	/**
	 * Sets the lore of this item.
	 * @param lore
	 * @return this
	 */
	public ItemBuilder lore(List<String> lore)
	{
		this.lore = lore;
		return this;
	}
	
	/**
	 * Sets the lore of this item.
	 * @param lore
	 * @return this
	 */
	public ItemBuilder lore(String... lore)
	{
		this.lore = Arrays.asList(lore);
		return this;
	}
	
	/**
	 * Sets the lore of this item and converts '&' color codes in the provided lore.
	 * @param lore
	 * @return this
	 */
	public ItemBuilder colorLore(List<String> lore)
	{
		this.lore = lore.stream().map(BukkitUtils::color).collect(Collectors.toList());
		return this;
	}
	
	/**
	 * Sets the lore of this item and converts '&' color codes in the provided lore.
	 * @param lore
	 * @return this
	 */
	public ItemBuilder colorLore(String... lore)
	{
		return colorLore(Arrays.asList(lore));
	}
	
	/**
	 * Adds {@code Unbreaking I} and the {@link org.bukkit.inventory.ItemFlag#HIDE_ENCHANTS} flag, resulting in an enchanted effect with no visible enchants.
	 * @return this
	 */
	public ItemBuilder enchantEffect()
	{
		return enchant(Enchantment.DURABILITY, 1).flag(ItemFlag.HIDE_ENCHANTS);
	}
	
	/**
	 * Applies the provided enchantment to the item.
	 * @param enchantment
	 * @param level
	 * @return this
	 */
	public ItemBuilder enchant(Enchantment enchantment, int level)
	{
		if(enchantment == null)
			return this;
		if(level <= 0)
		{
			enchantments.remove(enchantment);
			return this;
		}
		
		if(enchantments == null)
			enchantments = new HashMap<>();
		enchantments.put(enchantment, level);
		return this;
	}
	
	/**
	 * Applies the provided enchantment to the item at level 1.
	 * @param enchantment
	 * @return this
	 */
	public ItemBuilder enchant(Enchantment enchantment)
	{
		return enchant(enchantment, 1);
	}
	
	/**
	 * Removes the specified {@link org.bukkit.enchantments.Enchantment} from this item.
	 * @param enchantment
	 * @return this
	 */
	public ItemBuilder unenchant(Enchantment enchantment)
	{
		return enchant(enchantment, 0);
	}
	
	/**
	 * Applies the provided {@link org.bukkit.inventory.ItemFlag}
	 * @param flag
	 * @return this
	 */
	public ItemBuilder flag(ItemFlag flag)
	{
		if(flags == null)
			flags = new HashSet<>();
		flags.add(flag);
		return this;
	}
	
	/**
	 * Builds an {@link org.bukkit.inventory.ItemStack} with all the data in this ItemBuilder.
	 * @return The built {@link org.bukkit.inventory.ItemStack}
	 */
	public ItemStack build()
	{
		if(material == null)
			throw new IllegalStateException("Cannot build ItemStack with null material");
		if(amount < 1)
			throw new IllegalStateException("Cannot build ItemStack with amount less than 1");
		
		ItemStack item = new ItemStack(material, amount, (short)metadata);
//		ItemStack item = CraftItemStack.asCraftCopy(new ItemStack(material, amount, (short)metadata));
		if(enchantments != null)
			item.addUnsafeEnchantments(enchantments);
		
		if(name != null || lore != null || (flags != null && !flags.isEmpty()))
		{
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(name);
			meta.setLore(lore);
			if(flags != null)
				flags.forEach(meta::addItemFlags);
			item.setItemMeta(meta);
		}
		
		return item;
	}
	
	public ItemBuilder clone()
	{
		return new ItemBuilder(material, metadata, amount, name, lore == null ? null : new ArrayList<>(lore), enchantments == null ? null : new HashMap<>(enchantments), flags == null ? null : new HashSet<>(flags));
	}
	
	/**
	 *
	 * @return The {@link org.bukkit.Material} associated with this ItemBuilder.
	 */
	public Material getMaterial()
	{
		return material;
	}
	
	/**
	 *
	 * @return The number of items in the stack.
	 */
	public int getAmount()
	{
		return amount;
	}
	
	/**
	 *
	 * @return The metadata or durability of this item.
	 */
	public int getMetadata()
	{
		return metadata;
	}
	
	/**
	 *
	 * @return The display name of this item.
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 *
	 * @return The enchantments to be applied to the item.
	 */
	public Map<Enchantment, Integer> getEnchantments()
	{
		return Collections.unmodifiableMap(enchantments);
	}
	
	/**
	 *
	 * @return The lore of this item.
	 */
	public List<String> getLore()
	{
		return Collections.unmodifiableList(lore);
	}
	
	/**
	 *
	 * @return The flags to be applied to this item.
	 */
	public Set<ItemFlag> getFlags()
	{
		return Collections.unmodifiableSet(flags);
	}
}
