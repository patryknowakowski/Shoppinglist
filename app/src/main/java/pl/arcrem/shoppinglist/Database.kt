package pl.arcrem.shoppinglist

import android.arch.persistence.room.*
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.Database
import android.database.Cursor


@Database(entities = arrayOf(List::class, Entry::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun listDao(): ListDao
    abstract fun entryDao(): EntryDao
}

@Entity(tableName = "list")
class List(name: String) {
    @PrimaryKey
    @ColumnInfo(name = "list_id")
    val id: Int = 0

    @ColumnInfo(name = "list_name")
    val name: String = name
}

@Dao
interface ListDao {
    @get:Query("SELECT * FROM list")
    val all: Cursor

    @Query("SELECT * FROM list WHERE list_name LIKE :name")
    fun find(name: String): Cursor

    @Insert
    fun insert(list: List)

    @Delete
    fun delete(list: List)
}

@Entity
class Entry(name: String, description: String? = null) {
    @PrimaryKey
    @ColumnInfo(name = "entry_id")
    val id: Int = 0

    @ColumnInfo(name = "entry_list_id")
    @ForeignKey()
    val listId: Int = name

    @ColumnInfo(name = "entry_name")
    val name: String = name

    @ColumnInfo(name = "entry_description")
    val description: String? = description
}

@Dao
interface EntryDao {
    @Query("SELECT * FROM entry WHERE entry_id")
    fun get(id: Int): Cursor

    @Query("SELECT * FROM list WHERE list_name LIKE :name")
    fun find(name: String): Cursor

    @Insert
    fun insert(list: List)

    @Delete
    fun delete(list: List)
}