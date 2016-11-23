package net.teamfruit.eewreciever2.lib.bnnwidget.component;

import java.text.DecimalFormat;

import org.apache.commons.lang3.math.NumberUtils;

import net.minecraft.client.gui.GuiScreen;
import net.teamfruit.eewreciever2.lib.bnnwidget.WEvent;
import net.teamfruit.eewreciever2.lib.bnnwidget.WPanel;
import net.teamfruit.eewreciever2.lib.bnnwidget.position.Area;
import net.teamfruit.eewreciever2.lib.bnnwidget.position.Coord;
import net.teamfruit.eewreciever2.lib.bnnwidget.position.Point;
import net.teamfruit.eewreciever2.lib.bnnwidget.position.R;

public class MNumber extends WPanel {
	public MButton neg;
	public MChatTextField field;
	public MButton pos;

	public MNumber(final R position, final float buttonwidth) {
		super(position);
		this.neg = new MButton(new R(Coord.left(0), Coord.width(buttonwidth), Coord.top(0), Coord.bottom(0))) {
			@Override
			protected boolean onClicked(final WEvent ev, final Area pgp, final Point p, final int button) {
				float f;
				if (GuiScreen.isShiftKeyDown())
					f = .1f;
				else if (GuiScreen.isCtrlKeyDown())
					f = .01f;
				else
					f = 1f;
				MNumber.this.field.setText(format(NumberUtils.toFloat(MNumber.this.field.getText(), 0)-f));
				return true;
			}
		}.setText("-");
		add(this.neg);
		this.field = new MChatTextField(new R(Coord.left(buttonwidth), Coord.right(buttonwidth), Coord.top(0), Coord.bottom(0))) {
			@Override
			protected void onTextChanged(final String oldText) {
				onNumberChanged(oldText, getText());
			}
		}.setAllowedCharacters("+-.eE0123456789").setWatermark("?");
		add(this.field);
		this.pos = new MButton(new R(Coord.right(0), Coord.width(buttonwidth), Coord.top(0), Coord.bottom(0))) {
			@Override
			protected boolean onClicked(final WEvent ev, final Area pgp, final Point p, final int button) {
				float f;
				if (GuiScreen.isShiftKeyDown())
					f = .1f;
				else if (GuiScreen.isCtrlKeyDown())
					f = .01f;
				else
					f = 1f;
				MNumber.this.field.setText(format(NumberUtils.toFloat(MNumber.this.field.getText(), 0)+f));
				return true;
			}
		}.setText("+");
		add(this.pos);
	}

	public MNumber setNumber(final float f) {
		this.field.setText(Float.isNaN(f) ? "" : format(f));
		return this;
	}

	public MNumber setPosLabel(final String s) {
		this.pos.setText(s);
		return this;
	}

	public MNumber setNegLabel(final String s) {
		this.neg.setText(s);
		return this;
	}

	protected void onNumberChanged(final String oldText, final String newText) {
	}

	private static final DecimalFormat signformat = new DecimalFormat(".##");

	public static String format(final float f) {
		if (f==0)
			return "0";

		final String str = signformat.format(f);

		final String cut = ".0";

		int end = str.length();
		int last = cut.length();

		while (end!=0&&last!=0)
			if (cut.charAt(last-1)==str.charAt(end-1))
				end--;
			else
				last--;
		return str.substring(0, end);
	}
}
