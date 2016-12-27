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
package com.github.projectsandstone.nukkit.adapter.entity

import com.github.jonathanxd.adapterhelper.Adapter
import com.github.projectsandstone.api.entity.Entity
import com.github.projectsandstone.api.world.Location
import com.github.projectsandstone.api.world.World
import com.github.projectsandstone.common.util.extension.adapt
import com.github.projectsandstone.common.util.extension.convert
import com.github.projectsandstone.nukkit.util.UUIDHelper
import com.github.projectsandstone.nukkit.util.alias.NukkitEntity
import com.github.projectsandstone.nukkit.util.alias.NukkitLocation
import java.util.*

interface EntityAdapter<out T: NukkitEntity> : Adapter<T>, Entity {

    override val location: Location<World>
        get() = this.adapterManager.adapt(this.adapteeInstance.location)

    override val uniqueId: UUID
        get() = UUIDHelper.getUniqueId(this.adapteeInstance.namedTag, this.adapteeInstance::saveNBT)

    override fun teleport(location: Location<*>) {
        this.adapteeInstance.teleport(this.adapterManager.convert<Location<*>, NukkitLocation>(location, this))
    }

}