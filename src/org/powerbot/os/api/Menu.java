package org.powerbot.os.api;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import org.powerbot.os.api.util.Condition;
import org.powerbot.os.api.util.Filter;
import org.powerbot.os.bot.event.PaintListener;
import org.powerbot.os.client.Client;
import org.powerbot.os.util.StringUtils;

public class Menu extends ClientAccessor {
	private final AtomicBoolean registered;
	private final AtomicReference<String[]> actions, options;

	public Menu(ClientContext ctx) {
		super(ctx);
		this.registered = new AtomicBoolean(false);
		final String[] e = new String[0];
		this.actions = new AtomicReference<String[]>(e);
		this.options = new AtomicReference<String[]>(e);
	}

	public static Filter<Entry> filter(final String action) {
		return filter(action, null);
	}

	public static Filter<Entry> filter(final String action, final String option) {
		final String a = action != null ? action.toLowerCase() : null;
		final String o = option != null ? option.toLowerCase() : null;
		return new Filter<Entry>() {
			@Override
			public boolean accept(final Entry entry) {
				return (a == null || entry.action.toLowerCase().contains(a)) &&
						(o == null || entry.option.toLowerCase().contains(o));
			}
		};
	}

	public boolean isOpen() {
		final Client client = ctx.client();
		return client != null && client.isMenuOpen();
	}

	public int indexOf(final Filter<Entry> filter) {
		final String[] actions = this.actions.get(), options = this.options.get();
		final int len;
		if ((len = actions.length) != options.length) {
			return -1;
		}
		for (int i = 0; i < len; i++) {
			if (filter.accept(new Entry(actions[i], options[i]))) {
				return i;
			}
		}
		return -1;
	}

	public boolean hover(final Filter<Entry> filter) {
		final Client client = ctx.client();
		if (client == null || indexOf(filter) == -1) {
			return false;
		}
		if (!client.isMenuOpen()) {
			//TODO: right click.
		}
		final int idx;
		if (!Condition.wait(new Callable<Boolean>() {
			@Override
			public Boolean call() {
				return client.isMenuOpen();
			}
		}, 15, 10) || (idx = indexOf(filter)) == -1) {
			return false;
		}
		final Rectangle rectangle = new Rectangle(client.getMenuX(), client.getMenuY() + 19 + idx * 15, client.getMenuWidth(), 15);
		//TODO: move mouse
		return false;//TODO check if menu is open
	}

	private void register() {
		if (!this.registered.compareAndSet(false, true)) {
			return;
		}
		ctx.bot().dispatcher.add(new PaintListener() {
			@Override
			public void repaint(final Graphics render) {
				final Client client = ctx.client();
				if (client == null) {
					return;
				}

				final String[] actions = client.getMenuActions(), options = client.getMenuOptions();
				if (actions != null && options != null && actions.length == options.length) {
					Menu.this.actions.set(actions);
					Menu.this.options.set(options);
				}
			}
		});
	}

	public static class Entry {
		public final String action, option;

		private Entry(final String a, final String o) {
			this.action = a != null ? StringUtils.stripHtml(a) : "";
			this.option = o != null ? StringUtils.stripHtml(o) : "";
		}
	}
}
