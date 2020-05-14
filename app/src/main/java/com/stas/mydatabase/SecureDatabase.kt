//package com.stas.mydatabase
//
//import android.app.ProgressDialog.show
//import android.content.Context
//import android.widget.Toast
//import androidx.room.*
//import net.sqlcipher.database.SQLiteDatabase
//import net.sqlcipher.database.SupportFactory
//
//
//object SecureDatabase {
//
//    //    val db = Room.databaseBuilder(
////        applicationContext,
////        AppDatabase::class.java, "database-name"
////    ).build()
////
//    fun initDb(context: Context) {
//        val passphrase: ByteArray = SQLiteDatabase.getBytes(charArrayOf('s', 't'))
//        val factory = SupportFactory(passphrase)
//        val room: AppDatabase = Room.databaseBuilder(
//            context,
//            AppDatabase::class.java, "AppDatabase"
//        )
//            .openHelperFactory(factory).allowMainThreadQueries()
//            .build()
////        room.userDao().insertAll(
////            User(1, "", "s", 4),
////            User(2, "asd", "sd", 3)
////        )
//
//        Toast.makeText(context, "sum: ${room.userDao().getUser()}", Toast.LENGTH_SHORT).show()
//
//    }
//
//    @Database(entities = [User::class], version = 1)
//    abstract class AppDatabase : RoomDatabase() {
//        abstract fun userDao(): UserDao
//    }
//
//    @Entity
//    data class User(
//        @PrimaryKey val uid: Int,
//        @ColumnInfo(name = "first_name") val firstName: String?,
//        @ColumnInfo(name = "last_name") val lastName: String?,
//        @ColumnInfo(name = "number") val number: Int
//    )
//
//
//    @Dao
//    interface UserDao {
//        @Query("SELECT * FROM user")
//        fun getAll(): List<User>
//
//        @Query("SELECT * FROM user WHERE uid IN (:userIds)")
//        fun loadAllByIds(userIds: IntArray): List<User>
//
//        @Query(
//            "SELECT * FROM user WHERE first_name LIKE :first AND " +
//                    "last_name LIKE :last LIMIT 1"
//        )
//        fun findByName(first: String, last: String): User
//
//        @Query("SELECT SUM(number) FROM user")
//        fun sumUsers(): Int
//
//        @Query("SELECT last_name FROM user where number = 3")
//        fun getUser(): String
//
//        @Insert
//        fun insertAll(vararg users: User)
//
//        @Delete
//        fun delete(user: User)
//    }
//
//}