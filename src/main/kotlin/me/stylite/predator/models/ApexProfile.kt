package me.stylite.predator.models

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
    val ImgAssets: ImgAssetsXXXXXXXXXXXX,
    val LegendName: String,
    val `data`: List<DataX>
)

data class Bangalore(
    val ImgAssets: ImgAssets
)

data class Bloodhound(
    val ImgAssets: ImgAssetsX
)

data class Caustic(
    val ImgAssets: ImgAssetsXX
)

data class Crypto(
    val ImgAssets: ImgAssetsXXX
)

data class Gibraltar(
    val ImgAssets: ImgAssetsXXXX
)

data class Lifeline(
    val ImgAssets: ImgAssetsXXXXX
)

data class Mirage(
    val ImgAssets: ImgAssetsXXXXXX
)

data class Octane(
    val ImgAssets: ImgAssetsXXXXXXX,
    val `data`: List<Data>
)

data class Pathfinder(
    val ImgAssets: ImgAssetsXXXXXXXX
)

data class Revenant(
    val ImgAssets: ImgAssetsXXXXXXXXX
)

data class Wattson(
    val ImgAssets: ImgAssetsXXXXXXXXXX
)

data class Wraith(
    val ImgAssets: ImgAssetsXXXXXXXXXXX
)

data class ImgAssets(
    val banner: String,
    val icon: String
)

data class ImgAssetsX(
    val banner: String,
    val icon: String
)

data class ImgAssetsXX(
    val banner: String,
    val icon: String
)

data class ImgAssetsXXX(
    val banner: String,
    val icon: String
)

data class ImgAssetsXXXX(
    val banner: String,
    val icon: String
)

data class ImgAssetsXXXXX(
    val banner: String,
    val icon: String
)

data class ImgAssetsXXXXXX(
    val banner: String,
    val icon: String
)

data class ImgAssetsXXXXXXX(
    val banner: String,
    val icon: String
)

data class Data(
    val key: String,
    val name: String,
    val value: Int
)

data class ImgAssetsXXXXXXXX(
    val banner: String,
    val icon: String
)

data class ImgAssetsXXXXXXXXX(
    val banner: String,
    val icon: String
)

data class ImgAssetsXXXXXXXXXX(
    val banner: String,
    val icon: String
)

data class ImgAssetsXXXXXXXXXXX(
    val banner: String,
    val icon: String
)

data class ImgAssetsXXXXXXXXXXXX(
    val banner: String,
    val icon: String
)

data class DataX(
    val key: Any,
    val name: String,
    val value: Int
)

data class RateLimit(
    val current_req: String,
    val max_per_second: Int
)

data class Kd(
    val name: String,
    val value: Int
)

data class Kills(
    val name: String,
    val value: Int
)