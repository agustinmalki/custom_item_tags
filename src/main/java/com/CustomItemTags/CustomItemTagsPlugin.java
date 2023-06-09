/*
 * Copyright (c) 2023, Agustin <amalki.career@gmail.com>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.CustomItemTags;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.KeyCode;
import java.util.ArrayList;
import java.util.List;
import java.util.Collection;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.api.events.MenuOpened;
import net.runelite.api.MenuEntry;
import net.runelite.api.MenuAction;
import net.runelite.client.input.KeyManager;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetID;
import net.runelite.api.widgets.WidgetInfo;

@Slf4j
@PluginDescriptor(
	name = "Custom Item Tags"
)
public class CustomItemTagsPlugin extends Plugin
{
	static Collection<CustomTag> customTags = new ArrayList<>();
	@Inject
	private Client client;
	@Inject
	private CustomItemTagsConfig config;
	@Inject
	private OverlayManager overlayManager;
	@Inject
	private CustomItemTagsOverlay overlay;
	@Inject
	private KeyManager keyManager;

	@Provides
	CustomItemTagsConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(CustomItemTagsConfig.class);
	}

	public void reloadCustomSwaps()
	{
		customTags.clear();
		customTags.addAll(loadCustomTags(config.getCustomTags()));
	}

	private Collection<? extends CustomTag> loadCustomTags(String customTags)
	{
		List<CustomTag> tags = new ArrayList<>();
		for (String customTag : customTags.split("\n"))
		{
			if (customTag.trim().equals("")) continue;
			tags.add(CustomTag.fromString(customTag));
		}
		return tags;
	}

	protected static Collection<CustomTag> getTags(){
		return customTags;
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged configChanged) {
		if (configChanged.getGroup().equals("Custom Item Tags")) {
			reloadCustomSwaps();
		}
	}

	@Subscribe
	public void onMenuOpened(final MenuOpened event)
	{
		if (!client.isKeyPressed(KeyCode.KC_SHIFT) || !config.shiftMenuOption())
		{
			return;
		}

		final MenuEntry[] entries = event.getMenuEntries();
		for (int idx = entries.length - 1; idx >= 0; --idx)
		{
			final MenuEntry entry = entries[idx];
			final Widget w = entry.getWidget();

			if (w != null && WidgetInfo.TO_GROUP(w.getId()) == WidgetID.INVENTORY_GROUP_ID
					&& "Examine".equals(entry.getOption()) && entry.getIdentifier() == 10)
			{
				final int itemId = w.getItemId();
				final String[] name1 = w.getName().split(">");
				final String[] name2 = name1[1].split("<"); //there's gotta be a better way to get this name without color
				String itemName = name2[0];
				//final Tag tag = getTag(itemId);

				final MenuEntry parent = client.createMenuEntry(idx)
						.setOption("Text tag")
						.setTarget(entry.getTarget())
						.setType(MenuAction.RUNELITE)
						.onClick(e -> addTag(itemId,itemName)); //TODO add dialog box for custom name

				/*client.createMenuEntry(idx)
						.setOption("Pick")
						.setType(MenuAction.RUNELITE)
						.setParent(parent).
				if (tag != null)
				{
					client.createMenuEntry(idx)
							.setOption("Remove text")
							.setType(MenuAction.RUNELITE)
							.setParent(parent)
							.onClick(e -> removeTag(itemId)); //TODO implement add/remove submenus
				}
				 */
			}
		}
	}

	void addTag(int itemId, String itemName)
	{
		String n = config.getCustomTags().concat("\n").concat(itemName).concat(",").concat(Integer.toString(itemId));

		config.setCustomTags(n);
	}

	@Override
	protected void startUp()
	{
		reloadCustomSwaps();
		overlayManager.add(overlay);
	}

	@Override
	protected void shutDown()
	{
		overlayManager.remove(overlay);
	}

}
