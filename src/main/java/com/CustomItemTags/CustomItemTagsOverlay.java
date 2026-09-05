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

import java.awt.*;
import java.util.Collection;

import net.runelite.api.widgets.InterfaceID;
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
        showOnInterfaces(InterfaceID.KEPT_ON_DEATH, InterfaceID.GUIDE_PRICES, InterfaceID.LOOTING_BAG, InterfaceID.SEED_BOX, InterfaceID.KINGDOM);
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
                renderText(graphics, widgetItem.getCanvasBounds(), item.tag, item.loc);
                break;
            }
        }
    }

    private void renderText(Graphics2D graphics, Rectangle bounds, String itemTag, CustomTagLocation loc)
    {
        final TextComponent textComponent = new TextComponent();
//        textComponent.setColor(Color.white);  //TODO can add colors here in the future, modify fromString
        textComponent.setText(itemTag);
        var textDimension = textComponent.render(graphics);

        int itemOverlayX = (int) ((
            loc == CustomTagLocation.BOTTOM_LEFT ||
                loc == CustomTagLocation.TOP_LEFT
        )
            ? bounds.getMinX()
            : bounds.getMaxX() - textDimension.getWidth() - 1
        );

        int itemOverlayY = (int) ((
            loc == CustomTagLocation.TOP_LEFT ||
               loc == CustomTagLocation.TOP_RIGHT
        )
            ? bounds.getMinY() + textDimension.getHeight() - 1
            : bounds.getMaxY()
        );

//        textComponent.setPosition(new Point(bounds.x - 1, ));
        textComponent.setPosition(new Point(itemOverlayX, itemOverlayY));
        textComponent.render(graphics);
    }

}