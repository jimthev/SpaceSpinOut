package com.dragonjetgames.spacespinout.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.dragonjetgames.spacespinout.SpaceSpinOut;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Space SpinOut!";
		config.fullscreen = false;
		config.resizable = true;
		config.samples = 4;
		config.addIcon("icons/mascot_head_32.png", Files.FileType.Internal);

		new LwjglApplication(new SpaceSpinOut(), config);
	}
}
