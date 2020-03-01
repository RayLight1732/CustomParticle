package jp.ray.light.bukkit.customparticle

import org.bukkit.plugin.java.JavaPlugin

class CustomParticle : JavaPlugin() {
    override fun onEnable() { // Plugin startup logic
        getCommand("customparticle").executor = ParticleCommand()
    }

    override fun onDisable() { // Plugin shutdown logic
    }
}