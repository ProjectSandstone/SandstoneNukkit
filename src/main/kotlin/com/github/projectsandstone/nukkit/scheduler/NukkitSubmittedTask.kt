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
import com.github.projectsandstone.nukkit.util.alias.NukkitServer
import com.github.projectsandstone.nukkit.util.alias.NukkitTaskHandler

class NukkitSubmittedTask(val nukkit: NukkitServer, override val task: Task, val nukkitTaskHandler: NukkitTaskHandler) : SubmittedTask {

    internal var isCancelled: Boolean = false

    override fun cancel() {
        if (isCancelled)
            throw IllegalStateException("Cancelled task")

        isCancelled = true
        nukkitTaskHandler.cancel()
    }

    override fun isRunning(): Boolean {

        if (isCancelled)
            return false

        val lastTick = nukkitTaskHandler.lastRunTick

        return lastTick == this.nukkit.tick
    }

    override fun isSubmitted(): Boolean {
        if (isCancelled)
            return false

        return this.nukkit.scheduler.isQueued(nukkitTaskHandler.taskId)
    }

    override fun waitFinish() {
        if (isCancelled || !this.isSubmitted())
            return

        while (this.isSubmitted() || this.isRunning())
            Thread.sleep(1000)
    }
}