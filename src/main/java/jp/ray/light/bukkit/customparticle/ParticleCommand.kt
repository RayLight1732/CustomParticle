package jp.ray.light.bukkit.customparticle

import jp.momonnga.spigot.module.particle.ParticleModule
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.block.Block
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import java.lang.NumberFormatException

class ParticleCommand : CommandExecutor ,TabCompleter{
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        //cp circle particle 半径 (x y z yaw pitch) (点の数)
        //cp polygon particle 半径 頂点の数 (x y z yaw pitch)
        //cp line particle location1 location2 (space)
        when (args[0]){
            "circle" -> {
                return circle(sender,args)
            }
            "line" -> {
                return line(sender,args)
            }
            "polygon" -> {
                return polygon(sender,args)
            }
            else -> {
                sender.sendMessage("${ChatColor.RED}使用法:/customparticle[cp] <circle|line|polygon> ...")
            }
        }
        return true
    }

    private fun circle(sender: CommandSender,args: Array<String>):Boolean{
        val message = fun() {
            sender.sendMessage("${ChatColor.RED}使用法:/customparticle[cp] circle <名前> <半径> [x] [y] [z] [yaw] [pitch] [点の数]")
        }
        if (args.size >=3){
            if (args.size >=4){
                if (args.size >=9){
                    ParticleModule.draw3dCircle(
                            toParticle(args[1]) ?: kotlin.run{
                                sender.sendMessage("${ChatColor.RED}そのようなパーティクルは存在しません。")
                                return false
                            }
                            ,sender.getLocation()?.fromString(args[3],args[4],args[5],args[6],args[7]) ?: kotlin.run{
                        sender.sendMessage("正確な値を入力してください。")
                        return false
                    }
                            ,args[2].toDoubleOrNull() ?: kotlin.run {
                        message()
                        return false
                    }
                            ,args[8].toIntOrNull() ?: kotlin.run {
                        message()
                        return false
                    })
                    return true
                }else{
                    if (args.size<8){
                        sender.sendMessage("${ChatColor.RED}五点の入力が必要です")
                        return false
                    }else{
                        ParticleModule.draw3dCircle(
                                toParticle(args[1]) ?: kotlin.run{
                                    sender.sendMessage("${ChatColor.RED}そのようなパーティクルは存在しません。")
                                    return false
                                }
                        ,sender.getLocation()?.fromString(args[3],args[4],args[5],args[6],args[7]) ?: kotlin.run{
                            sender.sendMessage("正確な値を入力してください。")
                            return false
                        }
                        ,args[2].toDoubleOrNull() ?: kotlin.run {
                            message()
                            return false
                        }
                        ,16)
                        return true
                    }
                }
            }else{
                ParticleModule.draw3dCircle(
                        toParticle(args[1]) ?: kotlin.run {
                            sender.sendMessage("${ChatColor.RED}そのようなパーティクルは存在しません。")
                            return false
                        }
                        ,sender.getLocation(),args[2].toDoubleOrNull() ?: kotlin.run {
                    message()
                    return false
                },16)
                return true
            }
        }else {
            message()
            return false
        }
    }

    private fun polygon(sender: CommandSender,args: Array<String>):Boolean{
        val message = fun() {
            sender.sendMessage("${ChatColor.RED}使用法:/customparticle[cp] polygon <名前> <半径> <頂点の数> [x] [y] [z] [yaw] [pitch]")
        }
        when (args.size){
            4 -> {
                ParticleModule.drawPolygon(toParticle(args[1]) ?: kotlin.run {
                    message()
                    return false
                },sender.getLocation(), args[2].toDoubleOrNull() ?: kotlin.run {
                    message()
                    return false
                }, args[3].toIntOrNull() ?: kotlin.run {
                    message()
                    return false
                },0.2)
                return true
            }

            in 5..8 -> {
                sender.sendMessage("${ChatColor.RED}五点の入力が必要です。")
                return true
            }

            9 -> {
                ParticleModule.drawPolygon(
                        toParticle(args[1]) ?: kotlin.run{
                            sender.sendMessage("${ChatColor.RED}そのようなパーティクルは存在しません。")
                            return false
                        }
                        ,sender.getLocation()?.fromString(args[3],args[4],args[5],args[6],args[7]) ?: kotlin.run{
                    sender.sendMessage("正確な値を入力してください。")
                    return false
                }
                        ,args[2].toDoubleOrNull() ?: kotlin.run {
                    message()
                    return false
                }
                        ,args[8].toIntOrNull() ?: kotlin.run {
                    message()
                    return false
                }, 0.2)
                return true
            }
            else -> {
                message()
                return false
            }
        }
    }

    private fun line(sender: CommandSender,args: Array<String>):Boolean{
        val message = fun(){
            sender.sendMessage("${ChatColor.RED}使用法:/customparticle[cp] line <名前> <x1> <y1> <z1> <x2> <y2> <z2> [間隔]")
        }
        if (args.size>=8){
            if (args.size >=9){
                ParticleModule.drawLine(toParticle(args[1]),sender.getLocation()?.fromString(args[2],args[3],args[4]) ?: kotlin.run {
                    sender.sendMessage("正確な値を入力してください。")
                    return false
                },sender.getLocation()?.fromString(args[5],args[6],args[7]) ?: kotlin.run{
                    message()
                    return false
                },args[8].toDoubleOrNull() ?: kotlin.run{
                    message()
                    return false
                })
                return true
            }else{
                ParticleModule.drawLine(toParticle(args[1]),sender.getLocation()?.fromString(args[2],args[3],args[4]) ?: kotlin.run {
                    message()
                    return false
                },sender.getLocation()?.fromString(args[5],args[6],args[7]) ?: kotlin.run{
                    message()
                    return false
                },0.2)
                return true
            }
        }else{
            message()
            return false
        }
    }

    private fun toParticle(name:String):Particle?{
        return try {
            Particle.valueOf(name.toUpperCase())
        }catch (e:InternalError){
            null
        }
    }

    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<String>): List<String> {
        val list = mutableListOf<String>()
        when (args.size){

            1 -> {
                if ("circle".startsWith(args[0].toLowerCase())) list.add("circle")
                if ("polygon".startsWith(args[0].toLowerCase())) list.add("polygon")
                if ("line".startsWith(args[0].toLowerCase()))  list.add("line")
            }
            2 -> {
                Particle.values().forEach {
                    if (it.name.startsWith(args[1].toUpperCase())){
                        list.add(it.name)
                    }
                }
            }
            else -> {
                if (sender is Player) {
                    when (args[0].toLowerCase()) {
                        "circle" -> {
                            when (args.size) {
                                4 -> {
                                    list.add(getTargetX(sender)?.toString() ?: return emptyList())
                                }
                                5 -> {
                                    list.add(getTargetY(sender)?.toString() ?: return emptyList())
                                }
                                6 -> {
                                    list.add(getTargetZ(sender)?.toString() ?: return emptyList())
                                }
                            }
                        }
                        "polygon" -> {
                            when (args.size) {
                                5 -> {
                                    list.add(getTargetX(sender)?.toString() ?: return emptyList())
                                }
                                6 -> {
                                    list.add(getTargetY(sender)?.toString() ?: return emptyList())
                                }
                                7 -> {
                                    list.add(getTargetZ(sender)?.toString() ?: return emptyList())
                                }
                            }
                        }
                        "line" -> {
                            when (args.size) {
                                3,6 -> {
                                    list.add(getTargetX(sender)?.toString() ?: return emptyList())
                                }
                                4,7 -> {
                                    list.add(getTargetY(sender)?.toString() ?: return emptyList())
                                }
                                5,8 -> {
                                    list.add(getTargetZ(sender)?.toString() ?: return emptyList())
                                }
                            }
                        }
                    }
                }
            }
        }
        return list
    }

    private fun getTargetBlockLocation(player: Player):Location?{
        val target:Block = player.getTargetBlock(null,5)
        return if (target.isEmpty){
            null
        }else{
            target.location
        }
    }

    private fun getTargetX(player: Player):Int?{
        return getTargetBlockLocation(player)?.blockX
    }
    private fun getTargetY(player: Player):Int?{
        return getTargetBlockLocation(player)?.blockY
    }
    private fun getTargetZ(player: Player):Int?{
        return getTargetBlockLocation(player)?.blockZ
    }
}