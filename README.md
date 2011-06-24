NuxKits
=======

NuxKits is a plugin that dispenses kits to players.

Installation
------------

* First, you need the PermissionsEX plugin ([here](http://forums.bukkit.org/threads/admn-dev-permissionsex-pex-v1-11-tomorrow-is-today-733-860.18140/)).
* Download the latest jar [here](https://github.com/N4th4/NuxKits/downloads).
* Copy the downloaded jar file into the plugins folder and rename it to "NuxKits.jar".

Configuration
-------------

The configuration file is : plugins/NuxKits/config.yml

Example :

    kits :
        myFirstKit :
            GRASS : 13
            GOLD_ORE  : 2
            GLASS : -1    
        arrows : 
            BOW : 1
            ARROW : 128

Materials can be find [here](http://jd.bukkit.org/apidocs/org/bukkit/Material.html). With negative numbers, the plugin will take the user's material. __Never__ use tabulations, only four spaces.

Permissions' nodes
------------------

* nuxkits.reload
* nuxkits.give.`<kitName>`

Commands
--------

Type "/NuxKits help" in the Minecraft's chat.
