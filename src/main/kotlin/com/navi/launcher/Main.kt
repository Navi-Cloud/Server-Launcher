package com.navi.launcher

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import sun.misc.Signal
import sun.misc.SignalHandler
import java.io.File
import kotlin.system.exitProcess

/**
 * Supported Args: the root
 */
fun main(args: Array<String>) {

    val signalHandler = SignalHandler { signal ->
        ServerMainLauncher.destroy()
        runBlocking {
            delay(5000)
            if (!ServerMainLauncher.process.isAlive) {
                println("Process finished!")
            }
            exitProcess(0)
        }
    }

    Signal.handle(Signal("INT"), signalHandler)

    if (args.size != 1) {
        println("This Program needs only Root as argument!")
        println("i.e: java -jar testing.jar \"root_destination\"")
        return
    }
    val fileObject: File = File(args[0])
    if (!fileObject.exists() || !fileObject.isDirectory || !fileObject.isAbsolute) {
        println("Target ${fileObject.path} does not exists or not in ABSOLUTE path!")
        return
    }
    ServerMainLauncher.initServer(fileObject.absolutePath)
}