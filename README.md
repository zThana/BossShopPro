# BossShopPro
![i1](https://img.shields.io/badge/Minecraft-1.16+-blue.svg)
BossShopPro is one of the most player-friendly and multifunctional GUI plugins ever! Say goodbye to all the annoying command- or signshops!
It can not only be used to create shops but for every kind of menu. Here are just a few examples:
* Shop (Buy or sell items)
* Kits
* A menu that allows players to execute commands with a simple click
* Server selector (when having multiple servers connected)
* Warp menu

## About This Project
This project is an improved version of bosshoppro.This plugin only supports versions 1.16 and above.And fixed some bugs of the original project, see "Changes to the original project".Of course, you can also open issues and submit pull requests.(if the plugin you adapt stops upgrade, the pull request will be rejected.)  
You can download the development version from actions.

## Building BossShopPro

A few notes regarding building BSP: the current state of the code, unfortunately, requires you to manually add (the jars of) a few other plugins to the project in order to be able to successfully build BSP. As BSP includes support for many different plugins, such as Kingdoms and EpicSpawners, you need to add those to the project (at least those, which I was not able to add directly via Maven). It requires some initial effort to download those plugins and add them to the project.

## Changes to the original project
* Fixed the problem of putting minecraft color together when looking for hex color.  
* Remove VotingPlugin, LilyPad, CommandPoints, Kingdoms, EpicSpawners support.  
* Update dependency and change some prompt. 
* Something may be added in the future.
* The plugin usage remains unchanged.

## API
Information regarding the BossShopPro API can be found here: [Link](https://www.spigotmc.org/wiki/bossshoppro-api/).
