package org.powerbot.script.methods;

import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import org.powerbot.bot.Bot;
import org.powerbot.client.Client;
import org.powerbot.script.internal.methods.Items;
import org.powerbot.script.internal.methods.Map;

public class MethodContext {
	private AtomicReference<Client> client;
	private AtomicInteger preferredWorld;
	private AtomicReference<Bot> bot;

	public Properties properties;
	public Antipatterns antipatterns;
	public CombatBar combatBar;
	public Bank bank;
	public Camera camera;
	public Chat chat;
	public DepositBox depositBox;
	public Environment environment;
	public Equipment equipment;
	public Game game;
	public GroundItems groundItems;
	public HintArrows hintArrows;
	public Hud hud;
	public Backpack backpack;
	public Keyboard keyboard;
	public Lobby lobby;
	public Menu menu;
	public Mouse mouse;
	public Movement movement;
	public Npcs npcs;
	public Objects objects;
	public Players players;
	public Powers powers;
	public Projectiles projectiles;
	public Settings settings;
	public Skills skills;
	public Summoning summoning;
	public Widgets widgets;

	Items items;
	Map map;

	public MethodContext(final Bot bot) {
		this.client = new AtomicReference<Client>(null);
		this.preferredWorld = new AtomicInteger(-1);
		this.bot = new AtomicReference<Bot>(bot);

		properties = new Properties();
		antipatterns = new Antipatterns(this);
		combatBar = new CombatBar(this);
		backpack = new Backpack(this);
		bank = new Bank(this);
		camera = new Camera(this);
		chat = new Chat(this);
		depositBox = new DepositBox(this);
		environment = new Environment(this);
		equipment = new Equipment(this);
		game = new Game(this);
		groundItems = new GroundItems(this);
		hintArrows = new HintArrows(this);
		hud = new Hud(this);
		keyboard = new Keyboard(this);
		lobby = new Lobby(this);
		menu = new Menu(this);
		mouse = new Mouse(this);
		movement = new Movement(this);
		npcs = new Npcs(this);
		objects = new Objects(this);
		players = new Players(this);
		powers = new Powers(this);
		projectiles = new Projectiles(this);
		settings = new Settings(this);
		skills = new Skills(this);
		summoning = new Summoning(this);
		widgets = new Widgets(this);

		items = new Items(this);
		map = new Map(this);
	}

	public MethodContext() {
		this.client = new AtomicReference<Client>(null);
		this.preferredWorld = new AtomicInteger(-1);
		this.bot = new AtomicReference<Bot>(null);
	}

	public void init(final MethodContext ctx) {
		if (this.client.get() != null) {
			return;
		}
		client = ctx.client;
		preferredWorld = ctx.preferredWorld;
		bot = ctx.bot;

		properties = ctx.properties;
		antipatterns = ctx.antipatterns;
		combatBar = ctx.combatBar;
		backpack = ctx.backpack;
		bank = ctx.bank;
		camera = ctx.camera;
		chat = ctx.chat;
		depositBox = ctx.depositBox;
		environment = ctx.environment;
		equipment = ctx.equipment;
		game = ctx.game;
		groundItems = ctx.groundItems;
		hintArrows = ctx.hintArrows;
		hud = ctx.hud;
		keyboard = ctx.keyboard;
		lobby = ctx.lobby;
		menu = ctx.menu;
		mouse = ctx.mouse;
		movement = ctx.movement;
		npcs = ctx.npcs;
		objects = ctx.objects;
		players = ctx.players;
		powers = ctx.powers;
		projectiles = ctx.projectiles;
		settings = ctx.settings;
		skills = ctx.skills;
		summoning = ctx.summoning;
		widgets = ctx.widgets;

		items = ctx.items;
		map = ctx.map;
	}

	public void setClient(final Client client) {
		this.client.set(client);
	}

	public Client getClient() {
		return this.client.get();
	}

	public Bot getBot() {
		return this.bot.get();
	}

	/**
	 * Gets the preferred world.
	 *
	 * @return the requested preferred world (if set)
	 * @deprecated see {@link #properties}
	 */
	@Deprecated
	public int getPreferredWorld() {
		int w = -1;
		if (properties.containsKey("login.world")) {
			try {
				w = Integer.parseInt(properties.getProperty("login.world"));
			} catch (final NumberFormatException ignored) {
			}
		}
		return w;
	}

	/**
	 * Sets the preferred world.
	 *
	 * @param world the preferred world to log into
	 * @deprecated see {@link #properties}
	 */
	@Deprecated
	void setPreferredWorld(final int world) {
		properties.setProperty("login.world", Integer.toString(world));
	}
}
