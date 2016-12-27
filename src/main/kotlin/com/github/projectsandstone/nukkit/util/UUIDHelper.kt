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
package com.github.projectsandstone.nukkit.util

import cn.nukkit.nbt.tag.CompoundTag
import java.util.*

/**
 * UUID Helper
 *
 * This helper stores and fetches [UUID] from a [CompoundTag], Nukkit uses [Long] id and
 * Sandstone uses [UUID] id, to solve that incompatibility this class stores a generated UUID in [CompoundTag].
 *
 * Players have their UUID stored by Sandstone in [CompoundTag].
 */
object UUIDHelper {

    const val uuidTagName = "sandstone_uuid"

    fun getUniqueId(tag: CompoundTag, tagSaver: () -> Unit): UUID {
        if (tag.contains(uuidTagName)) {
            return UUID.fromString(tag.getString("uuid"))
        } else {
            val uuid = UUID.randomUUID()
            tag.putString(uuidTagName, uuid.toString())
            tagSaver()
            return uuid
        }

    }

    fun getRequiredUniqueId(tag: CompoundTag, owner: String): UUID {
        if (tag.contains(uuidTagName)) {
            return UUID.fromString(tag.getString(uuidTagName))
        } else {
            throw IllegalArgumentException("Cannot find '$uuidTagName' tag in compound '$tag' of '$owner'")
        }

    }
}