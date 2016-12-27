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

import com.github.projectsandstone.api.Server
import com.github.projectsandstone.api.entity.Damageable
import com.github.projectsandstone.api.entity.Entity
import com.github.projectsandstone.api.entity.living.*
import com.github.projectsandstone.api.entity.living.animal.*
import com.github.projectsandstone.api.entity.living.monster.Creeper
import com.github.projectsandstone.api.entity.living.monster.Monster
import com.github.projectsandstone.api.entity.living.player.Player
import com.github.projectsandstone.api.entity.living.player.User
import com.github.projectsandstone.api.world.World
import com.github.projectsandstone.common.adapter.converters.ClassConverter
import com.github.projectsandstone.nukkit.util.alias.*

object ClassConverterSetup {

    fun setup() {
        ClassConverter.run {
            addClass(NukkitServer::class.java, Server::class.java)
            addClass(NukkitLevel::class.java, World::class.java)

            // Entity
            addClass(NukkitEntity::class.java, Entity::class.java)
            addClass(NukkitEntityDamageable::class.java, Damageable::class.java)
            addClass(NukkitEntityLiving::class.java, LivingEntity::class.java)

            // Animal
            addClass(NukkitEntityAnimal::class.java, Animal::class.java)
            addClass(NukkitEntityChicken::class.java, Chicken::class.java)
            addClass(NukkitEntityCow::class.java, Cow::class.java)
            //addClass(NukkitEntityHorse::class.java, Horse::class.java)
            //addClass(NukkitEntityMushroomCow::class.java, MushroomCow::class.java)
            addClass(NukkitEntityOcelot::class.java, Ocelot::class.java)
            addClass(NukkitEntityPig::class.java, Pig::class.java)
            //addClass(NukkitEntityPolarBear::class.java, PolarBear::class.java)
            addClass(NukkitEntityRabbit::class.java, Rabbit::class.java)
            addClass(NukkitEntitySheep::class.java, Sheep::class.java)
            addClass(NukkitEntityWolf::class.java, Wolf::class.java)

            // Monster
            addClass(NukkitEntityMob::class.java, Monster::class.java)
            //addClass(NukkitEntityBlaze::class.java, Blaze::class.java)
            //addClass(NukkitEntityCaveSpider::class.java, CaveSpider::class.java)
            addClass(NukkitEntityCreeper::class.java, Creeper::class.java)
            //addClass(NukkitEntityEnderman::class.java, Enderman::class.java)
            //addClass(NukkitEntityEndermite::class.java, Endermite::class.java)
            //addClass(NukkitEntityGhast::class.java, Ghast::class.java)
            //addClass(NukkitEntityGiant::class.java, Giant::class.java)
            //addClass(NukkitEntityGuardian::class.java, Guardian::class.java)
            //addClass(NukkitEntityMagmaCube::class.java, MagmaCube::class.java)
            //addClass(NukkitEntitySilverfish::class.java, Silverfish::class.java)
            //addClass(NukkitEntitySkeleton::class.java, Skeleton::class.java)
            //addClass(NukkitEntitySlime::class.java, Slime::class.java)
            //addClass(NukkitEntitySpider::class.java, Spider::class.java)
            //addClass(NukkitEntityWitch::class.java, Witch::class.java)
            //addClass(NukkitEntityWither::class.java, Wither::class.java)
            //addClass(NukkitEntityZombie::class.java, Zombie::class.java)
            //addClass(NukkitEntityPigZombie::class.java, ZombiePigman::class.java)

            // User
            addClass(NukkitOfflinePlayer::class.java, User::class.java)
            addClass(NukkitEntityHuman::class.java, Human::class.java)
            addClass(NukkitPlayer::class.java, Player::class.java)

            // Others
            //addClass(NukkitEntityAmbient::class.java, Ambient::class.java)
            addClass(NukkitEntityAgeable::class.java, Ageable::class.java)
            //addClass(NukkitEntityBat::class.java, Bat::class.java)
            addClass(NukkitEntityCreature::class.java, Creature::class.java)
            //addClass(NukkitEntitySquid::class.java, Squid::class.java)
            addClass(NukkitEntityVillager::class.java, Villager::class.java)
        }
    }
}