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
package com.github.projectsandstone.nukkit.converter

import cn.nukkit.block.BlockUnknown
import com.github.projectsandstone.api.Sandstone
import com.github.projectsandstone.api.registry.RegistryEntry
import com.github.projectsandstone.nukkit.util.NukkitLookups

object RegistryEntryConverterHelper {

    inline fun <reified O : Any> convert(input: RegistryEntry, resolver: (Int) -> O): O {

        return resolver(NukkitLookups.lookup(O::class.java, input.name.toUpperCase())).let {
            if (it is BlockUnknown) {
                throw IllegalArgumentException("Cannot not convert from '$input' (name: ${input.name}) to Nukkit")
            } else it
        }
    }

    inline fun <T : Any, reified O : RegistryEntry> resolve(instance: T, nameResolver: (T) -> String): O {
        val id = nameResolver(instance).toLowerCase()

        return Sandstone.game.registry.getEntry(id, O::class.java)
                ?: throw IllegalArgumentException("Cannot find Entry '$id' (Instance: $instance) in registry.")
    }

}