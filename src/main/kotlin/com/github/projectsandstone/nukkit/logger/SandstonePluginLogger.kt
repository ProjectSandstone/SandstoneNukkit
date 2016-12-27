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
package com.github.projectsandstone.nukkit.logger

import cn.nukkit.utils.LogLevel
import com.github.projectsandstone.api.plugin.PluginContainer
import com.github.projectsandstone.nukkit.SandstoneNukkit
import com.github.projectsandstone.nukkit.util.alias.NukkitLogger

class SandstonePluginLogger(pluginContainer: PluginContainer) : NukkitLogger {

    val parent = SandstoneNukkit.getSandstonePluginInstance().logger
    val loggerName = "[${pluginContainer.name}]"

    override fun log(level: LogLevel?, message: String?) {
        if(level == null || message == null)
            return

        @Suppress("NAME_SHADOWING")
        val message = "$loggerName $message"

        this.parent.log(level, message)
    }

    override fun log(level: LogLevel?, message: String?, t: Throwable?) {

        if(level == null || message == null || t == null)
            return

        @Suppress("NAME_SHADOWING")
        val message = "$loggerName $message"

        this.parent.log(level, message, t)
    }

    override fun emergency(message: String?) {
        this.log(LogLevel.EMERGENCY, message)
    }

    override fun emergency(message: String?, t: Throwable?) {
        this.log(LogLevel.EMERGENCY, message)
    }

    override fun critical(message: String?) {
        this.log(LogLevel.CRITICAL, message)
    }

    override fun critical(message: String?, t: Throwable?) {
        this.log(LogLevel.CRITICAL, message, t)
    }

    override fun alert(message: String?) {
        this.log(LogLevel.ALERT, message)
    }

    override fun alert(message: String?, t: Throwable?) {
        this.log(LogLevel.ALERT, message, t)
    }

    override fun notice(message: String?) {
        this.log(LogLevel.NOTICE, message)
    }

    override fun notice(message: String?, t: Throwable?) {
        this.log(LogLevel.NOTICE, message, t)
    }

    override fun warning(message: String?) {
        this.log(LogLevel.WARNING, message)
    }

    override fun warning(message: String?, t: Throwable?) {
        this.log(LogLevel.WARNING, message, t)
    }

    override fun info(message: String?) {
        this.log(LogLevel.INFO, message)
    }

    override fun info(message: String?, t: Throwable?) {
        this.log(LogLevel.INFO, message, t)
    }

    override fun error(message: String?) {
        this.log(LogLevel.ERROR, message)
    }

    override fun error(message: String?, t: Throwable?) {
        this.log(LogLevel.ERROR, message, t)
    }

    override fun debug(message: String?) {
        this.log(LogLevel.DEBUG, message)
    }

    override fun debug(message: String?, t: Throwable?) {
        this.log(LogLevel.DEBUG, message, t)
    }
}