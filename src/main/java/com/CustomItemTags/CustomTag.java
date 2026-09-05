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

import java.util.Arrays;

public class CustomTag
{
    public final String tag;
    public final int itemID;
    public final CustomTagLocation loc;
    CustomTag(String tag, int itemID)
    {
        this(tag, itemID, CustomTagLocation.BOTTOM_LEFT);
    }

    CustomTag(String tag, int itemID, CustomTagLocation location)
    {
        this.itemID = itemID;
        this.tag = tag;
        this.loc = location;
    }

    public CustomTag withLoc(CustomTagLocation l)
    {
        if (l == loc)
        {
            return this;
        } else {
            return new CustomTag(tag, itemID, l);
        }
    }

    public String toLine() {
        String toRet = this.tag + ", " +  this.itemID;
        if (this.loc != CustomTagLocation.BOTTOM_LEFT)
        {
            toRet = toRet + ", " + this.loc.getAbbriviated();
        }
        return toRet;
    }

    static CustomTag fromString(String s)
    {
        String[] split = s.split(",");
        String tag = split[0].trim();
        String iD = split.length > 1 ? split[1].toLowerCase().trim() : "";
        int ItemID =  Integer.parseInt(iD.replaceAll("[\\D]", ""));

        CustomTagLocation loc = null;
        if(split.length > 2) loc = CustomTagLocation.fromStr(split[2].toLowerCase().trim());

        return (loc == null) ? new CustomTag(tag, ItemID) : new CustomTag(tag, ItemID, loc);
    }
}