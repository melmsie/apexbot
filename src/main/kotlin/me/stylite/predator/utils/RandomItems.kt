package me.stylite.predator.utils

object RandomItems {
    val guns = setOf("VK-47 Flatline", "G7 Scout", "Hemlok Burst AR", "R-301 Carbine", "HAVOC Rifle",
        "Alternator SMG", "Prowler Burst PDW", "R-99",
        "Devotion LMG", "M600 Spitfire", "L-STAR EMG",
        "Longbow DMR", "Kraber .50-cal Sniper", "Triple Take", "Sentinel",
        "EVA-8 Auto", "Mastiff Shotgun", "Mozambique Shotgun", "Peacekeeper",
        "RE-45 Auto", "P2020", "Wingman")

    val legends = setOf("Bangalore", "Bloodhound", "Caustic", "Crypto", "Gibraltar", "Lifeline",
        "Mirage", "Octane", "Pathfinder", "Revenant", "Wattson", "Wraith")

    val grenades = setOf("Frag", "Thermite", "Arc Star")

    val shields = setOf("Common body shield", "Rare body shield", "Epic body shield", "Legendary body shield", "Evo shield")
    val helmets = setOf("Common helmet", "Rare helmet", "Epic helmet", "Legendary helmet")
    val knockdownShields = setOf("Common knockdown", "Rare knockdown", "Epic knockdown", "Legendary knockdown")
    val backpacks = setOf("Common backpack", "Rare backpack", "Epic backpack", "Legendary backpack")

    fun generateLoadout(): String {
        val firstGun = guns.random()
        val secondGun = guns.random()
        val grenade = grenades.random()
        val legend = legends.random()
        val shield = shields.random()
        val helmet = helmets.random()
        val knockdown = knockdownShields.random()
        val backpack = backpacks.random()
        return "**Legend**: $legend\n\n" +
                "**First Weapon**: $firstGun\n" +
                "**Second Weapon**: $secondGun\n" +
                "**Grenades**: $grenade\n\n" +
                "**Body Shield**: $shield\n" +
                "**Helmet**: $helmet\n" +
                "**Knockdown Shield**: $knockdown\n" +
                "**Backpack**: $backpack"
    }
}