NuxKits
=======

Installation
------------

You need the Permissions plugin ([here](http://forums.bukkit.org/threads/admn-info-permissions-v2-0-revolutionizing-the-group-system.1403/)).

Download the latest jar [here](https://github.com/N4th4/NuxKits/downloads), copy it in your plugin directory and rename it to "NuxKits.jar"

Configuration
-------------

The configuration file is : plugins/NuxKits/config.yml

Example :
  >kits :
  >    myFirstKit :
  >        GRASS : 13
  >        GOLD_ORE  : 2
  >        GLASS : -1    
  >    arrows : 
  >        BOW : 1
  >        ARROW : 128
Materials can be find [here](http://javadoc.lukegb.com/Bukkit/d7/dd9/namespaceorg_1_1bukkit.html#ab7fa290bb19b9a830362aa88028ec80a)

With negative numbers, the plugin will take the user's material. __Never__ use tabulations, only four spaces.

Available nodes :
*   nuxkits.reload
*   nuxkits.give.<kitName>

Commands
--------

Type "/NuxKits help" in the Minecraft's chat.