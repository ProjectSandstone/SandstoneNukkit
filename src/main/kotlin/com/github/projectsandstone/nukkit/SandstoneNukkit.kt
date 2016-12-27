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

import cn.nukkit.plugin.PluginBase
import cn.nukkit.scheduler.TaskHandler
import com.github.projectsandstone.api.Sandstone
import com.github.projectsandstone.api.block.BlockTypes
import com.github.projectsandstone.api.constants.SandstonePlugin
import com.github.projectsandstone.api.entity.EntityTypes
import com.github.projectsandstone.api.event.SandstoneEventFactory
import com.github.projectsandstone.api.item.ItemTypes
import com.github.projectsandstone.common.SandstoneInit
import com.github.projectsandstone.common.adapter.Adapters
import com.github.projectsandstone.nukkit.logger.SandNukkitLogger
import com.github.projectsandstone.nukkit.logger.SandNukkitLoggerFactory
import java.nio.file.Files
import java.nio.file.Paths

class SandstoneNukkit : PluginBase() {

    private lateinit var task: TaskHandler
    private var serverInstanceAvailable: Boolean = false

    init {
        instance = this
        this.createDir()

        this.logger.info("Loading Sandstone adapters...")

        AdapterRegistry.registerAdapters()

        val loadedAdapters = Adapters.adapters.getAdapterSpecificationSet().size

        this.logger.info("$loadedAdapters adapters loaded!")

        val names = Adapters.adapters.getAdapterSpecificationSet().map { it.adapterClass.simpleName }.joinToString { it }

        this.logger.info("Adapters loaded: $names!")

        SandstoneInit.initConsts()
        SandstoneInit.initPath(Paths.get("./Sandstone/"))
        SandstoneInit.initGame(NukkitGame)
        SandstoneInit.initLogger(SandNukkitLogger(this.logger))
        SandstoneInit.initLoggerFactory(SandNukkitLoggerFactory)

        this.logger.info("Registering types...")
        NukkitRegistry.register(Sandstone.game)
        this.logger.info("Types registered!")

        this.logger.info("Initializing constants...")
        SandstoneInit.initRegistryConstants(Sandstone.game, EntityTypes::class.java, null)
        SandstoneInit.initRegistryConstants(Sandstone.game, ItemTypes::class.java, null)
        SandstoneInit.initRegistryConstants(Sandstone.game, BlockTypes::class.java, null)
        this.logger.info("Constants initialized!")

        SandstoneInit.loadPlugins(pluginsDir)
        SandstoneInit.startPlugins()
    }

    override fun onLoad() {
        this.preInitialize()
    }

    override fun onEnable() {
        this.initialize()

        task = this.server.scheduler.scheduleDelayedRepeatingTask({

            if (this.server.defaultLevel != null) {
                this.worldLoaded()

                this.serverStarting()
                this.serverInstanceAvailable = true
                this.serverStarted()

                task.cancel()
            }
        }, 1 * 20, 1 * 20, false)

        NukkitListeners.init(this)
    }

    override fun onDisable() {
        this.serverStopping()
        this.serverStopped()
    }

    fun isServerAvailable() = this.serverInstanceAvailable

    fun worldLoaded() {
        this.postInitialize()
    }

    fun serverStarting() {
        Sandstone.game.eventManager.dispatch(SandstoneEventFactory.createServerStartingEvent(), SandstonePlugin)
    }

    fun serverStarted() {
        Sandstone.game.eventManager.dispatch(SandstoneEventFactory.createServerStartedEvent(), SandstonePlugin)
    }

    fun preInitialize() {
        Sandstone.game.eventManager.dispatchAsync(SandstoneEventFactory.createPreInitializationEvent(), SandstonePlugin)
    }

    fun initialize() {
        Sandstone.game.eventManager.dispatch(SandstoneEventFactory.createInitializationEvent(), SandstonePlugin)
    }

    fun postInitialize() {
        Sandstone.game.eventManager.dispatch(SandstoneEventFactory.createPostInitializationEvent(), SandstonePlugin)
    }

    fun serverStopping() {
        Sandstone.game.eventManager.dispatch(SandstoneEventFactory.createServerStoppingEvent(), SandstonePlugin)
    }

    fun serverStopped() {
        Sandstone.game.eventManager.dispatch(SandstoneEventFactory.createServerStoppedEvent(), SandstonePlugin)
    }


    companion object {
        val pluginsDir = Paths.get(".", "Sandstone", "plugins")
        private lateinit var instance: SandstoneNukkit

        fun getSandstonePluginInstance() = instance
    }

    private fun createDir() {
        Files.createDirectories(pluginsDir)
    }
}