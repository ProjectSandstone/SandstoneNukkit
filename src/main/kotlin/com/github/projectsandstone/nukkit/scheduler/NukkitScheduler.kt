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
package com.github.projectsandstone.nukkit.scheduler

import com.github.projectsandstone.api.scheduler.SubmittedTask
import com.github.projectsandstone.api.scheduler.Task
import com.github.projectsandstone.common.scheduler.SandstoneScheduler
import com.github.projectsandstone.common.util.MinecraftTicks
import com.github.projectsandstone.nukkit.util.alias.NukkitServer
import com.github.projectsandstone.nukkit.util.alias.NukkitTaskHandler
import java.time.Duration

object NukkitScheduler : SandstoneScheduler() {

    val nukkit = NukkitServer.getInstance()

    override fun submit(task: Task): SubmittedTask {
        val interval = task.interval
        val isRepeating = interval != Duration.ZERO

        val isAsync = task.isAsync
        val nukkitTask: NukkitTaskHandler

        if (isRepeating) {
            nukkitTask = nukkit.scheduler.scheduleDelayedRepeatingTask(
                    task.runnable,
                    task.delay.toMinecraftTicks(),
                    task.interval.toMinecraftTicks(),
                    isAsync)
        } else {
            nukkitTask = nukkit.scheduler.scheduleDelayedTask(
                    task.runnable,
                    task.delay.toMinecraftTicks(),
                    isAsync)
        }

        nukkit.tick

        return NukkitSubmittedTask(nukkit, task, nukkitTask)
    }

    internal fun Duration.toMinecraftTicks(): Int {
        return MinecraftTicks.milliToTicks(this.toMillis()).toInt()
    }

    @Deprecated(message = "Will be removed")
    internal class SandstoneNukkitRunnable(val runnable: Runnable) : Runnable {

        @Volatile var isRunning: Boolean = false

        override fun run() {
            this.isRunning = true
            this.runnable.run()
            this.isRunning = false
        }
    }
}