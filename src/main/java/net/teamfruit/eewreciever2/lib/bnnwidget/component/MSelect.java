package net.teamfruit.eewreciever2.lib.bnnwidget.component;

import java.util.List;

import com.google.common.collect.Lists;

import net.teamfruit.eewreciever2.lib.bnnwidget.WCommon;
import net.teamfruit.eewreciever2.lib.bnnwidget.WEvent;
import net.teamfruit.eewreciever2.lib.bnnwidget.WPanel;
import net.teamfruit.eewreciever2.lib.bnnwidget.position.Area;
import net.teamfruit.eewreciever2.lib.bnnwidget.position.Coord;
import net.teamfruit.eewreciever2.lib.bnnwidget.position.Point;
import net.teamfruit.eewreciever2.lib.bnnwidget.position.R;

public class MSelect extends WPanel {
	public MButton neg;
	public MChatTextField field;
	public MButton pos;

	protected float buttonwidth;

	public Selector<String> selector = new ListSelector();

	public void setSelector(final Selector<String> selector) {
		this.selector = selector;
	}

	protected Selector<String> getSelector() {
		return this.selector;
	}

	public MSelect(final R position, final float buttonwidth) {
		super(position);
		this.buttonwidth = buttonwidth;
		this.neg = new MButton(new R(Coord.left(0), Coord.width(buttonwidth), Coord.top(0), Coord.bottom(0))) {
			@Override
			protected boolean onClicked(final WEvent ev, final Area pgp, final Point p, final int button) {
				getSelector().prev();
				MSelect.this.setText(getSelector().get());
				return true;
			}
		}.setText("<");
		add(this.neg);
		add(getField());
		this.pos = new MButton(new R(Coord.right(0), Coord.width(buttonwidth), Coord.top(0), Coord.bottom(0))) {
			@Override
			protected boolean onClicked(final WEvent ev, final Area pgp, final Point p, final int button) {
				getSelector().next();
				MSelect.this.setText(getSelector().get());
				return true;
			}
		}.setText(">");
		add(this.pos);
	}

	@Override
	protected void initWidget() {
		setText(getSelector().get());
	}

	protected WCommon getField() {
		return this.field = new MChatTextField(new R(Coord.left(this.buttonwidth), Coord.right(this.buttonwidth), Coord.top(0), Coord.bottom(0))) {
			@Override
			protected void onTextChanged(final String oldText) {
				onChanged(oldText, getText());
			}
		};
	}

	protected void onChanged(final String oldText, final String newText) {
	}

	public MSelect setText(final String text) {
		this.field.setText(text);
		return this;
	}

	public MSelect setPosLabel(final String s) {
		this.pos.setText(s);
		return this;
	}

	public MSelect setNegLabel(final String s) {
		this.neg.setText(s);
		return this;
	}

	public static interface Selector<E> {
		E get();

		void next();

		void prev();
	}

	public static abstract class PosSelector<E> implements Selector<E> {
		@Override
		public void next() {
			this.current++;
		}

		@Override
		public void prev() {
			this.current--;
		}

		protected int current;

		public void setCurrentPos(final int current) {
			this.current = current;
		}

		public void setCurrentPos(final E o) {
			final int i = this.indexOf(o);
			if (i>=0)
				setCurrentPos(i);
		}

		protected abstract int indexOf(E o);

		protected abstract int length();

		public int getCurrentPos() {
			final int length = length();
			if (length<=0)
				return 0;
			return this.current = (this.current%length+length)%length;
		}
	}

	public static class ListSelector extends PosSelector<String> {
		protected List<?> list = Lists.newArrayList();

		public void setList(final List<String> list) {
			this.list = list;
		}

		protected List<?> getList() {
			return this.list;
		}

		@Override
		public int length() {
			return getList().size();
		}

		@Override
		protected int indexOf(final String o) {
			return this.list.indexOf(o);
		}

		@Override
		public String get() {
			final int length = length();
			final int current = getCurrentPos();
			if (current<0||current>=length)
				return "";
			return getList().get(current).toString();
		}
	}
}
