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
package com.github.projectsandstone.nukkit.converter

import com.github.jonathanxd.adapterhelper.Adapter
import com.github.jonathanxd.adapterhelper.AdapterManager
import com.github.jonathanxd.adapterhelper.Converter
import com.github.projectsandstone.api.text.Text
import com.github.projectsandstone.api.text.style.TextColor
import com.github.projectsandstone.api.text.style.TextColors
import com.github.projectsandstone.api.text.style.TextFormat
import com.github.projectsandstone.api.text.style.TextFormats
import cn.nukkit.utils.TextFormat as NukkitTextFormat

object TextConverter : Converter<Text, String> {

    override fun convert(input: Text, adapter: Adapter<*>?, manager: AdapterManager): String {
        val builder = StringBuilder()

        this.convertAll(input, builder)

        return builder.toString()
    }

    @JvmStatic
    fun convertAll(text: Text, builder: StringBuilder) {
        this.convertTextIndividual(text, builder)
        val parent = text.parent

        if (parent != null && parent.isNotEmpty()) {
            parent.forEach {
                this.convertAll(it, builder)
            }
        }
    }

    @JvmStatic
    fun convertTextIndividual(text: Text, builder: StringBuilder) {
        builder.append(this.convertColor(text.color))
        builder.append(this.convertFormat(text.format))
        builder.append(text.content)
    }

    @JvmStatic
    fun convertColor(textColor: TextColor): String {
        return NukkitTextFormat.valueOf(textColor.name.toUpperCase()).toString()
    }

    @JvmStatic
    fun convertFormat(textFormat: TextFormat): String {
        val builder = StringBuilder()

        if (textFormat.bold)
            builder.append(NukkitTextFormat.BOLD)

        if (textFormat.italic)
            builder.append(NukkitTextFormat.ITALIC)

        if (textFormat.obfuscated)
            builder.append(NukkitTextFormat.OBFUSCATED)

        if (textFormat.strikeThrough)
            builder.append(NukkitTextFormat.STRIKETHROUGH)

        if (textFormat.underline)
            builder.append(NukkitTextFormat.UNDERLINE)

        if (textFormat == TextFormats.NORMAL)
            builder.append(NukkitTextFormat.RESET)

        return builder.toString()
    }

    override fun revert(): Converter<String, Text>? = Revert

    object Revert : Converter<String, Text> {
        override fun convert(input: String, adapter: Adapter<*>?, manager: AdapterManager): Text {
            val strBuilder = StringBuilder()
            val args = mutableListOf<Any>()

            fun build() {
                if (strBuilder.isNotEmpty()) {
                    args.add(strBuilder.toString())
                    strBuilder.setLength(0)
                }
            }

            var index = -1

            val chars = input.toCharArray()
            val iter = chars.iterator()

            while (iter.hasNext()) {
                val c = iter.nextChar()
                ++index

                if (c == NukkitTextFormat.ESCAPE && index + 1 < chars.size) {
                    val next = chars[index + 1]

                    if (this.isCode(next)) {
                        build()
                        args.add(this.convertCode(next))
                        iter.nextChar() // Skip next
                    } else {
                        strBuilder.append(c)
                    }
                } else {
                    strBuilder.append(c)
                }

                if (!iter.hasNext()) {
                    build()
                }
            }

            return Text.of(*args.toTypedArray())
        }

        override fun revert(): Converter<Text, String>? = TextConverter

        fun isCode(input: Char) = this.isFormatCode(input) || this.isColorCode(input)

        fun isFormatCode(input: Char) = when (input) {
            in 'k'..'o', 'r' -> true
            else -> false
        }


        fun isColorCode(input: Char) = when (input) {
            in '0'..'9', in 'a'..'f' -> true
            else -> false
        }

        fun convertCode(input: Char): Any {
            if (this.isFormatCode(input))
                return this.convertFormat(input)

            if (this.isColorCode(input))
                return this.convertColor(input)

            throw IllegalArgumentException("$input is not a valid code!")
        }

        fun convertColor(input: Char): TextColor = when (input) {
            '0' -> TextColors.BLACK
            '1' -> TextColors.DARK_BLUE
            '2' -> TextColors.DARK_GREEN
            '3' -> TextColors.DARK_AQUA
            '4' -> TextColors.DARK_RED
            '5' -> TextColors.DARK_PURPLE
            '6' -> TextColors.GOLD
            '7' -> TextColors.GRAY
            '8' -> TextColors.DARK_GRAY
            '9' -> TextColors.BLUE
            'a' -> TextColors.GREEN
            'b' -> TextColors.AQUA
            'c' -> TextColors.RED
            'd' -> TextColors.LIGHT_PURPLE
            'e' -> TextColors.YELLOW
            'f' -> TextColors.WHITE
            else -> throw IllegalArgumentException("$input is not a valid color code!")
        }

        fun convertFormat(input: Char): TextFormat = when (input) {
            'k' -> TextFormats.OBFUSCATED
            'l' -> TextFormats.BOLD
            'm' -> TextFormats.STRIKE_THROUGH
            'n' -> TextFormats.UNDERLINE
            'o' -> TextFormats.ITALIC
            'r' -> TextFormats.NORMAL
            else -> throw IllegalArgumentException("$input is not a valid format code!")
        }

    }
}