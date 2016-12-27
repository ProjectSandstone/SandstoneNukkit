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
package com.github.projectsandstone.nukkit.service

import cn.nukkit.plugin.service.ServicePriority
import com.github.projectsandstone.common.service.SandstoneServiceManager
import com.github.projectsandstone.nukkit.SandstoneNukkit
import com.github.projectsandstone.nukkit.util.alias.NukkitServer

object SandNukkitServiceManager : SandstoneServiceManager() {
    val nukkit = NukkitServer.getInstance()

    override fun <T : Any> internalSetProvider(service: Class<T>, instance: T) {
        this.nukkit.serviceManager.register(service, instance, SandstoneNukkit.getSandstonePluginInstance(), ServicePriority.NORMAL)
    }

    override fun <T : Any> internalProvide(service: Class<T>): T? {
        val reg = this.nukkit.serviceManager.getProvider(service)

        return reg?.provider
    }
}