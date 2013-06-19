package org.powerbot.script.lang;

public interface Identifiable {
	public int getId();

	public interface Query<T> {
		public T id(int... ids);

		public T id(int[]... ids);

		public T id(Identifiable... ids);
	}

	public class Matcher implements Predicate<Identifiable> {
		private final int[] ids;

		public Matcher(final int... ids) {
			this.ids = ids;
		}

		public Matcher(final Identifiable... ids) {
			this.ids = new int[ids.length];
			for (int i = 0; i < ids.length; i++) {
				this.ids[i] = ids[i].getId();
			}
		}

		@Override
		public boolean apply(final Identifiable i) {
			final int x = i != null ? i.getId() : -1;
			if (x == -1) return false;
			for (int id : ids) {
				if (id == x) {
					return true;
				}
			}
			return false;
		}
	}
}
