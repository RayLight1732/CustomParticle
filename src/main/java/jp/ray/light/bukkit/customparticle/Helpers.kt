package jp.ray.light.bukkit.customparticle

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.command.BlockCommandSender
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player
import java.lang.NumberFormatException

fun String.toIntOrNull():Int?{
    return try {
        toInt()
    }catch (e: NumberFormatException){
        null
    }
}

fun String.toDoubleOrNull():Double?{
    return try {
        Bukkit.getLogger().info(this)
        toDouble()
    }catch (e: NumberFormatException){
        null
    }
}

fun String.toFloatOrNull():Float?{
    return try {
        toFloat()
    }catch (e: NumberFormatException){
        null
    }
}

fun CommandSender.getLocation():Location?{
    if(this is Player){
        return location
    }else if (this is BlockCommandSender){
        return block.location
    }
    return null
}

fun Location.fromString(a1:String,a2:String,a3:String,yaw:String = "0",pitch:String = "0"):Location?{
    if (world ==null) return null
    if (a1.startsWith("~")){
        a1.replace(Regex("^~"),"")
        x += a1.toDoubleOrNull() ?: return null
    }else{
        x = a1.toDoubleOrNull() ?: return null
    }
    if (a2.startsWith("~")){
        a2.replace(Regex("^~"),"")
        y += a2.toDoubleOrNull() ?: return null
    }else{
        y = a2.toDoubleOrNull() ?: return null
    }
    if (a3.startsWith("~")){
        a3.replace(Regex("^~"),"")
        z += a3.toDoubleOrNull() ?: return null
    }else{
        z = a3.toDoubleOrNull() ?: return null
    }

    if (yaw.startsWith("~")){
        yaw.replace(Regex("^~"),"")
        this.yaw += yaw.toFloatOrNull() ?: return null
    }else{
        this.yaw = yaw.toFloatOrNull() ?: return null
    }
    if (pitch.startsWith("~")){
        pitch.replace(Regex("^~"),"")
        this.pitch += pitch.toFloatOrNull() ?: return null
    }else{
        this.pitch = pitch.toFloatOrNull() ?: return null
    }

    return this
}
