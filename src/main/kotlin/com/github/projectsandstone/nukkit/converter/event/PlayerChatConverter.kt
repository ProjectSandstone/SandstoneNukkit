/**
 *      SandstoneNukkit - Nukkit implementation of SandstoneCommon
 *
 *         The MIT License (MIT)
 *
 *      Copyright (c) 2016 Sandstone <https://github.com/ProjectSandstone/>
 *      Copyright (c) contributors
 *
 *
 *      Permission is hereby granted, free of charge, to any person obtaining a copy
 *      of this software and associated documentation files (the "Software"), to deal
 *      in the Software without restriction, including without limitation the rights
 *      to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *      copies of the Software, and to permit persons to whom the Software is
 *      furnished to do so, subject to the following conditions:
 *
 *      The above copyright notice and this permission notice shall be included in
 *      all copies or substantial portions of the Software.
 *
 *      THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *      IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *      FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *      AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *      LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *      OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *      THE SOFTWARE.
 */
package com.github.projectsandstone.nukkit.converter.event

import cn.nukkit.event.player.PlayerChatEvent
import com.github.jonathanxd.adapterhelper.Adapter
import com.github.jonathanxd.adapterhelper.AdapterManager
import com.github.jonathanxd.adapterhelper.Converter
import com.github.jonathanxd.iutils.type.TypeInfo
import com.github.projectsandstone.api.entity.living.player.Player
import com.github.projectsandstone.api.event.SandstoneEventFactory
import com.github.projectsandstone.api.event.player.PlayerMessageChannelEvent
import com.github.projectsandstone.api.text.Text
import com.github.projectsandstone.api.text.channel.MessageReceiver
import com.github.projectsandstone.common.util.extension.adapt
import com.github.projectsandstone.common.util.extension.convert
import com.github.projectsandstone.nukkit.channel.NukkitChannel
import com.github.projectsandstone.nukkit.util.alias.NukkitPlayer

object PlayerChatConverter : Converter<PlayerChatEvent, PlayerMessageChannelEvent> {

    override fun convert(input: PlayerChatEvent, adapter: Adapter<*>?, manager: AdapterManager): PlayerMessageChannelEvent {

        val members = manager.adaptAll(NukkitPlayer::class.java, input.recipients.filterIsInstance<NukkitPlayer>(), MessageReceiver::class.java).toMutableList()
        val channel = NukkitChannel(members)
        val sandPlayer: Player = manager.adapt(input.player)
        val message = manager.convert<String, Text>(input.message, adapter)

        return SandstoneEventFactory.createEvent(TypeInfo.aEnd(PlayerMessageChannelEvent::class.java), mapOf(
                "channel" to channel,
                "message" to message,
                "entity" to sandPlayer,
                "player" to sandPlayer
        ))
    }

}