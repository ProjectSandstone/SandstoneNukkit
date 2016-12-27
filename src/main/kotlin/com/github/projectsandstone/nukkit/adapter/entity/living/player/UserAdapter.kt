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
package com.github.projectsandstone.nukkit.adapter.entity.living.player

import cn.nukkit.IPlayer
import com.github.jonathanxd.adapterhelper.Adapter
import com.github.projectsandstone.api.entity.living.player.Player
import com.github.projectsandstone.api.entity.living.player.User
import com.github.projectsandstone.common.util.extension.adapt
import com.github.projectsandstone.nukkit.util.UUIDHelper
import com.github.projectsandstone.nukkit.util.alias.NukkitServer
import java.util.*

interface UserAdapter<out T : IPlayer> : Adapter<T>, User {

    override val uniqueId: UUID
        get() = UUIDHelper.getRequiredUniqueId(NukkitServer.getInstance().getOfflinePlayerData(this.adapteeInstance.name), this.adapteeInstance.name)

    override val isOnline: Boolean
        get() = this.adapteeInstance.isOnline

    override val name: String
        get() = this.adapteeInstance.name

    override val player: Player?
        get() {
            return this.adapteeInstance.player?.let { this.adapterManager.adapt(it) }
        }
}