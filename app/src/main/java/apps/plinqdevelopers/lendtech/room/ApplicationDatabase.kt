package apps.plinqdevelopers.lendtech.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import apps.plinqdevelopers.lendtech.data.domain.DomainAccount
import apps.plinqdevelopers.lendtech.data.domain.DomainTransaction
import apps.plinqdevelopers.lendtech.data.domain.DomainWallet

@Database(
    entities = [
        DomainAccount::class,
        DomainWallet::class,
        DomainTransaction::class
    ],
    version = 1
)
@TypeConverters(Converters::class)
abstract class ApplicationDatabase : RoomDatabase() {
    abstract fun applicationDAO(): ApplicationDAO
}