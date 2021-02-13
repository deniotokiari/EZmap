package by.deniotokiari.core.tiles.provider

interface TilesProvider<T> {

    fun findFiles(): Array<T>
}