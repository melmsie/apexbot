package me.stylite.predator.models.stats

data class ApexProfile(
    val global: Global,
    val legends: Legends,
    val mozambiquehere_internal: MozambiquehereInternal,
    val realtime: Realtime,
    val total: Total
)

data class Global(
    val bans: Bans,
    val battlepass: Battlepass,
    val internalUpdateCount: Int,
    val level: Int,
    val name: String,
    val platform: String,
    val rank: Rank,
    val toNextLevelPercent: Int,
    val uid: Long
)

data class Legends(
    val all: All,
    val selected: Selected
)

data class MozambiquehereInternal(
    val APIAccessType: String,
    val ClusterID: String,
    val claimedBy: String,
    val isNewToDB: Boolean,
    val rate_limit: RateLimit
)

data class Realtime(
    val canJoin: Int,
    val isInGame: Int,
    val isOnline: Int,
    val lobbyState: String,
    val partyFull: Int,
    val selectedLegend: String
)

data class Total(
    val kd: Kd,
    val kills: Kills
)

data class Bans(
    val isActive: Boolean,
    val last_banReason: String,
    val remainingSeconds: Int
)

data class Battlepass(
    val level: String
)

data class Rank(
    val rankDiv: Int,
    val rankImg: String,
    val rankName: String,
    val rankScore: Int
)

data class All(
    val Bangalore: Bangalore,
    val Bloodhound: Bloodhound,
    val Caustic: Caustic,
    val Crypto: Crypto,
    val Gibraltar: Gibraltar,
    val Lifeline: Lifeline,
    val Mirage: Mirage,
    val Octane: Octane,
    val Pathfinder: Pathfinder,
    val Revenant: Revenant,
    val Wattson: Wattson,
    val Wraith: Wraith
)

data class Selected(
    val ImgAssets: ImgAssets,
    val LegendName: String,
    val `data`: List<Data>
)

data class Bangalore(val ImgAssets: ImgAssets)
data class Bloodhound(val ImgAssets: ImgAssets)
data class Caustic(val ImgAssets: ImgAssets)
data class Crypto(val ImgAssets: ImgAssets)
data class Gibraltar(val ImgAssets: ImgAssets)
data class Lifeline(val ImgAssets: ImgAssets)
data class Mirage(val ImgAssets: ImgAssets)
data class Octane(val ImgAssets: ImgAssets, val `data`: List<Data>)
data class Pathfinder(val ImgAssets: ImgAssets)
data class Revenant(val ImgAssets: ImgAssets)
data class Wattson(val ImgAssets: ImgAssets)
data class Wraith(val ImgAssets: ImgAssets)

data class ImgAssets(
    val banner: String,
    val icon: String
)

data class Data(
    val key: String,
    val name: String?,
    val value: Int
)

data class RateLimit(
    val current_req: String,
    val max_per_second: Int
)

data class Kd(
    val name: String,
    val value: Float
)

data class Kills(
    val name: String,
    val value: Int
)
