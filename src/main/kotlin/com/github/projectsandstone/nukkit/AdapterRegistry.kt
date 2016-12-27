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
package com.github.projectsandstone.nukkit

import com.github.projectsandstone.common.adapter.Adapters
import com.github.projectsandstone.common.util.extension.register
import com.github.projectsandstone.common.util.extension.registerConverter
import com.github.projectsandstone.nukkit.adapter.ServerAdapter
import com.github.projectsandstone.nukkit.adapter.block.BlockAdapter
import com.github.projectsandstone.nukkit.adapter.entity.living.player.PlayerAdapter
import com.github.projectsandstone.nukkit.adapter.world.ChunkFactory
import com.github.projectsandstone.nukkit.adapter.world.LevelWorldAdapter
import com.github.projectsandstone.nukkit.converter.*
import com.github.projectsandstone.nukkit.converter.event.PlayerChatConverter
import com.github.projectsandstone.nukkit.converter.event.PlayerCommandConverter
import com.github.projectsandstone.nukkit.converter.event.PlayerInteractConverter
import com.github.projectsandstone.nukkit.converter.event.ServerCommandConverter

object AdapterRegistry {
    fun registerAdapters() {
        ClassConverterSetup.setup()

        val adapters = Adapters.adapters

        adapters.registerConverter(TextConverter)
        adapters.registerConverter(BlockTypeConverter)
        adapters.registerConverter(ItemTypeConverter)
        adapters.registerConverter(EntityTypeConverter)
        adapters.registerConverter(LocationConverter)
        adapters.registerConverter(PlayerChatConverter)
        adapters.registerConverter(PlayerCommandConverter)
        adapters.registerConverter(PlayerInteractConverter)
        adapters.registerConverter(ServerCommandConverter)


        // Adapters

        adapters.register(::ServerAdapter)
        adapters.register(::LevelWorldAdapter)
        adapters.register(::PlayerAdapter)
        adapters.register(::BlockAdapter)
        adapters.register(ChunkFactory)
    }
}