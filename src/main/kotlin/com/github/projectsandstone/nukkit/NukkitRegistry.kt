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

import com.github.projectsandstone.api.Game
import com.github.projectsandstone.api.Sandstone
import com.github.projectsandstone.api.block.BlockState
import com.github.projectsandstone.api.block.BlockType
import com.github.projectsandstone.api.entity.Entity
import com.github.projectsandstone.api.entity.EntityType
import com.github.projectsandstone.api.item.ItemType
import com.github.projectsandstone.common.adapter.converters.ClassConverter
import com.github.projectsandstone.common.util.extension.formatToSandstoneRegistryId
import com.github.projectsandstone.common.util.extension.formatToSandstoneRegistryName
import com.github.projectsandstone.nukkit.block.DefaultBlockState
import com.github.projectsandstone.nukkit.util.alias.NukkitEntity
import com.github.projectsandstone.nukkit.util.alias.NukkitItem
import java.lang.reflect.Modifier

object NukkitRegistry {

    fun register(game: Game) {

        val knownEntitiesField = NukkitEntity::class.java.getDeclaredField("knownEntities")

        knownEntitiesField.isAccessible = true

        @Suppress("UNCHECKED_CAST")
        val knownEntities = knownEntitiesField.get(null) as Map<String, Class<out NukkitEntity>>

        knownEntities
                .values
                .filter { it.declaringClass == null }
                .forEach {
                    if (ClassConverter.hasSandstoneEquivalent(it)) {
                        val type = NukkitEntityType(it)
                        game.registry[type.id, EntityType::class.java] = type
                    } else {
                        Sandstone.logger.warn("No equivalent entity type for nukkit class: '$it'");
                    }
                }

        NukkitItem::class.java.declaredFields
                .filter {
                    it.type == Integer.TYPE
                            && Modifier.isPublic(it.modifiers)
                            && Modifier.isStatic(it.modifiers)
                            && Modifier.isFinal(it.modifiers)
                }
                .map {
                    NukkitMaterial(it.getInt(null), it.name)
                }
                .forEach {
                    val type = NukkitItemType(it, game)
                    game.registry[type.id, ItemType::class.java] = type

                    if (it.isBlock) {
                        val blockType = NukkitBlockType(it, game)

                        game.registry[blockType.id, BlockType::class.java] = blockType
                    }
                }
    }

    private data class NukkitMaterial(val id: Int, val name: String) {
        val maxStackSize = NukkitItem.get(id).maxStackSize
        val isBlock = NukkitItem.get(id).block != null
    }

    private class NukkitEntityType(nukkitEntityClass: Class<out NukkitEntity>) : EntityType {
        override val id: String = nukkitEntityClass.simpleName.formatToSandstoneRegistryId()
        override val name: String = nukkitEntityClass.simpleName.formatToSandstoneRegistryName()
        @Suppress("UNCHECKED_CAST")
        override val entityClass: Class<out Entity> = ClassConverter.getSandstoneEquivalent(nukkitEntityClass) as Class<out Entity>
    }

    private class NukkitItemType(material: NukkitMaterial, game: Game) : ItemType {
        override val id: String = material.formatToSandstoneRegistryId()
        override val name: String = material.formatToSandstoneRegistryName()
        override val block: BlockType? = if (material.isBlock) game.registry[id, BlockType::class.java] else null
        override val maxStack: Int = material.maxStackSize

    }

    private class NukkitBlockType(material: NukkitMaterial, game: Game) : BlockType {
        override val id: String = material.formatToSandstoneRegistryId()
        override val name: String = material.formatToSandstoneRegistryName()
        override val item: ItemType? = game.registry[id, ItemType::class.java]
        override val defaultState: BlockState = DefaultBlockState(this)
        override var tickRandomly: Boolean = false
    }


    /**
     * Format [NukkitMaterial] to Sandstone Registry Format.
     *
     * Example: ENDER_PEARL will be formatted as ender_pearl
     */
    private fun NukkitMaterial.formatToSandstoneRegistryId(): String = this.name.toLowerCase()

    /**
     * Format [NukkitMaterial] to Sandstone Registry Name.
     *
     * Example: ENDER_PEARL will be formatted as Ender Pearl
     */
    private fun NukkitMaterial.formatToSandstoneRegistryName(): String {
        val sb = StringBuilder()
        val chars = this.name.toCharArray()

        chars.forEachIndexed { i, c ->

            if (i == 0 || i - 1 > 0 && chars[i - 1] == '_')
                sb.append(c.toUpperCase())
            else if (c == '_')
                sb.append(' ')
            else
                sb.append(c.toLowerCase())

        }

        return sb.toString()
    }
}