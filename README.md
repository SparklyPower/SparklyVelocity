<p align="center">
<h1 align="center">✨ SparklyVelocity ✨</h1>
</p>

SparklyPower's Velocity fork. This fork removes the default Minecraft listener and allows plugins to register binds manually. This way, you can have multiple listeners in your Velocity proxy, just like BungeeCord's multiple listeners!

```kotlin
    @Subscribe
    fun onProxyInitialization(event: ProxyInitializeEvent) {
        // Register our custom listeners
        // THIS REQUIRES SPARKLYVELOCITY!!!
        val proxyVersion = server.version
        if (proxyVersion.name == "SparklyVelocity") {
            val velocityServer = server as VelocityServer
            for (listener in config.listeners) {
                velocityServer.cm.bind(
                    listener.name,
                    AddressUtil.parseAndResolveAddress(listener.bind),
                    listener.proxyProtocol
                )
            }
        } else {
            logger.warn { "You aren't using SparklyVelocity! We aren't going to attempt to register another listeners then..." }
        }
    }
```

You can also check the listener that the connection is using! We use this on SparklyPower to differentiate Java connections from Geyser connections.

"Why not use Geyser's API???"

We like running Geyser standalone because it is useful to update Geyser without restarting the entire proxy and, even if we used Geyser on Velocity, it is impossible to know if it is a Geyser connection on `PreLoginEvent`.

```kotlin
    private fun isGeyser(connection: InboundConnection): Boolean {
        val minecraftConnection = if (connection is LoginInboundConnection) {
            connection.delegatedConnection()
        } else if (connection is VelocityInboundConnection) {
            connection.connection
        } else error("I don't know how to get a MinecraftConnection from a ${connection}!")

        val listenerName = minecraftConnection.listenerName
        m.logger.info { "${connection.remoteAddress} listener name: $listenerName" }

        // To detect and keep player IPs correctly, we use a separate Bungee listener that uses the PROXY protocol
        // To check if the user is connecting thru Geyser, we will check if the listener name matches what we would expect
        return listenerName == "geyser"
    }
```