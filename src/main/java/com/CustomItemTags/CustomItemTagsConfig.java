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

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;

@ConfigGroup("Custom Item Tags")
public interface CustomItemTagsConfig extends Config
{
	@ConfigSection(name = "Instructions", description = "instructions", position = 0, closedByDefault = true)
	String customTagsInstructions = "Custom tags instructions";

	@ConfigItem(
			keyName = "customSwapperInstructions",
			name = "Click to reset instructions",
			description = "Instructions for how to add tags.",
			section = customTagsInstructions,
			position = 2
	)
	default String customTagsInstructions() {
		return "### Basic use:\nAdd tags, one per line. Format is \"text,item ID\". Item IDs can be found on https://www.osrsbox.com/tools/item-search/. \n";
	}

	@ConfigSection(name = "Custom Tags", description = "List custom item tags here", position = 1)
	String customTags = "Custom Tags";
	@ConfigItem(
			keyName = "customTags",
			name = "Custom tags",
			description = "Options for item text.",
			section = customTags,
			position = 0
	)
	default String customTags() {
		return "";
	}

	//TODO add shift click menu option and toggle on/off to avoid menu clutter
}
