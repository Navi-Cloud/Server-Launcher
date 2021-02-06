package com.navi.launcher

import kotlinx.coroutines.runBlocking
import java.io.*
import java.net.URL
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

object ServerMainLauncher {
    // Server INIT related
    lateinit var process: Process
    lateinit var targetServerRoot: File
    val targetServerDirectory: File = File(System.getProperty("java.io.tmpdir"), "testServer.jar")
    val targetServerSettings: File = File(System.getProperty("java.io.tmpdir"), "application-test.properties")


    fun initServer(rootDirectory: String) {
        targetServerRoot = File(rootDirectory)
        val serverURL: String =
            "https://github.com/Navi-Cloud/Navi-Server/releases/download/V0.5.0-Alpha/NavIServer-1.0-SNAPSHOT.jar"

        // Create Server Directory
        targetServerRoot.mkdir()

        // Download server to targetServerDirectory
        if (!targetServerDirectory.exists()) {
            println("Downloading files...")
            URL(serverURL).openStream().use { `in` ->
                val serverPath: Path = Paths.get(targetServerDirectory.absolutePath)
                Files.copy(`in`, serverPath)
            }
            println("Download Finished!")
        } else {
            println("Using previously-downloaded server")
        }

        // Set Property file
        val tmpString: String = convertString(targetServerRoot.absolutePath)
        val propertyString: String = """
         spring.jpa.show-sql=true
         spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5InnoDBDialect
         spring.h2.console.enabled=true
         navi.server-root=${tmpString}
     """.trimIndent()
        if (targetServerSettings.exists()) targetServerSettings.delete()
        val writer: BufferedWriter = BufferedWriter(FileWriter(targetServerSettings))
        writer.write(propertyString)
        writer.close()

        val command: Array<String> = arrayOf(
            "java",
            "-jar",
            targetServerDirectory.absolutePath,
            "--spring.config.location=${targetServerSettings.absolutePath}"
        )
        process = Runtime.getRuntime().exec(command)

        val stdInput: BufferedReader = BufferedReader(InputStreamReader(process.inputStream))
        val stdError: BufferedReader = BufferedReader(InputStreamReader(process.errorStream))

        println("Server Output...")
        var outputString: String? = null
        while (true) {
            outputString = stdInput.readLine()
            if (outputString == null) {
                break
            }

            println(outputString)
        }
    }

    fun convertString(targetString: String): String {
        val osType: String = System.getProperty("os.name").toLowerCase()
        var tmpString: String = ""
        return if (osType.contains("win")) {
            for (i in targetString.indices) {
                if (targetString[i] == '\\') {
                    tmpString += "\\"
                }
                tmpString += targetString[i]
            }
            tmpString
        } else {
            targetString
        }
    }

    fun destroy() = runBlocking {
        process.destroy()
        if (process.isAlive) {
            println("Process still alive..!")
            process.waitFor()
        }
    }
}