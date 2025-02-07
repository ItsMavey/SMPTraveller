# SMPTraveller

*All right reserved - Not security tested, use at your own risk*

<img src="logo.png" width="500" height="auto">

This plugin as been develop for the Nexus SMP of [ItsMavey](https://twitch.tv/itsmavey) on Twitch.

This plugin is a simple plugin that allow the player to travel at some location in different world.

All rights reserved to [ItsMavey](https://twitch.tv/itsmavey)

## Compatibility

This plugin as been developed and tested for Paper 1.21.1, but it should work on any version of Paper.

## Installation

To install the plugin, you need to download the jar file in the release section of the repository.

Then, you need to put the jar file in the plugin folder of your server.

Finally, you need to restart the server to load the plugin.

(An Error might appear in the console at the first start of the server, but it's normal)

## Commands 

### Default Permissions

#### - /traveller

> /traveller travel <location>: Allow the player to travel at the location in the world. 
> 
> /traveller list: List all the location that the player can travel.
> 
> /traveller spawn: Teleport the player at the spawn of the world. (need to be set)
> 
> /traveller end: Teleport the player to the End Portal or the End Spawn. (need to be set)
> 
> /traveller shop: Teleport the player to the shop. (need to be set)
> 

#### /spawn

> 
> /spawn: Teleport the player at the spawn of the world. (need to be set)


#### /end

> /end: Teleport the player to the End Portal or the End Spawn. (need to be set)


#### /shop

> /shop: Teleport the player to the shop. (need to be set)


### Admin Permissions

#### - /traveller

> /traveller set (location) [x , y, z] [yaw, pitch] [world] : Add a location to the list of location that the player can travel.
>
> /traveller remove (location): Remove a location from the list of location that the player can travel.
>
> /traveller delete (location): Delete a location from the list of location that the player can travel.
