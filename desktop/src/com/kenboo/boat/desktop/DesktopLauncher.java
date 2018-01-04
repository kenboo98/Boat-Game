package com.kenboo.boat.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.kenboo.boat.GameScreen;
import com.kenboo.boat.Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = 1600;
		config.width = 900;
		new LwjglApplication(new Main(), config);
	}
}
