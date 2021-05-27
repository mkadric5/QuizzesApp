package ba.etf.rma21.projekat.data.repositories

object AccountRepository {
        var acHash: String = "b7aa1b3d-7a8d-4411-9a47-0d23b4f0cb9c"

        fun postaviHash(acHash: String): Boolean {
            this.acHash = acHash
            return true
        }

        fun getHash(): String {
            return acHash
        }
}