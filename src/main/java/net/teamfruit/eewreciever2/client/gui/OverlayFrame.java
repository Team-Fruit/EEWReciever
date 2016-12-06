package net.teamfruit.eewreciever2.client.gui;

import org.lwjgl.util.Timer;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.teamfruit.eewreciever2.lib.OpenGL;
import net.teamfruit.eewreciever2.lib.bnnwidget.WFrame;
import net.teamfruit.eewreciever2.lib.bnnwidget.WPanel;
import net.teamfruit.eewreciever2.lib.bnnwidget.position.R;

public class OverlayFrame extends WFrame {
	public static final OverlayFrame instance = new OverlayFrame();

	protected boolean initialized;
	public GuiOverlay pane = new GuiOverlay(new R());
	private boolean d;

	private OverlayFrame() {
		this.mc = FMLClientHandler.instance().getClient();
	}

	@Override
	protected void initWidget() {
		add(this.pane);
	}

	@SubscribeEvent
	public void onRenderTick(final TickEvent.RenderTickEvent event) {
		Timer.tick();
	}

	@SubscribeEvent
	public void onDraw(final GuiScreenEvent.DrawScreenEvent.Post event) {
		if (!isDelegated()) {
			setWidth(event.gui.width);
			setHeight(event.gui.height);
			OpenGL.glPushMatrix();
			OpenGL.glTranslatef(0f, 0f, 1000f);
			drawScreen(event.mouseX, event.mouseY, event.renderPartialTicks);
			OpenGL.glPopMatrix();
		}
	}

	@SubscribeEvent
	public void onDraw(final RenderGameOverlayEvent.Post event) {
		if (event.type==ElementType.CHAT&&FMLClientHandler.instance().getClient().currentScreen==null)
			if (!isDelegated()) {
				setWidth(event.resolution.getScaledWidth());
				setHeight(event.resolution.getScaledHeight());
				drawScreen(event.mouseX, event.mouseY, event.partialTicks);
			}
	}

	@Override
	public void drawScreen(final int mousex, final int mousey, final float f) {
		if (!this.initialized) {
			setWorldAndResolution(FMLClientHandler.instance().getClient(), (int) width(), (int) height());
			this.initialized = true;
		}
		super.drawScreen(mousex, mousey, f);
	}

	@SubscribeEvent
	public void onTick(final ClientTickEvent event) {
		updateScreen();
	}

	public void delegate() {
		this.d = true;
	}

	public void release() {
		this.d = false;
	}

	public boolean isDelegated() {
		return this.d;
	}

	public static class GuiOverlay extends WPanel {

		public GuiOverlay(final R position) {
			super(position);
		}
	}
}
