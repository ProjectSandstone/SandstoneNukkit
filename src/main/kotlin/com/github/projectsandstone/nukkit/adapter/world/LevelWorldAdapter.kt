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
package com.github.projectsandstone.nukkit.adapter.world

import cn.nukkit.math.Vector3
import cn.nukkit.nbt.tag.CompoundTag
import cn.nukkit.nbt.tag.DoubleTag
import cn.nukkit.nbt.tag.FloatTag
import cn.nukkit.nbt.tag.ListTag
import com.flowpowered.math.vector.Vector3i
import com.github.jonathanxd.adapterhelper.Adapter
import com.github.jonathanxd.adapterhelper.AdapterManager
import com.github.projectsandstone.api.block.BlockState
import com.github.projectsandstone.api.entity.Entity
import com.github.projectsandstone.api.entity.EntityType
import com.github.projectsandstone.api.event.Source
import com.github.projectsandstone.api.text.Text
import com.github.projectsandstone.api.text.channel.MessageReceiver
import com.github.projectsandstone.api.world.Chunk
import com.github.projectsandstone.api.world.Location
import com.github.projectsandstone.api.world.World
import com.github.projectsandstone.common.util.extension.adapt
import com.github.projectsandstone.common.util.extension.adaptAll
import com.github.projectsandstone.common.util.extension.convert
import com.github.projectsandstone.nukkit.util.alias.NukkitEntity
import com.github.projectsandstone.nukkit.util.alias.NukkitLevel
import com.github.projectsandstone.nukkit.util.alias.NukkitLocation
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*

class LevelWorldAdapter(override val adapteeInstance: NukkitLevel, override val adapterManager: AdapterManager) :
        Adapter<NukkitLevel>, World {

    override val name: String = this.adapteeInstance.name

    override val directory: Path = Paths.get(this.adapteeInstance.folderName)

    override val entities: Collection<Entity>
        get() = this.adapterManager.adaptAll(this.adapteeInstance.entities.toList())

    override val loadedChunks: List<Chunk>
        get() = this.adapterManager.adaptAll(this.adapteeInstance.chunks.values.toList())

    override val uniqueId: UUID
        get() = TODO("Not supported yet")

    override fun spawnEntity(type: EntityType, location: Location<*>): Entity {
        val nukkitLocation = this.adapterManager.adapt<Location<*>, NukkitLocation>(location)

        val chunk = this.adapteeInstance.getChunk(nukkitLocation.floorX shr 4, nukkitLocation.floorZ shr 4)

        val nbt = CompoundTag()
                .putList(ListTag<DoubleTag>("Pos")
                        .add(DoubleTag("", nukkitLocation.x))
                        .add(DoubleTag("", nukkitLocation.y))
                        .add(DoubleTag("", nukkitLocation.z)))
                .putList(ListTag<DoubleTag>("Motion")
                        .add(DoubleTag("", 0.0))
                        .add(DoubleTag("", 0.0))
                        .add(DoubleTag("", 0.0)))
                .putList(ListTag<FloatTag>("Rotation")
                        .add(FloatTag("", 0F))
                        .add(FloatTag("", 0F)))
                .putShort("Health", 20)

        val entity = NukkitEntity.createEntity(type.name, chunk, nbt)

        this.adapteeInstance.addEntity(entity)

        return this.adapterManager.adapt(entity)
    }

    override fun getBlock(location: Vector3i): BlockState {
        val nukkitBlock = this.adapteeInstance.getBlock(Vector3(location.x.toDouble(), location.y.toDouble(), location.z.toDouble()))

        return this.adapterManager.convert(nukkitBlock, this)
    }

    override fun setBlock(location: Vector3i, blockState: BlockState, source: Source?) {
        val pos = Vector3(location.x.toDouble(), location.y.toDouble(), location.z.toDouble())

        this.adapteeInstance.setBlock(pos, this.adapterManager.convert(blockState, this))
    }

    override fun sendMessage(text: Text) {
        this.entities.forEach {
            if (it is MessageReceiver) {
                it.sendMessage(text)
            }
        }
    }
}