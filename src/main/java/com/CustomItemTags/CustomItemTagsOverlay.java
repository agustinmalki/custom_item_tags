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

import com.google.inject.Inject;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Collection;

import static net.runelite.api.widgets.WidgetID.GUIDE_PRICE_GROUP_ID;
import static net.runelite.api.widgets.WidgetID.KEPT_ON_DEATH_GROUP_ID;
import static net.runelite.api.widgets.WidgetID.LOOTING_BAG_GROUP_ID;
import static net.runelite.api.widgets.WidgetID.SEED_BOX_GROUP_ID;
import static net.runelite.api.widgets.WidgetID.KINGDOM_GROUP_ID;

import net.runelite.api.widgets.WidgetItem;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.overlay.WidgetItemOverlay;
import net.runelite.client.ui.overlay.components.TextComponent;

class CustomItemTagsOverlay extends WidgetItemOverlay
{
    private final CustomItemTagsConfig config;
    private final ItemManager itemManager;

    @Inject
    CustomItemTagsOverlay(CustomItemTagsConfig config, ItemManager itemManager)
    {
        this.config = config;
        this.itemManager = itemManager;
        showOnInventory();
        showOnBank();
        showOnInterfaces(KEPT_ON_DEATH_GROUP_ID, GUIDE_PRICE_GROUP_ID, LOOTING_BAG_GROUP_ID, SEED_BOX_GROUP_ID, KINGDOM_GROUP_ID);
    }

    static final Collection<CustomTag> customTags = CustomItemTagsPlugin.getTags();

    @Override
    public void renderItemOverlay(Graphics2D graphics, int itemId, WidgetItem widgetItem)
    {
        for (CustomTag item : customTags)
        {
            if (item.itemID == itemId)
            {
                graphics.setFont(FontManager.getRunescapeSmallFont());
                renderText(graphics, widgetItem.getCanvasBounds(), item.tag);
                break;
            }
        }
    }

    private void renderText(Graphics2D graphics, Rectangle bounds, String itemTag)
    {
        final TextComponent textComponent = new TextComponent();
        textComponent.setPosition(new Point(bounds.x - 1, bounds.y + bounds.height - 1));
        textComponent.setColor(Color.white);  //TODO can add colors here in the future, modify fromString
        textComponent.setText(itemTag);
        textComponent.render(graphics);
    }

}