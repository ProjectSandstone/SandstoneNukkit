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

import com.github.projectsandstone.api.logging.LogLevel
import com.github.projectsandstone.api.logging.Logger
import com.github.projectsandstone.nukkit.util.alias.NukkitLogger

class SandNukkitLogger(val logger: NukkitLogger) : Logger {

    override fun log(level: LogLevel, exception: Exception) {
        exception.printStackTrace {
            logger.log(level.toNukkit(), it)
        }
    }

    override fun log(level: LogLevel, exception: Exception, message: String) {
        exception.printStackTrace(message) {
            logger.log(level.toNukkit(), it)
        }
    }

    override fun log(level: LogLevel, exception: Exception, format: String, vararg objects: Any) {
        exception.printStackTrace(String.format(format, *objects)) {
            logger.log(level.toNukkit(), it)
        }
    }

    override fun log(level: LogLevel, message: String) {
        logger.log(level.toNukkit(), message)
    }

    override fun log(level: LogLevel, format: String, vararg objects: Any) {
        logger.log(level.toNukkit(), String.format(format, *objects))
    }

}