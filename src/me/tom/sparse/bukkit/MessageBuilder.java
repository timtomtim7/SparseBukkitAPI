package me.tom.sparse.bukkit;

import me.tom.sparse.bukkit.tag.TagUtils;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

/**
 * TODO: Documentation
 */
public final class MessageBuilder
{
	private Supplier<Collection<? extends Player>> playerSupplier;
	private ChatColor normalColor;
	private ChatColor normalVariableColor;
	private ChatColor errorColor;
	private ChatColor errorVariableColor;
	
	public static MessageBuilder broadcaster()
	{
		return new MessageBuilder(Bukkit::getOnlinePlayers);
	}
	
	public MessageBuilder(Player... players)
	{
		this(players == null ? null : Arrays.asList(players));
	}
	
	public MessageBuilder(Collection<Player> players)
	{
		this(players == null ? null : () -> players);
	}
	
	public MessageBuilder(Supplier<Collection<? extends Player>> playerSupplier)
	{
		if(playerSupplier == null)
			throw new IllegalArgumentException("Player supplier cannot be null.");
		this.playerSupplier = playerSupplier;
	}
	
	public Supplier<Collection<? extends Player>> getPlayerSupplier()
	{
		return playerSupplier;
	}
	
	public Collection<? extends Player> getPlayers()
	{
		return playerSupplier.get();
	}
	
//	public MessagePart prefix(String prefix)
//	{
//		return new MessagePart(prefix);
//	}
//
//	public MessagePart prefix(ChatColor color, String text)
//	{
//		return prefix(color+"\u00a7l"+text+" \u00a7f\u00a7l>> ");
//	}
//
//	public MessagePart p(String prefix)
//	{
//		return prefix(prefix);
//	}
//
//	public MessagePart p(ChatColor color, String text)
//	{
//		return prefix(color, text);
//	}
//
//	public MessagePart basic()
//	{
//		return new MessagePart("\u00a7f\u00a7l>> ");
//	}
//
//	public MessagePart b()
//	{
//		return basic();
//	}
	
	public MessagePart basic()
	{
		return style(ChatColor.GRAY, ChatColor.AQUA, ChatColor.RED, ChatColor.GOLD);
	}
	
	public MessagePart style(ChatColor normal, ChatColor variable, ChatColor error, ChatColor errorVariable)
	{
		this.normalColor = normal;
		this.normalVariableColor = variable;
		this.errorColor = error;
		this.errorVariableColor = errorVariable;
		return new MessagePart("");
	}
	
	public void spacer()
	{
		new MessagePart("\u00a7m-------------------------------").send();
	}
	
	public interface IMessagePart
	{
		IMessagePart all();
		IMessagePart next();
		IMessagePart previous();
		
		default IMessagePart beginning()
		{
			IMessagePart previous = previous();
			return previous == null ? this : previous;
		}
		
		default IMessagePart end()
		{
			IMessagePart next = next();
			return next == null ? this : next;
		}
		
		IMessagePart normal(String legacyText);
		IMessagePart error(String legacyText);
		IMessagePart variable(String legacyText);
		
		default IMessagePart n(String legacyText)
		{
			return normal(legacyText);
		}
		
		default IMessagePart e(String legacyText)
		{
			return error(legacyText);
		}
		
		default IMessagePart v(String legacyText)
		{
			return variable(legacyText);
		}
		
		IMessagePart withItem(ItemStack item);
		IMessagePart withHoverText(String legacyText);
		
		IMessagePart withCommand(String command);
		IMessagePart withCommandSuggestion(String suggestion);
		
		default IMessagePart command(String command)
		{
			return variable("/"+command).withCommand(command).withHoverText("/"+command);
		}
		
		default IMessagePart commandSuggestion(String commandWithArgs, String commandWithoutArgs)
		{
			return variable("/"+commandWithArgs).withCommandSuggestion(commandWithoutArgs+" ").withHoverText("/"+commandWithArgs);
		}
		
		default IMessagePart c(String command)
		{
			return command(command);
		}
		
		default IMessagePart cs(String commandWithArgs, String commandWithoutArgs)
		{
			return commandSuggestion(commandWithArgs, commandWithoutArgs);
		}
		
		default List<IMessagePart> toList()
		{
			IMessagePart beginning = beginning();
			if(beginning == this)
			{
				List<IMessagePart> list = new ArrayList<>();
				
				IMessagePart next = this;
				while(next != null)
				{
					list.add(next);
					next = next.next();
				}
				 
				return list;
			}else{
				return beginning.toList();
			}
		}
		
		BaseComponent[] build();
		void send();
		
		default void s()
		{
			send();
		}
	}
	
	public class MessagePart implements IMessagePart
	{
		private MessagePart previous;
		private MessagePart next;
		
		private String legacyText;
	
		private ClickEvent.Action clickAction;
		private String clickData;
		
		private HoverEvent.Action hoverAction;
		private BaseComponent[] hoverData;
		
		private ChatColor variableColor = ChatColor.GREEN;
		
		private MessagePart(String legacyText)
		{
			this.legacyText = legacyText;
		}
		
		private MessagePart(String legacyText, ClickEvent.Action clickAction, String clickData, HoverEvent.Action hoverAction, BaseComponent[] hoverData)
		{
			this.legacyText = legacyText;
			this.clickAction = clickAction;
			this.clickData = clickData;
			this.hoverAction = hoverAction;
			this.hoverData = hoverData;
		}
		
		public AllMessagePart all()
		{
			return new AllMessagePart(this);
		}
		
		public MessagePart next()
		{
			return next;
		}
	
		public MessagePart previous()
		{
			return previous;
		}
		
		public BaseComponent[] build()
		{
			if(previous == null)
			{
				List<BaseComponent> components = new ArrayList<>();
				
				addToBuild(components);
				
				return components.toArray(new BaseComponent[components.size()]);
			}else{
				return beginning().build();
			}
		}
		
		private void addToBuild(List<BaseComponent> build)
		{
			BaseComponent[] components = TextComponent.fromLegacyText(legacyText);
		
			if(clickAction != null && clickData != null)
				for(BaseComponent component : components)
					component.setClickEvent(new ClickEvent(clickAction, clickData));
			
			if(hoverAction != null && hoverData != null && hoverData.length != 0)
				for(BaseComponent component : components)
					component.setHoverEvent(new HoverEvent(hoverAction, hoverData));
			
			build.addAll(Arrays.asList(components));
			
			if(next != null)
				next.addToBuild(build);
		}
		
		private MessagePart setVariableColor(ChatColor color)
		{
			this.variableColor = color;
			return this;
		}
		
		public void send()
		{
			for(Player p : getPlayers())
			{
				if(!p.isOnline())
					continue;
				p.spigot().sendMessage(build());
			}
		}
		
		private MessagePart createNext(String legacyText)
		{
			next = new MessagePart(legacyText);
			next.previous = this;
			
			return next;
		}
		
		private MessagePart createNext(ChatColor color, String legacyText)
		{
			return createNext(color+legacyText);
		}
		
		public MessagePart normal(String legacyText)
		{
			return createNext(normalColor, legacyText).setVariableColor(normalVariableColor);
		}
		
		public MessagePart error(String legacyText)
		{
			return createNext(errorColor, legacyText).setVariableColor(errorVariableColor);
		}
		
		public MessagePart variable(String legacyText)
		{
			return createNext(variableColor, legacyText).setVariableColor(variableColor);
		}
		
		public MessagePart withItem(ItemStack item)
		{
			if(item == null || item.getAmount() == 0 || item.getType() == Material.AIR)
				throw new IllegalArgumentException("Invalid item.");
		
			hoverAction = HoverEvent.Action.SHOW_ITEM;
			hoverData = new BaseComponent[] {new TextComponent(TagUtils.toTag(item).toString())};
			
			return this;
		}
		
		public MessagePart withHoverText(String legacyText)
		{
			if(legacyText == null || legacyText.isEmpty())
				throw new IllegalArgumentException("Invalid text.");
			
			hoverAction = HoverEvent.Action.SHOW_TEXT;
			hoverData = TextComponent.fromLegacyText(legacyText);
			
			return this;
		}
		
		public MessagePart withCommand(String command)
		{
			if(command == null || command.isEmpty())
				throw new IllegalArgumentException("Invalid command.");
			
			clickAction = ClickEvent.Action.RUN_COMMAND;
			clickData = "/"+command;
			
			return this;
		}
		
		public MessagePart withCommandSuggestion(String command)
		{
			if(command == null || command.isEmpty())
				throw new IllegalArgumentException("Invalid command suggestion.");
			
			clickAction = ClickEvent.Action.SUGGEST_COMMAND;
			clickData = "/"+command;
			
			return this;
		}
	}
	
	public class AllMessagePart implements IMessagePart
	{
		private IMessagePart beginning;
		
		public AllMessagePart(IMessagePart messagePart)
		{
			this.beginning = messagePart.beginning();
		}
		
		@Override
		public IMessagePart all()
		{
			return this;
		}
		
		@Override
		public IMessagePart next()
		{
			return beginning.end();
		}
		
		@Override
		public IMessagePart previous()
		{
			return beginning.end().previous();
		}
		
		@Override
		public IMessagePart normal(String legacyText)
		{
			return beginning.end().normal(legacyText);
		}
		
		@Override
		public IMessagePart error(String legacyText)
		{
			return beginning.end().error(legacyText);
		}
		
		@Override
		public IMessagePart variable(String legacyText)
		{
			return beginning.end().variable(legacyText);
		}
		
		@Override
		public IMessagePart withItem(ItemStack item)
		{
			toList().forEach(m -> m.withItem(item));
			return this;
		}
		
		@Override
		public IMessagePart withHoverText(String legacyText)
		{
			toList().forEach(m -> m.withHoverText(legacyText));
			return this;
		}
		
		@Override
		public IMessagePart withCommand(String legacyText)
		{
			toList().forEach(m -> m.withCommand(legacyText));
			return this;
		}
		
		@Override
		public IMessagePart withCommandSuggestion(String legacyText)
		{
			toList().forEach(m -> m.withCommandSuggestion(legacyText));
			return this;
		}
		
		@Override
		public List<IMessagePart> toList()
		{
			return beginning.toList();
		}
		
		@Override
		public BaseComponent[] build()
		{
			return beginning.build();
		}
		
		@Override
		public void send()
		{
			beginning.send();
		}
	}
}
