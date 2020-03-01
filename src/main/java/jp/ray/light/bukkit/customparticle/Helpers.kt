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
        if (this == ""){
            0
        }else {
            toInt()
        }
    }catch (e: NumberFormatException){
        null
    }
}

fun String.toDoubleOrNull():Double?{
    return try {
        if (this == ""){
            0.0
        }else {
            toDouble()
        }
    }catch (e: NumberFormatException){
        null
    }
}

fun String.toFloatOrNull():Float?{
    return try {
        if (this == ""){
            0f
        }else {
            toFloat()
        }
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
    val regex = Regex("^~")
    if (a1.startsWith("~")){
        x += a1.replace(regex,"").toDoubleOrNull() ?: return null
    }else{
        x = a1.toDoubleOrNull() ?: return null
    }
    if (a2.startsWith("~")){
        y += a2.replace(regex,"").toDoubleOrNull() ?: return null
    }else{
        y = a2.toDoubleOrNull() ?: return null
    }
    if (a3.startsWith("~")){
        z += a3.replace(regex,"").toDoubleOrNull() ?: return null
    }else{
        z = a3.toDoubleOrNull() ?: return null
    }

    if (yaw.startsWith("~")){
        this.yaw += yaw.replace(regex,"").toFloatOrNull() ?: return null
    }else{
        this.yaw = yaw.toFloatOrNull() ?: return null
    }
    if (pitch.startsWith("~")){
        this.pitch += pitch.replace(regex,"").toFloatOrNull() ?: return null
    }else{
        this.pitch = pitch.toFloatOrNull() ?: return null
    }

    return this
}
